package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.ClientMessage;
import it.polimi.ingsw.messages.servertoclient.ServerMessage;
import javafx.application.Platform;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController implements Runnable{
    private Socket server;
    private final View view;
    private final String serverAddress;
    private final int serverPort;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private final ConcreteClientVisitor clientVisitor;
    private final boolean isGui;

    public ClientController(int serverPort, String serverAddress, View view, boolean isGui) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.view = view;
        this.clientVisitor = new ConcreteClientVisitor(this.view);
        this.isGui = isGui;
    }

    @Override
    public void run()  {
        while(true) {
             try {
                 ServerMessage servermessage =  (ServerMessage) inputStream.readObject();
                 System.out.println("Received message: " + servermessage.TypeOfMessage());
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
             } catch (ClassNotFoundException | IOException e) {
                 e.printStackTrace();
             }
         }
    }

    public void connect() throws IOException {
        this.server = new Socket(this.serverAddress, this.serverPort);
        this.outputStream = new ObjectOutputStream(this.server.getOutputStream());
        this.inputStream = new ObjectInputStream(this.server.getInputStream());
    }

    public void sendObjectMessage(ClientMessage message){
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
