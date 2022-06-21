package it.polimi.ingsw.client;

import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.GamePhase;

import java.io.Serializable;
import java.util.ArrayList;

public class GameUpdate implements Serializable {
    private final int numOfPlayers;
    private final boolean expertMode;
    private final String currentPlayer;
    private final GamePhase gamePhase;
    private final ArrayList<Characters> validCharacters;

    public GameUpdate(int numOfPlayers, boolean expertMode, String currentPlayer, GamePhase gamePhase, ArrayList<Characters> validCharacters) {
        this.numOfPlayers = numOfPlayers;
        this.expertMode = expertMode;
        this.currentPlayer = currentPlayer;
        this.gamePhase = gamePhase;
        this.validCharacters = validCharacters;
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

    public ArrayList<Characters> getValidCharacters() {
        return this.validCharacters;
    }
}
