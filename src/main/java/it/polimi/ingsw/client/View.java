package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.Wizards;

import java.util.ArrayList;

public interface View {

    /**
     * Method that shows the player turn in the Action Phase
     * @param nickname player
     */

    void showActionPhaseTurn(String nickname);

    /**
     * Method that show the Assistant Card chosen by the other/others player/players
     */

    void showAssistantCardChosen();

    /**
     * Method that shows the request for an Assistant Card
     * @param availableCards List of Assistant Cards that can be used
     */

    void showAssistantCardRequest(ArrayList<AssistantCard> availableCards);

    /**
     * Method that shows the Assistant Card chosen by one player to the others
     * @param assistantsCardUpdate Assistant Card chosen by a player
     */

    void showAssistantsCardUpdate(AssistantsCardUpdate assistantsCardUpdate);

    /**
     * Method that shows the board update after every action on the board
     * @param boardUpdate it contains all the board parameters that are shown
     */

    void showBoardUpdate(BoardUpdate boardUpdate);

    /**
     * Method that shows the Character Card used by a player
     * @param character Character Card used
     * @param username player who used the Character Card
     */

    void showCharacterCardUsed(Characters character, String username);

    /**
     * Method that show the Wizard Card chosen by a player
     */

    void showChosenWizardCard();

    /**
     * Method that shows the request to choose a Cloud among the availables
     * @param validClouds List of Clouds that have pawns on them
     */

    void showCloudRequest(ArrayList<Integer> validClouds);


    /**
     * Method that shows that someone has lost connection
     */

    void showConnectionLost();

    /**
     * Method that shows that there is an Error
     * @param message it contains the error
     */

    void showErrorMessage(String message);

    /**
     * Method that shows that there is an error on pawn position
     */

    void showErrorOnPawnPosition();

    /**
     * Method that shows the fact that a game is starting
     */

    void showGameStartingView();

    /**
     * Method that shows the possibility to create a new match or partecipate to an existing one
     */

    void showMatchRequest();

    /**
     * Method that shows the update about the position of Mother Nature on the board
     */

    void showMNPositionUpdate();

    /**
     * Method that shows to a player the question about the island where he wants to put Mother Nature
     * @param movements the number of movements that Mother Nature has to do
     * @param validIndexes the available indexes of the island where Mother Nature can be moved
     *                     given by the Assistant Card chosen
     */

    void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes);

    /**
     * Method that shows the request to move a pawn
     * @param numOfPawns index of the pawn that the player want to move
     */

    void showMovePawnRequest(int numOfPawns);

    /**
     * Method that shows that there aren't available matches
     */

    void showNoMatchAvailable();

    /**
     * Method that shows that a player hasn't Enough Coins to use a Character Card
     */

    void showNotEnoughCoins();

    /**
     * Method that shows which player is in the planning phase
     * @param nickname Username of the player
     */

    void showPlanningPhaseTurn(String nickname);

    /**
     * Method that shows that another player is choosing a Wizard Card
     */

    void showPlayerChoosingWizard();

    /**
     * Method that shows to the first player if he wants to play the Expert Mode or not
     */

    void showRequestExpertMode();

    /**
     * Method that shows to the first player to choose the number of player of the match
     */

    void showRequestNumOfPlayers();

    /**
     * Method that shows to a player that he has to insert the username
     */

    void showRequestUsername();

    /**
     * Method that shows the updates on the School Board of a player
     */

    void showSchoolBoardUpdate();

    /**
     * Method that shows to a player that he has to choose the color of a pawn
     * @param selectColorRequest message that contains the available colors
     */

    void showSelectColorRequest(SelectColorRequest selectColorRequest);

    void showSelectEntrancePawnRequest(SelectEntrancePawnRequest selectEntrancePawnRequest);

    /**
     * Method that shows to a player that he has to choose an island
     * @param selectIslandRequest message that contains the available indexes ot the islands
     */

    void showSelectIslandRequest(SelectIslandRequest selectIslandRequest);

    /**
     * Method that shows to a player that he has to choose a pawn
     * @param selectPawnRequest message that contains the available pawns
     */

    void showSelectPawnRequest(SelectPawnRequest selectPawnRequest);

    /**
     * Method that show to the player that he has to wait for other players
     */

    void showWaitingView();

    /**
     * Method that shows to all participants who is the winner and the reason
     * @param winner the username of the winner player
     * @param reason the reason of the winning
     */

    void showWinnerMessage(String winner, String reason);

    /**
     * Method that shows to a player that he has to choose a Wizard Card
     * @param validWizards the available wizards
     */

    void showWizardCardRequest(ArrayList<Wizards> validWizards);

    /**
     * Method that shows that an Action Phase turn ends
     */

    void showYourActionPhaseTurnEnds();

    /**
     * Method that shows that a Planning phase turn Ends
     */

    void showYourPlanningPhaseTurnEnds();
}
