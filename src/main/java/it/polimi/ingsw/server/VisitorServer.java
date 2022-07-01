package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.*;
import it.polimi.ingsw.messages.servertoclient.MatchRequest;
import it.polimi.ingsw.messages.servertoclient.NumOfPlayersRequest;
import it.polimi.ingsw.messages.servertoclient.SelectIslandRequest;

import java.io.IOException;

public interface VisitorServer {

    /**
     * Method that gets the Number of players according to NumOfPlayersResponse
     * if the input is valid
     * @param numOfPlayersResponse response that contains the number of player chosen by the first user
     */

    void visitMessage(NumOfPlayersResponse numOfPlayersResponse);

    /**
     * Method that gets the Game mode according to the GameModeResponse
     * @param gameModeResponse response that contains the Game mode (expert mode or simple mode)
     */

    void visitMessage(GameModeResponse gameModeResponse);

    /**
     * Method that gets the username of a player
     * @param username the response that contains the username
     */

    void visitMessage(SetUsername username);

    /**
     * Method that gets the Assistant Card chosen by a player
     * @param assistantCardResponse message that contains the chosen assistant card
     */

    void visitMessage(AssistantCardResponse assistantCardResponse);

    /**
     * Method that chooses the cloud according to the CloudResponse
     * @param cloudResponse message that contains the chosen Cloud
     */

    void visitMessage(CloudResponse cloudResponse);

    /**
     * Method that get if the player wants to create a new match
     * @param matchResponse message that contains the fact that the player wants to create a new match
     *                      or participate an existing match
     * @throws IOException
     */

    void visitMessage(MatchResponse matchResponse) throws IOException;

    /**
     * Method that moves Mother Nature according to moveMNResponse
     * @param moveMNResponse message that contains the position where the player want to put Mother Nature
     */

    void visitMessage(MoveMNResponse moveMNResponse);

    /**
     * Method that moves a Pawn according to MovePawnResponse
     * @param movePawnResponse message that contains the which pawn the player wants to move
     *                         and where he wants to put the pawn
     */

    void visitMessage(MovePawnResponse movePawnResponse);

    /**
     * Method that get the color chosen by the player
     * @param selectColorResponse message that contains the chosen color
     */

    void visitMessage(SelectColorResponse selectColorResponse);

    void visitMessage(SelectEntrancePawnResponse selectEntrancePawnResponse);

    /**
     * Method that gets the island chosen by the player
     * @param selectIslandResponse message that contains tha chosen islands
     */

    void visitMessage(SelectIslandResponse selectIslandResponse);

    /**
     * Method that gets the pawn selected by a player
     * @param selectPawnResponse  message that contains the pawn selected by the player
     */

    void visitMessage(SelectPawnResponse selectPawnResponse);

    /**
     * Method that gets the Character Card that the player want to use
     * @param useCharacterCard message that contains the selected Character Card
     */

    void visitMessage(UseCharacterCard useCharacterCard);

    /**
     * Method that gets the Wizard Card that the player want to use
     * @param wizardCardResponse message that contains the selected Wizard Card
     */

    void visitMessage(WizardCardResponse wizardCardResponse);

    void visitMessage(Quit quit);
}
