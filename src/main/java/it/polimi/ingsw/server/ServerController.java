package it.polimi.ingsw.server;

import it.polimi.ingsw.model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static it.polimi.ingsw.utils.Utils.CARDS_RESOURCE_PATH;

public class ServerController {
    private Game game;
    private ArrayList<ClientHandler> clients;
    private Map<String, ClientHandler> usernames;
    private int numOfPlayers;

    public ServerController() {
        this.numOfPlayers = -1;
        this.clients = new ArrayList<>(0);
        this.usernames = new HashMap<>(0);
    }

    public void addPlayer(Socket player) throws IOException {
        if (this.clients.size() >= this.numOfPlayers && this.numOfPlayers > 0) {
            new PrintWriter(player.getOutputStream()).println("ConnectionRefused");
            System.out.println("Too many players");
        }
        ClientHandler client = new ClientHandler(player, this);
        this.clients.add(client);
        if (this.numOfPlayers < 0) {
            System.out.println("First player");
            String message = "NumOfPlayersRequest";
            client.sendMessage(message);
            message = "GameModeRequest";
            client.sendMessage(message);
        }
        String message = "RequestUsername";
        client.sendMessage(message);
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    public void setGameMode(boolean expertMode) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(CARDS_RESOURCE_PATH));
        String jsonCards = input.readLine();
        this.game = new Game(this.numOfPlayers, expertMode, jsonCards);
    }

    public void setUsername(String username, ClientHandler player) {
        this.usernames.put(username, player);
        String message = "UsernameCorrectlyAssigned";
        System.out.println("Added player " + username);
        player.sendMessage(message);
        if (this.numOfPlayers == this.clients.size()) {
            this.startGame();
        }
    }

    private void startGame() {
        Iterator<String> iterUsernames = this.usernames.keySet().iterator();
        ArrayList<TowersColors> colors = new ArrayList<>(0);
        int towers = 8;
        colors.add(TowersColors.WHITE);
        colors.add(TowersColors.BLACK);
        if (this.numOfPlayers > 2) {
            towers = 6;
            colors.add(TowersColors.GREY);
        }
        for (int i = 0; i<this.numOfPlayers; i++) {
            Player player= new Player(iterUsernames.next(), towers);
            player.setColor(colors.get(i));
            this.game.addPlayer(player);
        }
        this.game.startGame();
    }
}
