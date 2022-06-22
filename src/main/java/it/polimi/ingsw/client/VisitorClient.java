package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

public interface VisitorClient {
    void visitMessage(ActionPhaseTurn actionPhaseTurn);
    void visitMessage(AssistantCardChosen assistantCardChosen);
    void visitMessage(AssistantCardRequest assistantCardRequest);
    void visitMessage(AssistantsCardUpdate assistantsCardUpdate);
    void visitMessage(BoardUpdate boardUpdate);
    void visitMessage(ChosenWizardCard chosenWizardCard);
    void visitMessage(CloudRequest cloudRequest);
    void visitMessage(CoinsUpdate coinsUpdate);
    void visitMessage(ConnectionLost connectionLost);
    void visitMessage(ConnectionRefused connectionRefused);
    void visitMessage(ErrorOnPawnResponse errorOnPawnResponse);
    void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber);
    void visitMessage(GameIsStarting gameIsStarting);
    void visitMessage(GameModeRequest gameModeRequest);
    void visitMessage(MNPositionUpdate mnPositionUpdate);
    void visitMessage(MoveMNRequest moveMNRequest);
    void visitMessage(MovePawnRequest movePawnRequest);
    void visitMessage(NotEnoughCoins notEnoughCoins);
    void visitMessage(NumOfPlayersRequest numOfPlayersRequest);
    void visitMessage(PlanningPhaseTurn planningPhaseTurn);
    void visitMessage(PlayerChoosingWizard playerChoosingWizard);
    void visitMessage(PlayerDisconnected playerDisconnected);
    void visitMessage(PlayerWinner playerWinner);
    void visitMessage(RequestUsername requestUsername);
    void visitMessage(SchoolBoardUpdate schoolBoardUpdate);
    void visitMessage(SelectPawnRequest selectPawnRequest);
    void visitMessage(UsernameNotAssigned usernameNotAssigned);
    void visitMessage(WaitingForPlayers waitingForPlayers);
    void visitMessage(WizardCardRequest wizardCardRequest);
    void visitMessage(YourActionPhaseTurnEnds yourActionPhaseTurnEnds);
    void visitMessage(YourPlanningPhaseTurnEnds yourPlanningPhaseTurnEnds);
}
