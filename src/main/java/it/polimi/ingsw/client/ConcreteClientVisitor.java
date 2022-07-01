package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

public class ConcreteClientVisitor implements VisitorClient{

    public View view;

    /**
     * Class constructor
     * @param view interface with showing methods
     */

    public ConcreteClientVisitor(View view) {
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(ActionPhaseTurn actionPhaseTurn) {
        this.view.showActionPhaseTurn(actionPhaseTurn.getNickname());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(AssistantCardChosen assistantCardChosen) {
        this.view.showAssistantCardChosen();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(AssistantCardInvalid assistantCardInvalid) {
        this.view.showErrorMessage("Non puoi selezionare questa carta!");
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(AssistantCardRequest assistantCardRequest) {
        this.view.showAssistantCardRequest(assistantCardRequest.getAvailableCards());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(AssistantsCardUpdate assistantsCardUpdate) {
        this.view.showAssistantsCardUpdate(assistantsCardUpdate);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(BoardUpdate boardUpdate) {
        this.view.showBoardUpdate(boardUpdate);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(CharacterCardError characterCardError) {
        this.view.showErrorMessage(characterCardError.getError());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(CharacterCardUsed characterCardUsed) {
        this.view.showCharacterCardUsed(characterCardUsed.getCharacter(), characterCardUsed.getUsername());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(ChosenWizardCard chosenWizardCard) {
        this.view.showChosenWizardCard();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(CloudRequest cloudRequest) {
        this.view.showCloudRequest(cloudRequest.getValidClouds());
    }


    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(ConnectionLost connectionLost) {
        this.view.showConnectionLost();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(ConnectionRefused connectionRefused) {
        this.view.showErrorMessage("Raggiunto il massimo limite di giocatori");
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(ErrorOnPawnResponse errorOnPawnResponse) {
        this.view.showErrorOnPawnPosition();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber) {
        this.view.showErrorMessage("Non è possibile creare una partita con questo numero di giocatori! Ritenta! ");
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(GameIsStarting gameIsStarting) { this.view.showGameStartingView(); }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(GameModeRequest gameModeRequest) { this.view.showRequestExpertMode(); }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(MatchRequest matchRequest) {
        this.view.showMatchRequest();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(MNPositionUpdate mnPositionUpdate) {
        this.view.showMNPositionUpdate();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(MoveMNRequest moveMNRequest) {
        this.view.showMoveMNRequest(moveMNRequest.getMovements(), moveMNRequest.getValidIndexes());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(MovePawnRequest movePawnRequest) {
        this.view.showMovePawnRequest(movePawnRequest.getNumOfPawns());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(NoMatchAvailable noMatchAvailable) {
        this.view.showNoMatchAvailable();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(NotEnoughCoins notEnoughCoins){
        this.view.showErrorMessage("Non hai abbastanza monete!");
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(NumOfPlayersRequest numOfPlayersRequest) {
        this.view.showRequestNumOfPlayers();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(PlanningPhaseTurn planningPhaseTurn) {
        this.view.showPlanningPhaseTurn(planningPhaseTurn.getNickname());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(PlayerChoosingWizard playerChoosingWizard) {
        this.view.showPlayerChoosingWizard();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(PlayerDisconnected playerDisconnected) {
        this.view.showErrorMessage(playerDisconnected.getPlayer() + " si è disconnesso!");
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(PlayerWinner playerWinner) {
        this.view.showWinnerMessage(playerWinner.getWinner(), playerWinner.getReason());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(RequestUsername requestUsername) {
        this.view.showRequestUsername();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(SchoolBoardUpdate schoolBoardUpdate) {
        this.view.showSchoolBoardUpdate();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(SelectColorRequest selectColorRequest) {
        this.view.showSelectColorRequest(selectColorRequest);
    }

    @Override
    public void visitMessage(SelectEntrancePawnRequest selectEntrancePawnRequest) {
        this.view.showSelectEntrancePawnRequest(selectEntrancePawnRequest);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(SelectIslandRequest selectIslandRequest) {
        this.view.showSelectIslandRequest(selectIslandRequest);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(SelectPawnRequest selectPawnRequest) {
        this.view.showSelectPawnRequest(selectPawnRequest);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(UsernameNotAssigned usernameNotAssigned) {
        this.view.showErrorMessage("Lo username deve essere univoco!"); }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(WaitingForPlayers waitingForPlayers) { this.view.showWaitingView(); }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(WizardCardRequest wizardCardRequest){
        this.view.showWizardCardRequest(wizardCardRequest.getValidWizards());
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(YourActionPhaseTurnEnds yourActionPhaseTurnEnds) {
        this.view.showYourActionPhaseTurnEnds();
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void visitMessage(YourPlanningPhaseTurnEnds yourPlanningPhaseTurnEnds) {
        this.view.showYourPlanningPhaseTurnEnds();
    }
}


