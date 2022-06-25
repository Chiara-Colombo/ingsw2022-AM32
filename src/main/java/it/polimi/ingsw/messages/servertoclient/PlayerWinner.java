package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.Player;

import java.io.IOException;

/**
 * Message that notify the winner
 */

public class PlayerWinner extends ServerMessage{
    private final String winner, reason;

    public PlayerWinner(String winner, String reason) {
        this.reason = reason;
        this.winner = winner;
    }

    @Override
    public String TypeOfMessage() {
        return "PlayerWinner";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public String getWinner() {
        return winner;
    }

    public String getReason() {
        return reason;
    }
}
