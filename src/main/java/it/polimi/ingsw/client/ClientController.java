package it.polimi.ingsw.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientController implements Runnable{
    private final Socket server;
    private final View view;
    private final Scanner input;
    private final PrintWriter output;
    public ClientController(int serverPort, String serverAddress, View view) throws IOException {
        this.server = new Socket(serverAddress, serverPort);
        this.input = new Scanner(this.server.getInputStream());
        this.output = new PrintWriter(this.server.getOutputStream());
        this.view = view;
    }

    @Override
    public void run() {
        while (true) {
            String line = this.input.nextLine();
            System.out.println("Received: " + line);
            this.handleMessage(line);
        }
    }

    public void sendMessage(String message) {
        this.output.println(message);
        this.output.flush();
    }

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
        }
    }
}
