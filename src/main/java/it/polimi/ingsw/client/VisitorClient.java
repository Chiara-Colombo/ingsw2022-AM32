package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.clienttoserver.AssistantCardResponse;
import it.polimi.ingsw.messages.clienttoserver.CloudResponse;
import it.polimi.ingsw.messages.servertoclient.*;

public interface VisitorClient {
    void visitMessage(ConnectionRefused connectionRefused);
    void visitMessage(NumOfPlayersRequest numOfPlayersRequest);
    void visitMessage(GameModeRequest gameModeRequest);
    void visitMessage(RequestUsername requestUsername);
    void visitMessage(GameIsStarting gameIsStarting);
    void visitMessage(UsernameNotAssigned usernameNotAssigned);
    void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber);
    void visitMessage(WaitingForPlayers waitingForPlayers);
    void visitMessage(PlayerDisconnected playerDisconnected);
    void visitMessage(PlayerWinner playerWinner);
    void visitMessage(WizardCardRequest wizardCardRequest);
    void visitMessage(PlayerChoosingWizard playerChoosingWizard);
    void visitMessage(ActionPhaseTurn actionPhaseTurn);
    void visitMessage(AssistantCardChosen assistantCardChosen);
    void visitMessage(AssistantCardRequest assistantCardRequest);
    void visitMessage(AssistantsCardUpdate assistantsCardUpdate);
    void visitMessage(BoardUpdate boardUpdate);
    void visitMessage(ChosenWizardCard chosenWizardCard);
    void visitMessage(CloudRequest cloudRequest);
    void visitMessage(CoinsUpdate coinsUpdate);
    void visitMessage(MNPositionUpdate mnPositionUpdate);
    void visitMessage(MoveMNRequest moveMNRequest);
    void visitMessage(MovePawnRequest movePawnRequest);
    void visitMessage(PlanningPhaseTurn planningPhaseTurn);
    void visitMessage(SchoolBoardUpdate schoolBoardUpdate);
    void visitMessage(SelectPawnRequest selectPawnRequest);
    void visitMessage(YourActionPhaseTurnEnds yourActionPhaseTurnEnds);
    void visitMessage(YourPlanningPhaseTurnEnds yourPlanningPhaseTurnEnds);
    void visitMessage(ErrorMotherNaturePosition errorMotherNaturePosition);
    void visitMessage(NotEnoughCoins notEnoughCoins);
}
