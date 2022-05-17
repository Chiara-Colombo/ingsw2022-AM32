package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.*;

import java.io.IOException;

public class ConcreteServerVisitor implements VisitorServer{

    ServerController serverController;
    ClientHandler player;

    public ConcreteServerVisitor(ServerController serverController,ClientHandler player) {
         this.serverController = serverController;
         this.player = player;
    }

    @Override
    public void visitMessage(NumOfPlayersResponse numOfPlayersResponse) {
        if(numOfPlayersResponse.getNumOfPlayers() > 3 || numOfPlayersResponse.getNumOfPlayers() < 2){
            this.serverController.errorOnPlayerNumber(player);
        }
        else
        this.serverController.setNumOfPlayers(numOfPlayersResponse.getNumOfPlayers(),player);
    }

    @Override
    public void visitMessage(GameModeResponse gameModeResponse)  {
        try {
            this.serverController.setGameMode(gameModeResponse.getExpertMode(), this.player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void visitMessage(SetUsername setUsername)  {
       this.serverController.setUsername(setUsername.getUsername(), player);
    }

    @Override
    public void visitMessage(AssistantCardResponse assistantCardResponse) {

    }

    @Override
    public void visitMessage(CloudResponse cloudResponse) {

    }

    @Override
    public void visitMessage(MoveMNResponse moveMNResponse) {

    }

    @Override
    public void visitMessage(MovePawnResponse movePawnResponse) {

    }

    @Override
    public void visitMessage(SelectPawnResponse selectPawnResponse) {

    }

    @Override
    public void visitMessage(UseCharacterCard useCharacterCard) {

    }

    @Override
    public void visitMessage(WizardCardResponse wizardCardResponse) {
        this.serverController.setWizard(wizardCardResponse.getChosenWizard());
    }

    public void visitMessage(Quit quit){

    }
}
