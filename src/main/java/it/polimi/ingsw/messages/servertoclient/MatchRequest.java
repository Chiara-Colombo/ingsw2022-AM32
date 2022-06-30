package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that asks to the user if he wants to create a new game or participate an existing one
 */

public class MatchRequest extends ServerMessage {
    @Override
    public String TypeOfMessage() {
        return "MatchRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}