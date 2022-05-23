package it.polimi.ingsw.server;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.messages.clienttoserver.ClientMessage;
import it.polimi.ingsw.messages.clienttoserver.NumOfPlayersResponse;
import it.polimi.ingsw.messages.servertoclient.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final ServerController controller;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final ConcreteServerVisitor visitorServer;
    private String nickname;

    public ClientHandler(Socket client, ServerController controller) throws IOException {
        this.client = client;
        this.controller = controller;
        this.inputStream = new ObjectInputStream(this.client.getInputStream());
        this.outputStream = new ObjectOutputStream(this.client.getOutputStream());
        this.visitorServer = new ConcreteServerVisitor(this.controller,this);
    }

    @Override
    public void run() {
        while(true) {
            try {
                ClientMessage clientMessage =  (ClientMessage) inputStream.readObject();
                System.out.println("Received message: " + clientMessage.TypeOfMessage());
                clientMessage.accept(visitorServer);
            } catch (SocketException e) {
                this.controller.removePlayer(this);
                break;
            } catch (ClassNotFoundException | IOException e) {
                break;
            }
        }
    }

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void sendObjectMessage(ServerMessage message) {
        System.out.println("SENDING MESSAGE: " + message.TypeOfMessage());
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
