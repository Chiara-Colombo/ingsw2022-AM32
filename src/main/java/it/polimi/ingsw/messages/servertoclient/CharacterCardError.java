package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that notifies that there was an error when a player used a Character Card
 */

public class CharacterCardError extends ServerMessage {
    private final String error;

    public CharacterCardError(String error) {
        this.error = error;
    }

    @Override
    public String TypeOfMessage() {
        return "CharacterCardError";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public String getError() {
        return this.error;
    }
}
