package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.ClientMessage;
import it.polimi.ingsw.messages.servertoclient.Ping;
import it.polimi.ingsw.messages.servertoclient.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler implements Runnable {
    private final ServerController controller;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final ConcreteServerVisitor visitorServer;
    private int pingSent;
    private String nickname;
    private final Timer timer;

    public ClientHandler(Socket client, ServerController controller) throws IOException {
        this.controller = controller;
        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        this.visitorServer = new ConcreteServerVisitor(this.controller,this);
        this.pingSent = 0;
        this.timer = new Timer();
    }

    @Override
    public void run() {
        this.startPing();
        while(true) {
            try {
                ClientMessage clientMessage = (ClientMessage) inputStream.readObject();
                if (clientMessage.TypeOfMessage().equals("PongMessage")) {
                    this.pingSent = 0;
                } else {
                    clientMessage.accept(visitorServer);
                }
            } catch (SocketException e) {
                this.controller.removePlayer(this);
                break;
            } catch (ClassNotFoundException | IOException e) {
                break;
            }
        }
    }

    void setNickname(String nickname){
        this.nickname = nickname;
    }

    String getNickname() {
        return nickname;
    }

    void sendObjectMessage(ServerMessage message) {
        synchronized (this.outputStream) {
            try {
                this.outputStream.writeObject(message);
                this.outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                this.outputStream.notifyAll();
            }
        }
    }

    void startPing() {
        timer.scheduleAtFixedRate(new ConnectionTask(this), 1000, 1000);
    }

    void newPingSent() {
        this.pingSent++;
        if (this.pingSent >= 5) {
            System.out.println("Lost connection from " + nickname);
            this.controller.removePlayer(this);
            timer.cancel();
            try {
                this.inputStream.close();
            } catch (IOException ignored) {}
            synchronized (this.outputStream) {
                try {
                    this.outputStream.close();
                } catch (IOException ignored) {}
                finally {
                    this.outputStream.notifyAll();
                }
            }
        }
    }
}
class ConnectionTask extends TimerTask {
    private final ClientHandler handler;

    ConnectionTask(ClientHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        this.handler.sendObjectMessage(new Ping());
        this.handler.newPingSent();
    }
}