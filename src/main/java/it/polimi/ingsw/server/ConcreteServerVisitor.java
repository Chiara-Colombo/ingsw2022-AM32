package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.*;

import java.io.IOException;

public class ConcreteServerVisitor implements VisitorServer{

    private ServerController serverController;
    private final ClientHandler player;
    private final MatchManager matchManager;

    public ConcreteServerVisitor(MatchManager matchManager, ClientHandler player) {
         this.matchManager = matchManager;
         this.player = player;
    }

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    @Override
    public void visitMessage(NumOfPlayersResponse numOfPlayersResponse) {
        if(numOfPlayersResponse.getNumOfPlayers() > 3 || numOfPlayersResponse.getNumOfPlayers() < 2){
            this.serverController.errorOnPlayerNumber(player);
        } else
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
        this.serverController.setAssistantCard(assistantCardResponse.getChosenCard());
    }

    @Override
    public void visitMessage(CloudResponse cloudResponse) {
        this.serverController.chooseCloud(cloudResponse.getCloudIndex());
    }

    @Override
    public void visitMessage(MatchResponse matchResponse) throws IOException {
        this.matchManager.matchResponse(matchResponse.isNewMatch(), this.player);
    }

    @Override
    public void visitMessage(MoveMNResponse moveMNResponse) {
        this.serverController.moveMotherNature(moveMNResponse.getPosition());
    }

    @Override
    public void visitMessage(MovePawnResponse movePawnResponse) {
        this.serverController.moveStudent(movePawnResponse.getStudentIndex(), movePawnResponse.getIslandIndex(), movePawnResponse.isMoveOnSchoolBoard());
    }

    @Override
    public void visitMessage(SelectColorResponse selectColorResponse) {
        this.serverController.selectColor(selectColorResponse.getColor());
    }

    @Override
    public void visitMessage(SelectIslandResponse selectIslandResponse) {
        this.serverController.selectIsland(selectIslandResponse.getIslandIndex());
    }

    @Override
    public void visitMessage(SelectPawnResponse selectPawnResponse) {
        this.serverController.selectPawn(selectPawnResponse.getPawnIndex());
    }

    @Override
    public void visitMessage(UseCharacterCard useCharacterCard) {
        this.serverController.useCharacterCard(useCharacterCard.getCharacter());
    }

    @Override
    public void visitMessage(WizardCardResponse wizardCardResponse) {
        this.serverController.setWizard(wizardCardResponse.getChosenWizard());
    }

    @Override
    public void visitMessage(SelectPawnsResponse selectPawnsResponse) {
        this.serverController.selectPawns(selectPawnsResponse.getPawnsindex());
    }

    public void visitMessage(Quit quit){

    }
}