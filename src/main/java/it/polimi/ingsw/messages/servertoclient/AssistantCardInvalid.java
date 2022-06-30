package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that notifies that the chosen Assistant Card isn't valid
 */

public class AssistantCardInvalid extends ServerMessage {
    @Override
    public String TypeOfMessage() {
        return "AssistantCardInvalid";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}
