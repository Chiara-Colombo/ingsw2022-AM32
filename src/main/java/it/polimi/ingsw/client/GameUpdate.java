package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.GamePhase;
import it.polimi.ingsw.model.PawnsColors;

import java.io.Serializable;
import java.util.ArrayList;

public class GameUpdate implements Serializable {
    private final int numOfPlayers, grandmaHerbsNoEntryTiles;
    private final boolean expertMode;
    private final String currentPlayer;
    private final GamePhase gamePhase;
    private final ArrayList<Characters> validCharacters;
    private final ArrayList<PawnsColors> monkStudents, spoiledPrincessStudents, jesterStudents;

    /**
     * Class constructor
     *
     * @param numOfPlayers              number of players
     * @param grandmaHerbsNoEntryTiles  tiles affected by Grandma Herbs
     * @param expertMode                says if the expert mode is on
     * @param currentPlayer             th current player
     * @param gamePhase                 the phase of the match
     * @param validCharacters           valid characters for this game
     * @param monkStudents              students on monk card
     * @param spoiledPrincessStudents   Students of spoiled princess card
     * @param jesterStudents            Students of jester card
     */

    public GameUpdate(int numOfPlayers,
                      int grandmaHerbsNoEntryTiles,
                      boolean expertMode,
                      String currentPlayer,
                      GamePhase gamePhase,
                      ArrayList<Characters> validCharacters,
                      ArrayList<PawnsColors> monkStudents,
                      ArrayList<PawnsColors> spoiledPrincessStudents, ArrayList<PawnsColors> jesterStudents) {
        this.numOfPlayers = numOfPlayers;
        this.grandmaHerbsNoEntryTiles = grandmaHerbsNoEntryTiles;
        this.expertMode = expertMode;
        this.currentPlayer = currentPlayer;
        this.gamePhase = gamePhase;
        this.validCharacters = validCharacters;
        this.monkStudents = monkStudents;
        this.spoiledPrincessStudents = spoiledPrincessStudents;
        this.jesterStudents = jesterStudents;
    }

    /**
     * Getter for the number of players
     * @return the number of players
     */

    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    /**
     * Method that says if it is expert mode
     * @return true if it is expert mode
     *          false if it isn't expert mode
     */

    public boolean isExpertMode() {
        return this.expertMode;
    }

    /**
     * Getter for the current player
     * @return the current player
     */
    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Getter for the game phase
     * @return the game phase
     */

    public GamePhase getGamePhase() {
        return this.gamePhase;
    }

    /**
     * Getter for the tiles with ban of grandma herbs
     * @return tiles with ban
     */

    public int getGrandmaHerbsNoEntryTiles() {
        return this.grandmaHerbsNoEntryTiles;
    }

    /**
     * Getter for Valid Characters
     * @return valid characters
     */

    public ArrayList<Characters> getValidCharacters() {
        return this.validCharacters;
    }

    /**
     * Getter for the students for the monk card
     * @return list of pawns
     */

    public ArrayList<PawnsColors> getMonkStudents() {
        return this.monkStudents;
    }

    /**
     * Getter for the students for the spoiled princess card
     * @return list of pawns
     */

    public ArrayList<PawnsColors> getSpoiledPrincessStudents() {
        return this.spoiledPrincessStudents;
    }

    public ArrayList<PawnsColors> getJesterStudents() { return this.jesterStudents; }
}
