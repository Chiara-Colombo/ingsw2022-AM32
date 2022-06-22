package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.ClientMessage;
import it.polimi.ingsw.messages.clienttoserver.Pong;
import it.polimi.ingsw.messages.servertoclient.ConnectionLost;
import it.polimi.ingsw.messages.servertoclient.ServerMessage;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements Runnable{
    private String username;
    private final String serverAddress;
    private final int serverPort;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private final ConcreteClientVisitor clientVisitor;
    private final Timer timer;
    private final boolean isGui;
    private int unreceivedPing;

    public ClientController(int serverPort, String serverAddress, View view, boolean isGui) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.isGui = isGui;
        this.unreceivedPing = 0;
        this.clientVisitor = new ConcreteClientVisitor(view);
        this.timer = new Timer();
    }

    @Override
    public synchronized void run()  {
        ExecutorService executor = Executors.newCachedThreadPool();
        timer.scheduleAtFixedRate(new ConnectionTask(this), 1000, 1000);
        while(true) {
             try {
                 ServerMessage servermessage = (ServerMessage) this.inputStream.readObject();
                 if (servermessage.TypeOfMessage().equals("PingMessage")) {
                     executor.submit(() -> {
                         this.sendObjectMessage(new Pong());
                         this.unreceivedPing = 0;
                     });
                 } else {
                     if (this.isGui) {
                         Platform.runLater(() -> {
                             try {
                                 servermessage.accept(clientVisitor);
                             } catch (IOException e) {
                                 e.printStackTrace();
                             }
                         });
                     } else {
                         servermessage.accept(clientVisitor);
                     }
                 }
             } catch (ClassNotFoundException | IOException e) {
                 e.printStackTrace();
                 break;
             }
        }
        System.out.println("Ending Thread");
    }

    void connect() {
        Socket server;
        try {
            server = new Socket(this.serverAddress, this.serverPort);
        } catch (IOException e) {
            this.timer.cancel();
            throw new RuntimeException(e);
        }
        try {
            this.outputStream = new ObjectOutputStream(server.getOutputStream());
            this.inputStream = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            this.timer.cancel();
            try {
                server.close();
                System.out.println("Server closed");
            } catch (IOException ignored) {}
            throw new RuntimeException(e);
        }
    }

    public void sendObjectMessage(ClientMessage message){
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    void pingNotReceived() {
        this.unreceivedPing++;
        if (this.unreceivedPing >= 5)
            this.connectionLost();
    }

    private void connectionLost() {
        this.timer.cancel();
        try {
            this.outputStream.close();
            this.inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.clientVisitor.visitMessage(new ConnectionLost());
    }

    void close() {
        try {
            this.outputStream.close();
            this.inputStream.close();
        } catch (IOException e) {
            this.timer.cancel();
            throw new RuntimeException(e);
        }
    }
}
class ConnectionTask extends TimerTask {
    private final ClientController controller;

    ConnectionTask(ClientController controller) {
        this.controller = controller;
    }

    @Override
    public void run() {
        this.controller.pingNotReceived();
    }
}