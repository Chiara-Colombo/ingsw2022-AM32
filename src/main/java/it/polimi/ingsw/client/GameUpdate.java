package it.polimi.ingsw.client;

import it.polimi.ingsw.model.GamePhase;

import java.io.Serializable;

public class GameUpdate implements Serializable {
    private final int numOfPlayers;
    private final boolean expertMode;
    private final String currentPlayer;
    private final GamePhase gamePhase;

    public GameUpdate(int numOfPlayers, boolean expertMode, String currentPlayer, GamePhase gamePhase) {
        this.numOfPlayers = numOfPlayers;
        this.expertMode = expertMode;
        this.currentPlayer = currentPlayer;
        this.gamePhase = gamePhase;
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
}
