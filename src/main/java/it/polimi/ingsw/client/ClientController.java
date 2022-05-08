package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.ClientMessage;
import it.polimi.ingsw.messages.servertoclient.ServerMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientController implements Runnable{
    private final Socket server;
    private final View view;
    //private final Scanner input;
   // private final PrintWriter output;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final ConcreteClientVisitor clientVisitor;

    public ClientController(int serverPort, String serverAddress, View view) throws IOException {
        this.server = new Socket(serverAddress, serverPort);
        //this.input = new Scanner(this.server.getInputStream());
       // this.output = new PrintWriter(this.server.getOutputStream());
        this.view = view;
        outputStream = new ObjectOutputStream(this.server.getOutputStream());
        inputStream = new ObjectInputStream(this.server.getInputStream());
         this.clientVisitor = new ConcreteClientVisitor(this.view);

    }

    @Override
    public void run()  {
        /**
       while (true) {
            String line = this.input.nextLine();
            System.out.println("Received: " + line);
            this.handleMessage(line);
        }

*/

        while(true) {

             try {

                 ServerMessage servermessage =  (ServerMessage) inputStream.readObject();
                 System.out.println("Received message: " + servermessage.TypeOfMessage());
                 servermessage.accept(clientVisitor);
             } catch (IOException e) {
                 e.printStackTrace();
             } catch (ClassNotFoundException e) {
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
    public void sendObjectMessage(ClientMessage message){
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

 /**

    private void handleMessage(String message) {
        switch (message.split("%")[0]) {
            case "NumOfPlayersRequest" -> {
                this.view.showRequestNumOfPlayers();
            }
            case "GameModeRequest" -> {
                this.view.showRequestExpertMode();
            }
            case "RequestUsername" -> {
                this.view.showRequestUsername();
            }
            case "UsernameCorrectlyAssigned" -> {
                this.view.showWaitingView();
            }
            case "UsernameNotAssigned" -> {
                String error = "Username invalido. Deve essere univoco!";
                this.view.showErrorMessage(error);
            }
            case "GameIsStarting" -> {
                this.view.showGameStartingView();
            }
            case "ErrorOnplayernumber" -> {
                String error = "Hai sbagliato a selezionare i giocatori!";
                this.view.showErrorMessage(error);
            }
        }
    }
  */
}
