package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.NumOfPlayersResponse;
import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Future;

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
            player.close();
        }
        ClientHandler client = new ClientHandler(player, this);
        new Thread(client).start();
        this.clients.add(client);
        if (this.numOfPlayers < 0) {
            System.out.println("First player");
           // String message = "NumOfPlayersRequest";
           ServerMessage NumOfPlayersRequest = new NumOfPlayersRequest();
           client.sendObjectMessage(NumOfPlayersRequest);
           // client.sendMessage(message);
           ServerMessage GameModeRequest = new GameModeRequest();
           client.sendObjectMessage(GameModeRequest);
          //  message = "GameModeRequest";
          //  client.sendMessage(message);
        }
       // String message = "RequestUsername";
        ServerMessage RequestUsername = new RequestUsername();
        client.sendObjectMessage(RequestUsername);
       // client.sendMessage(message);
    }
/**
    public void setNumOfPlayers(int numOfPlayers, ClientHandler player) {
        this.numOfPlayers = numOfPlayers;
    }
    */



    public void setNumOfPlayersVero(int numOfPlayers, ClientHandler player) {
        this.numOfPlayers = numOfPlayers;
    }



    public void setGameMode(boolean expertMode) throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(CARDS_RESOURCE_PATH));
        String jsonCards = input.readLine();
        this.game = new Game(this.numOfPlayers, expertMode, jsonCards);
    }

    public void setUsername(String username, ClientHandler player) {
        if (this.usernames.containsKey(username)) {
           // String message = "UsernameNotAssigned";
            UsernameNotAssigned usernameNotAssigned = new UsernameNotAssigned();
            player.sendObjectMessage(usernameNotAssigned);
           // player.sendMessage(message);
            RequestUsername requestUsername = new RequestUsername();
            player.sendObjectMessage(requestUsername);
            //message = "RequestUsername";
            //player.sendMessage(message);
            return;
        }
        this.usernames.put(username, player);
       // String message = "UsernameCorrectlyAssigned";
        UsernameCorrectlyAssigned usernameCorrectlyAssigned = new UsernameCorrectlyAssigned();
        System.out.println("Added player " + username);
        player.sendObjectMessage(usernameCorrectlyAssigned);
        if(this.numOfPlayers != this.clients.size()){
            WaitingForPlayers waitingForPlayers = new WaitingForPlayers();
            player.sendObjectMessage(waitingForPlayers);
        }
        //player.sendMessage(message);
       // if (this.numOfPlayers == this.clients.size())
        else {
          //  message = "GameIsStarting";
            GameIsStarting gameIsStarting = new GameIsStarting();
            for(int i = 0; i < clients.size(); i++) {
                clients.get(i).sendObjectMessage(gameIsStarting);
            }
           // player.sendMessage(message);
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
