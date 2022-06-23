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
import java.net.SocketException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientController implements Runnable{
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final ConcreteClientVisitor clientVisitor;
    private final Timer timer;
    private final TimerTask task;
    private final boolean isGui;
    private int unreceivedPing;
    private boolean closed;
    private String username;

    public ClientController(int serverPort, String serverAddress, View view, boolean isGui) throws IOException {
        this.isGui = isGui;
        this.unreceivedPing = 0;
        this.clientVisitor = new ConcreteClientVisitor(view);
        this.timer = new Timer();
        this.task = new ConnectionTask(this);
        this.closed = false;
        Socket server = new Socket(serverAddress, serverPort);
        this.outputStream = new ObjectOutputStream(server.getOutputStream());
        this.inputStream = new ObjectInputStream(server.getInputStream());
    }

    @Override
    public synchronized void run()  {
        ExecutorService executor = Executors.newCachedThreadPool();
        this.timer.scheduleAtFixedRate(this.task, 1000, 1000);
        while(true) {
            ServerMessage servermessage;
            synchronized (this.inputStream) {
                try {
                    servermessage = (ServerMessage) this.inputStream.readObject();
                    if (!Objects.isNull(servermessage)) {
                        try {
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
                                        } catch (IOException ignored) {}
                                    });
                                } else {
                                    executor.submit(() -> {
                                        try {
                                            servermessage.accept(clientVisitor);
                                        } catch (IOException ignored) {}
                                    });
                                }
                            }
                        } catch (NullPointerException ignored) {}
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                } catch (IOException e) {
                    this.inputStream.notifyAll();
                    this.close();
                    break;
                } finally {
                    this.inputStream.notifyAll();
                }
            }
        }
        System.out.println("Ending Thread");
    }

    public void sendObjectMessage(ClientMessage message){
        synchronized (this.outputStream) {
            try {
                outputStream.writeObject(message);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.outputStream.notifyAll();
            }
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
        this.close();
        this.clientVisitor.visitMessage(new ConnectionLost());
    }

    void close() {
        this.task.cancel();
        this.timer.cancel();
        synchronized (this.outputStream) {
            try {
                this.outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                this.outputStream.notifyAll();
            }
        }
        synchronized (this.inputStream) {
            try {
                this.inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                this.closed = true;
                this.inputStream.notifyAll();
            }
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