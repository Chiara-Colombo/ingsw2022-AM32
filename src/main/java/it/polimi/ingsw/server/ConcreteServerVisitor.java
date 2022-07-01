package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.*;

import java.io.IOException;

public class ConcreteServerVisitor implements VisitorServer{

    private ServerController serverController;
    private final ClientHandler player;
    private final MatchManager matchManager;

    /**
     * Class Constructor
     */

    public ConcreteServerVisitor(MatchManager matchManager, ClientHandler player) {
         this.matchManager = matchManager;
         this.player = player;
    }

    /**
     * Setter for the Server Controller
     * @param serverController the Server Controller
     */

    public void setServerController(ServerController serverController) {
        this.serverController = serverController;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(NumOfPlayersResponse numOfPlayersResponse) {
        if(numOfPlayersResponse.getNumOfPlayers() > 3 || numOfPlayersResponse.getNumOfPlayers() < 2){
            this.serverController.errorOnPlayerNumber(player);
        } else
            this.serverController.setNumOfPlayers(numOfPlayersResponse.getNumOfPlayers(),player);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(GameModeResponse gameModeResponse)  {
        try {
            this.serverController.setGameMode(gameModeResponse.getExpertMode(), this.player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(SetUsername setUsername)  {
       this.serverController.setUsername(setUsername.getUsername(), player);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(AssistantCardResponse assistantCardResponse) {
        this.serverController.setAssistantCard(assistantCardResponse.getChosenCard());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(CloudResponse cloudResponse) {
        this.serverController.chooseCloud(cloudResponse.getCloudIndex());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(MatchResponse matchResponse) throws IOException {
        this.matchManager.matchResponse(matchResponse.isNewMatch(), this.player);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(MoveMNResponse moveMNResponse) {
        this.serverController.moveMotherNature(moveMNResponse.getPosition());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(MovePawnResponse movePawnResponse) {
        this.serverController.moveStudent(movePawnResponse.getStudentIndex(), movePawnResponse.getIslandIndex(), movePawnResponse.isMoveOnSchoolBoard());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(SelectColorResponse selectColorResponse) {
        this.serverController.selectColor(selectColorResponse.getColor());
    }

    @Override
    public void visitMessage(SelectEntrancePawnResponse selectEntrancePawnResponse) {
        this.serverController.selectPawn(selectEntrancePawnResponse.getPawnIndex());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(SelectIslandResponse selectIslandResponse) {
        this.serverController.selectIsland(selectIslandResponse.getIslandIndex());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(SelectPawnResponse selectPawnResponse) {
        this.serverController.selectPawn(selectPawnResponse.getPawnIndex());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(UseCharacterCard useCharacterCard) {
        this.serverController.useCharacterCard(useCharacterCard.getCharacter());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(WizardCardResponse wizardCardResponse) {
        this.serverController.setWizard(wizardCardResponse.getChosenWizard());
    }

    public void visitMessage(Quit quit){

    }
}