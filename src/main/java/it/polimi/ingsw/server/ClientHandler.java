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
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final ServerController controller;
    //private final Scanner input;
  //  private final PrintWriter output;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
   private final ConcreteServerVisitor visitorServer;



    public ClientHandler(Socket client, ServerController controller) throws IOException {
        this.client = client;
        this.controller = controller;
       // this.input = new Scanner(this.client.getInputStream());
      //  this.output = new PrintWriter(this.client.getOutputStream());
        this.inputStream=new ObjectInputStream(this.client.getInputStream());
        this.outputStream=new ObjectOutputStream(this.client.getOutputStream());
        this.visitorServer=new ConcreteServerVisitor(this.controller,this);
    }

    @Override
    public void run() {
/**
        while (true) {
            String message = this.input.nextLine();
            System.out.println("Received message: " + message);
            try {
                this.handleMessage(message);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
*/

        while(true) {
            try {
                ClientMessage clientMessage =  (ClientMessage) inputStream.readObject();
                System.out.println("Received message: " + clientMessage.TypeOfMessage());
                clientMessage.accept(visitorServer);
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
        }
    }


/**
    public void sendMessage(String message) {
        this.output.println(message);
        this.output.flush();
    }
 */


    public void sendObjectMessage(ServerMessage message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


/**
    private void handleMessage(String message) throws IOException {
        switch (message.split("%")[0]) {
            case "NumOfPlayersResponse" -> {
                this.controller.setNumOfPlayers(Integer.parseInt(message.split("%")[1]),this);
            }
            case "GameModeResponse" -> {
                this.controller.setGameMode(Boolean.parseBoolean(message.split("%")[1]));
            }
            case "SetUsername" -> {
                this.controller.setUsername(message.split("%")[1], this);
            }
        }
    }
    */
}
