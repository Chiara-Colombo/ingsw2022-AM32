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

public class ClientHandler implements Runnable {
    private final MatchManager matchManager;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final Timer timer;
    private final TimerTask task;
    private int pingSent;
    private boolean closed;
    private String nickname;
    private ServerController controller;
    private ConcreteServerVisitor visitorServer;

    /**
     * Class constructor
     */

    public ClientHandler(MatchManager matchManager, Socket client) throws IOException {
        this.matchManager = matchManager;
        this.inputStream = new ObjectInputStream(client.getInputStream());
        this.outputStream = new ObjectOutputStream(client.getOutputStream());
        this.pingSent = 0;
        this.timer = new Timer();
        this.task = new ConnectionTask(this);
        this.visitorServer = new ConcreteServerVisitor(this.matchManager, this);
        this.closed = false;
    }

    /**
     * Method that run a Client Handler
     */

    @Override
    public void run() {
        this.startPing();
        while(true) {
            synchronized (this.inputStream) {
                if (this.closed) {
                    this.inputStream.notifyAll();
                    return;
                }
                try {
                    ClientMessage clientMessage = (ClientMessage) this.inputStream.readObject();
                    if (clientMessage.TypeOfMessage().equals("PongMessage")) {
                        this.pingSent = 0;
                    } else {
                        clientMessage.accept(visitorServer);
                    }
                } catch (SocketException e) {
                    this.inputStream.notifyAll();
                    try {
                        this.controller.removePlayer(this);
                    } catch (NullPointerException ne) {
                        this.matchManager.removeClient(this);
                    }
                    break;
                } catch (ClassNotFoundException | IOException e) {
                    break;
                } catch(NullPointerException ignored) {
                } finally {
                    this.inputStream.notifyAll();
                }
            }
        }
    }

    /**
     * Method that closes the Client Handler
     */

    void close() {
        this.timer.cancel();
        this.task.cancel();
        synchronized (this.inputStream) {
            try {
                this.inputStream.close();
            } catch (IOException ignored){}
            finally {
                this.closed = true;
                this.inputStream.notifyAll();
            }
        }
        synchronized (this.outputStream) {
            try {
                this.outputStream.close();
            } catch (IOException ignored){}
            finally {
                this.outputStream.notifyAll();
            }
        }
    }

    /**
     * Setter for the controller
     * @param controller the Server Controller
     */

    void setController(ServerController controller) {
        this.controller = controller;
        this.visitorServer.setServerController(controller);
    }

    /**
     * Method that set the player Username
     * @param nickname the player username
     */

    void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * Getter for the player username
     * @return the player username
     */

    String getNickname() {
        return nickname;
    }

    /**
     * Method that sends Server Messages
     * @param message message sent by the Server
     */

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

    /**
     * Method that start to send the ping
     */

    void startPing() {
        this.timer.scheduleAtFixedRate(this.task, 1000, 1000);
    }

    /**
     * Method that removes a player if it lost connection
     * (the connection is lost when 5 ping don't have a response)
     */

    void newPingSent() {
        this.pingSent++;
        if (this.pingSent >= 5) {
            System.out.println("Lost connection from " + nickname);
            this.controller.removePlayer(this);
            this.close();
        }
    }
}
class ConnectionTask extends TimerTask {
    private final ClientHandler handler;

    ConnectionTask(ClientHandler handler) {
        this.handler = handler;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void run() {
        this.handler.sendObjectMessage(new Ping());
        this.handler.newPingSent();
    }
}