package it.polimi.ingsw.server;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.messages.clienttoserver.GameModeResponse;
import it.polimi.ingsw.messages.clienttoserver.NumOfPlayersResponse;
import it.polimi.ingsw.messages.clienttoserver.SetUsername;
import it.polimi.ingsw.messages.servertoclient.NumOfPlayersRequest;

import java.io.IOException;
import java.net.Socket;

public class ConcreteServerVisitor implements VisitorServer{

    ServerController serverController;
    ClientHandler player;

    public ConcreteServerVisitor(ServerController serverController,ClientHandler player) {
         this.serverController = serverController;
         this.player = player;
    }

    @Override
    public void visitMessage(NumOfPlayersResponse numOfPlayersResponse) {
        this.serverController.setNumOfPlayersVero(numOfPlayersResponse.getNumOfPlayers(), player);
    }

    @Override
    public void visitMessage(GameModeResponse gameModeResponse)  {
        try {
            this.serverController.setGameMode(gameModeResponse.getExpertMode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitMessage(SetUsername setUsername)  {
       this.serverController.setUsername(setUsername.getUsername(), player);
    }
}
