package it.polimi.ingsw.client;

import java.io.IOException;
import java.util.Scanner;

import static it.polimi.ingsw.utils.Utils.*;

public class CLI implements View{
    private ClientController clientController;

    public void start() {
        try {
            this.clientController = new ClientController(DEFAULT_SERVER_PORT, DEFAULT_SERVER_ADDRESS, this);
            System.out.println("Controller initialized");
        } catch (IOException e) {
            System.out.println("Cannot instantiate connection towards server");
            return;
        }
        new Thread(this.clientController).start();
    }

    @Override
    public void showRequestNumOfPlayers() {
        System.out.print("Quanti giocatori per questa partita? [2 - 3]\n - ");
        int numOfPlayers = new Scanner(System.in).nextInt();
        String message = "NumOfPlayersResponse" + '%' + numOfPlayers;
        this.clientController.sendMessage(message);
    }

    @Override
    public void showRequestExpertMode() {
        System.out.print("Vuoi giocare con la modalità esperti? [y - n]\n - ");
        String ans = new Scanner(System.in).nextLine();
        boolean expertMode = ans.toLowerCase().charAt(0) == 'y';
        String message = "GameModeResponse" + '%' + expertMode;
        this.clientController.sendMessage(message);
    }

    @Override
    public void showRequestUsername() {
        System.out.print("Inserisci uno username:\n - ");
        String username = new Scanner(System.in).nextLine();
        String message = "SetUsername" + '%' + username;
        this.clientController.sendMessage(message);
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println('\n' + message + '\n');
    }

    @Override
    public void showWaitingView() {
        System.out.println("In attesa che altri giocatori si colleghino...");
    }
}
