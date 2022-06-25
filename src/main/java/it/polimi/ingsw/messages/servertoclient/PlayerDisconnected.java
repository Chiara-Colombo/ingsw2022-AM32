package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Method that notify that a player has disconnected
 */

public class PlayerDisconnected extends ServerMessage{
    private final String player;

    public PlayerDisconnected(String player) {
        this.player = player;
    }

    @Override
    public String TypeOfMessage() {
        return "PlayerDisconnected";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public String getPlayer() {
        return this.player;
    }
}
