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
     * Method that show the update of Coins
     */

    void showCoinsUpdate();

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
    void showGameStartingView();
    void showMatchRequest();
    void showMNPositionUpdate();
    void showMoveMNRequest(int movements, ArrayList<Integer> validIndexes);
    void showMovePawnRequest(int numOfPawns);
    void showNoMatchAvailable();
    void showNotEnoughCoins();
    void showPlanningPhaseTurn(String nickname);
    void showPlayerChoosingWizard();
    void showRequestExpertMode();
    void showRequestNumOfPlayers();
    void showRequestUsername();
    void showSchoolBoardUpdate();
    void showSelectColorRequest(SelectColorRequest selectColorRequest);
    void showSelectIslandRequest(SelectIslandRequest selectIslandRequest);
    void showSelectPawnRequest(SelectPawnRequest selectPawnRequest);
    void showWaitingView();
    void showWinnerMessage(String winner, String reason);
    void showWizardCardRequest(ArrayList<Wizards> validWizards);
    void showYourActionPhaseTurnEnds();
    void showYourPlanningPhaseTurnEnds();
}
