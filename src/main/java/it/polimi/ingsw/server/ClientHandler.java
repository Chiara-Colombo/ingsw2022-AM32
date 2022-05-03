package it.polimi.ingsw.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable {
    private final Socket client;
    private final ServerController controller;
    private final Scanner input;
    private final PrintWriter output;

    public ClientHandler(Socket client, ServerController controller) throws IOException {
        this.client = client;
        this.controller = controller;
        this.input = new Scanner(this.client.getInputStream());
        this.output = new PrintWriter(this.client.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            String message = this.input.nextLine();
            try {
                this.handleMessage(message);
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    public void sendMessage(String message) {
        this.output.println(message);
        this.output.flush();
    }

    private void handleMessage(String message) throws IOException {
        switch (message.split("%")[0]) {
            case "NumOfPlayersResponse" -> {
                this.controller.setNumOfPlayers(Integer.parseInt(message.split("%")[1]));
            }
            case "GameModeResponse" -> {
                this.controller.setGameMode(Boolean.parseBoolean(message.split("%")[1]));
            }
            case "SetUsername" -> {
                this.controller.setUsername(message.split("%")[1], this);
            }
        }
    }
}
