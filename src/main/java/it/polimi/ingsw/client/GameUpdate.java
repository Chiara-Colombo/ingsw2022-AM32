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
    private final ArrayList<PawnsColors> monkStudents, spoiledPrincessStudents;

    public GameUpdate(int numOfPlayers,
                      int grandmaHerbsNoEntryTiles,
                      boolean expertMode,
                      String currentPlayer,
                      GamePhase gamePhase,
                      ArrayList<Characters> validCharacters,
                      ArrayList<PawnsColors> monkStudents,
                      ArrayList<PawnsColors> spoiledPrincessStudents) {
        this.numOfPlayers = numOfPlayers;
        this.grandmaHerbsNoEntryTiles = grandmaHerbsNoEntryTiles;
        this.expertMode = expertMode;
        this.currentPlayer = currentPlayer;
        this.gamePhase = gamePhase;
        this.validCharacters = validCharacters;
        this.monkStudents = monkStudents;
        this.spoiledPrincessStudents = spoiledPrincessStudents;
    }

    public int getNumOfPlayers() {
        return this.numOfPlayers;
    }

    public boolean isExpertMode() {
        return this.expertMode;
    }

    public String getCurrentPlayer() {
        return this.currentPlayer;
    }

    public GamePhase getGamePhase() {
        return this.gamePhase;
    }

    public int getGrandmaHerbsNoEntryTiles() {
        return this.grandmaHerbsNoEntryTiles;
    }

    public ArrayList<Characters> getValidCharacters() {
        return this.validCharacters;
    }

    public ArrayList<PawnsColors> getMonkStudents() {
        return this.monkStudents;
    }

    public ArrayList<PawnsColors> getSpoiledPrincessStudents() {
        return this.spoiledPrincessStudents;
    }
}
