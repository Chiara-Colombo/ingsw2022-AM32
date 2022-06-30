package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that shows that there aren't available matches
 */

public class NoMatchAvailable extends ServerMessage {
    @Override
    public String TypeOfMessage() {
        return "NoMatchAvailable";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}
