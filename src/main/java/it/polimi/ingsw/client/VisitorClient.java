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
     * Method that activates the view that shows the Used Character Cards
     * @param characterCardUsed message that notifies which Character Card has been used
     */

    void visitMessage(CharacterCardUsed characterCardUsed);

    /**
     *  Method that activates the view that shows the Chosen Wizard Card
     * @param chosenWizardCard message that notifies which Wizard Card has been chosen
     */

    void visitMessage(ChosenWizardCard chosenWizardCard);

    /**
     *  Method that activates the view that shows the Cloud Request
     * @param cloudRequest message that ask to choose a cloud within the available ones
     */

    void visitMessage(CloudRequest cloudRequest);

    /**
     *  Method that activates the view that shows the fact that the server lost the connection with a player
     * @param connectionLost message that notifies the lost of connection
     */

    void visitMessage(ConnectionLost connectionLost);

    /**
     *  Method that activates the view that shows the fact that the connection is refused
     * @param connectionRefused message that notifies that the connection is refused
     */

    void visitMessage(ConnectionRefused connectionRefused);

    /**
     * Method that activates the view that shows the fact that there was an error on pawn response
     * @param errorOnPawnResponse message that notifies there was an error with the pawn response
     */

    void visitMessage(ErrorOnPawnResponse errorOnPawnResponse);

    /**
     *  Method that activates the view that shows the fact there was an error on player number
     * @param errorOnPlayerNumber message that notifies that there was an error on player number
     */

    void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber);

    /**
     *  Method that activates the view that shows the fact that the game is starting
     * @param gameIsStarting message that notifies that the game is starting
     */

    void visitMessage(GameIsStarting gameIsStarting);

    /**
     *  Method that activates the view that shows the request to choose the Game Mode
     * @param gameModeRequest message that request the Game Mode (expert or simple)
     */

    void visitMessage(GameModeRequest gameModeRequest);

    /**
     *  Method that activates the view that shows the possibility to create or participate a game
     * @param matchRequest message that asks if the user wants to create a new game ot participate an existing one
     */

    void visitMessage(MatchRequest matchRequest);

    /**
     *  Method that activates the view that shows the new position of Mother Nature
     * @param mnPositionUpdate message that notifies the new position of Mother Nature
     */

    void visitMessage(MNPositionUpdate mnPositionUpdate);

    /**
     *  Method that activates the view that shows the request to move Mother Nature
     * @param moveMNRequest message that notifies the possibility to move Mother Nature
     */

    void visitMessage(MoveMNRequest moveMNRequest);

    /**
     * Method that activates the view that shows the request to move a Pawn
     * @param movePawnRequest message that notifies that the player has to move a pawn
     */

    void visitMessage(MovePawnRequest movePawnRequest);

    /**
     *  Method that activates the view that shows that there aren't available matches
     * @param noMatchAvailable message that notifies that there aren't available matches
     */

    void visitMessage(NoMatchAvailable noMatchAvailable);

    /**
     *  Method that activates the view that shows the player hasn't Enough Coins
     * @param notEnoughCoins message that notifies to a player that he doesn't have Enough Coins
     *                       in order to use a Character Card
     */

    void visitMessage(NotEnoughCoins notEnoughCoins);

    /**
     *  Method that activates the view that shows the request to choose the Number of Players
     * @param numOfPlayersRequest message that ask the first player to choose the number of players of
     *                            the match (2 or 3)
     */

    void visitMessage(NumOfPlayersRequest numOfPlayersRequest);

    /**
     * Method that activates the view that shows the player who is in the Planning Phase
     * @param planningPhaseTurn message that notifies the player who is in the Planning phase
     */

    void visitMessage(PlanningPhaseTurn planningPhaseTurn);

    /**
     * Method that activates the view that shows the fact that another player is choosing a wizard
     * @param playerChoosingWizard message that notifies that another player is choosing a Wizard Card
     */

    void visitMessage(PlayerChoosingWizard playerChoosingWizard);

    /**
     *  Method that activates the view that shows the fact that a player is Disconnected
     * @param playerDisconnected message that notifies that a player disconnected from the game
     */

    void visitMessage(PlayerDisconnected playerDisconnected);

    /**
     * Method that activates the view that shows the Winner of the match
     * @param playerWinner message that notifies the winner
     */

    void visitMessage(PlayerWinner playerWinner);

    /**
     * Method that activates the view that shows the request to write the username
     * @param requestUsername message that asks to set a Username
     */

    void visitMessage(RequestUsername requestUsername);

    /**
     *  Method that activates the view that shows all the updates that happens on School Board
     * @param schoolBoardUpdate message that notifies the updates on School Board
     */

    void visitMessage(SchoolBoardUpdate schoolBoardUpdate);

    /**
     * Method that activates the view that shows the request to select a color
     * @param selectColorRequest message that notifies to tha player that he has to select a Color
     */

    void visitMessage(SelectColorRequest selectColorRequest);

    /**
     * Method that activates the view that shows the request to select a student from the entrance
     * @param selectEntrancePawnRequest message object
     */

    void visitMessage(SelectEntrancePawnRequest selectEntrancePawnRequest);

    /**
     * Method that activates the view that shows the request to select an island
     * @param selectIslandRequest message that notifies to tha player that he has to select an Island
     */

    void visitMessage(SelectIslandRequest selectIslandRequest);

    /**
     * Method that activates the view that shows the request to select a pawn
     * @param selectPawnRequest message that notifies to tha player that he has to select a Pawn
     */

    void visitMessage(SelectPawnRequest selectPawnRequest);

    /**
     * Method that activates the view that shows the fact that the chosen username isn't valid
     * @param usernameNotAssigned message that notifies that the username provided hasn't been accepted
     */

    void visitMessage(UsernameNotAssigned usernameNotAssigned);

    /**
     * Method that activates the view that shows the fact that the players has to wait for other players connection
     * @param waitingForPlayers message that notifies that the player has to wait for other players
     */

    void visitMessage(WaitingForPlayers waitingForPlayers);

    /**
     * Method that activates the view that shows the request to choose a Wizard Card
     * @param wizardCardRequest message that ask to choose a Wizard Card
     */

    void visitMessage(WizardCardRequest wizardCardRequest);

    /**
     *  Method that activates the view that shows  to a player that his Action Phase is ended
     * @param yourActionPhaseTurnEnds message that shows to a player that his Action phase turn is ended
     */

    void visitMessage(YourActionPhaseTurnEnds yourActionPhaseTurnEnds);

    /**
     *  Method that activates the view that shows to a player that his Planning Phase is ended
     * @param yourPlanningPhaseTurnEnds message that shows to a player that his Planning phase turn is ended
     */

    void visitMessage(YourPlanningPhaseTurnEnds yourPlanningPhaseTurnEnds);
}
