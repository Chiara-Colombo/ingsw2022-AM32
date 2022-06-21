package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

public class ConcreteClientVisitor implements VisitorClient{

    public View view;

    public ConcreteClientVisitor(View view) {
        this.view = view;
    }

    @Override
    public void visitMessage(ActionPhaseTurn actionPhaseTurn) {
        this.view.showActionPhaseTurn(actionPhaseTurn.getNickname());
    }
    @Override
    public void visitMessage(AssistantCardChosen assistantCardChosen) {
        this.view.showAssistantCardChosen();
    }
    @Override
    public void visitMessage(AssistantCardRequest assistantCardRequest) {
        this.view.showAssistantCardRequest(assistantCardRequest.getAvailableCards());
    }
    @Override
    public void visitMessage(AssistantsCardUpdate assistantsCardUpdate) {
        this.view.showAssistantsCardUpdate(assistantsCardUpdate);
    }
    @Override
    public void visitMessage(BoardUpdate boardUpdate) {
        this.view.showBoardUpdate(boardUpdate);
    }
    @Override
    public void visitMessage(ChosenWizardCard chosenWizardCard) {
        this.view.showChosenWizardCard();
    }
    @Override
    public void visitMessage(CloudRequest cloudRequest) {
        this.view.showCloudRequest(cloudRequest.getValidClouds());
    }
    @Override
    public void visitMessage(CoinsUpdate coinsUpdate) {
        this.view.showCoinsUpdate();
    }
    @Override
    public void visitMessage(ConnectionLost connectionLost) {
        this.view.showErrorMessage("Connessione con il server persa!");
    }
    @Override
    public void visitMessage(ConnectionRefused connectionRefused) {
        this.view.showErrorMessage("Raggiunto il massimo limite di giocatori");
    }
    @Override
    public void visitMessage(ErrorOnPawnResponse errorOnPawnResponse) {
        this.view.showErrorOnPawnPosition();
    }
    @Override
    public void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber) {
        this.view.showErrorMessage("Non è possibile creare una partita con questo numero di giocatori! Ritenta! ");
    }
    @Override
    public void visitMessage(GameIsStarting gameIsStarting) { this.view.showGameStartingView(); }
    @Override
    public void visitMessage(GameModeRequest gameModeRequest) { this.view.showRequestExpertMode(); }
    @Override
    public void visitMessage(MNPositionUpdate mnPositionUpdate) {
        this.view.showMNPositionUpdate();
    }
    @Override
    public void visitMessage(MoveMNRequest moveMNRequest) {
        this.view.showMoveMNRequest(moveMNRequest.getMovements(), moveMNRequest.getValidIndexes());
    }
    @Override
    public void visitMessage(MovePawnRequest movePawnRequest) {
        this.view.showMovePawnRequest(movePawnRequest.getNumOfPawns());
    }
    @Override
    public void visitMessage(NotEnoughCoins notEnoughCoins){
        this.view.showNotEnoughCoins();
    }
    @Override
    public void visitMessage(NumOfPlayersRequest numOfPlayersRequest) {
        this.view.showRequestNumOfPlayers();
    }
    @Override
    public void visitMessage(PlanningPhaseTurn planningPhaseTurn) {
        this.view.showPlanningPhaseTurn(planningPhaseTurn.getNickname());
    }
    @Override
    public void visitMessage(PlayerChoosingWizard playerChoosingWizard) {
        this.view.showPlayerChoosingWizard();
    }
    @Override
    public void visitMessage(PlayerDisconnected playerDisconnected) {
        this.view.showErrorMessage(playerDisconnected.getPlayer() + " si è disconnesso!");
    }
    @Override
    public void visitMessage(PlayerWinner playerWinner) {
        this.view.showWinnerMessage(playerWinner.getWinner(), playerWinner.getReason());
    }
    @Override
    public void visitMessage(RequestUsername requestUsername) {
        this.view.showRequestUsername();
    }
    @Override
    public void visitMessage(SchoolBoardUpdate schoolBoardUpdate) {
        this.view.showSchoolBoardUpdate();
    }
    @Override
    public void visitMessage(SelectPawnRequest selectPawnRequest) {
        this.view.showSelectPawnRequest();
    }
    @Override
    public void visitMessage(UsernameNotAssigned usernameNotAssigned) { this.view.showErrorMessage("Lo username deve essere univoco!"); }
    @Override
    public void visitMessage(WaitingForPlayers waitingForPlayers) { this.view.showWaitingView(); }
    @Override
    public void visitMessage(WizardCardRequest wizardCardRequest){
        this.view.showWizardCardRequest(wizardCardRequest.getValidWizards());
    }
    @Override
    public void visitMessage(YourActionPhaseTurnEnds yourActionPhaseTurnEnds) {
        this.view.showYourActionPhaseTurnEnds();
    }
    @Override
    public void visitMessage(YourPlanningPhaseTurnEnds yourPlanningPhaseTurnEnds) {
        this.view.showYourPlanningPhaseTurnEnds();
    }
}


