package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

public interface VisitorClient {

    /**
     * Method that activate the view that shows the Action phase turn message
     * @param actionPhaseTurn message that says that action phase of a player is starting
     */

    void visitMessage(ActionPhaseTurn actionPhaseTurn);

    /**
     * Method that activates the view that shows the Assistant Card Chosen message
     * @param assistantCardChosen message that says witch Assistant Card has been chosen by a player
     */

    void visitMessage(AssistantCardChosen assistantCardChosen);

    /**
     * Method that activates the view that shows the Assistant Card Invalid message
     * @param assistantCardInvalid message that says that the chosen Assistant Card isn't valid
     */

    void visitMessage(AssistantCardInvalid assistantCardInvalid);

    /**
     * Method that activates the view that shows the Assistant Card Request message
     * @param assistantCardRequest message that asks to choose an Assistant Card
     */

    void visitMessage(AssistantCardRequest assistantCardRequest);

    /**
     * Method that activates the view that shows the Assistant Card Update message
     * @param assistantsCardUpdate message that shows the update of the Assistant Cards of a player
     */

    void visitMessage(AssistantsCardUpdate assistantsCardUpdate);

    /**
     * Method that activates the view that shows the Board Update
     * @param boardUpdate message that shows the changes on tha Board
     */

    void visitMessage(BoardUpdate boardUpdate);

    /**
     * Method that activates the view that shows the Character Card Error message
     * @param characterCardError message that notifies that there was an error when a player used a Character Card
     */

    void visitMessage(CharacterCardError characterCardError);

    /**
     * Method That activates the view that shows the Used Character Cards
     * @param characterCardUsed message that notifies which Character Card has been used
     */

    void visitMessage(CharacterCardUsed characterCardUsed);
    void visitMessage(ChosenWizardCard chosenWizardCard);
    void visitMessage(CloudRequest cloudRequest);
    void visitMessage(CoinsUpdate coinsUpdate);
    void visitMessage(ConnectionLost connectionLost);
    void visitMessage(ConnectionRefused connectionRefused);
    void visitMessage(ErrorOnPawnResponse errorOnPawnResponse);
    void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber);
    void visitMessage(GameIsStarting gameIsStarting);
    void visitMessage(GameModeRequest gameModeRequest);
    void visitMessage(MatchRequest matchRequest);
    void visitMessage(MNPositionUpdate mnPositionUpdate);
    void visitMessage(MoveMNRequest moveMNRequest);
    void visitMessage(MovePawnRequest movePawnRequest);
    void visitMessage(NoMatchAvailable noMatchAvailable);
    void visitMessage(NotEnoughCoins notEnoughCoins);
    void visitMessage(NumOfPlayersRequest numOfPlayersRequest);
    void visitMessage(PlanningPhaseTurn planningPhaseTurn);
    void visitMessage(PlayerChoosingWizard playerChoosingWizard);
    void visitMessage(PlayerDisconnected playerDisconnected);
    void visitMessage(PlayerWinner playerWinner);
    void visitMessage(RequestUsername requestUsername);
    void visitMessage(SchoolBoardUpdate schoolBoardUpdate);
    void visitMessage(SelectColorRequest selectColorRequest);
    void visitMessage(SelectIslandRequest selectIslandRequest);
    void visitMessage(SelectPawnRequest selectPawnRequest);
    void visitMessage(UsernameNotAssigned usernameNotAssigned);
    void visitMessage(WaitingForPlayers waitingForPlayers);
    void visitMessage(WizardCardRequest wizardCardRequest);
    void visitMessage(YourActionPhaseTurnEnds yourActionPhaseTurnEnds);
    void visitMessage(YourPlanningPhaseTurnEnds yourPlanningPhaseTurnEnds);
}
