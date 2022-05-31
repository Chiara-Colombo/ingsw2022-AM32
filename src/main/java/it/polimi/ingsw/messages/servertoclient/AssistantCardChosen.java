package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that notifies to the other players the chosen Assistant Card
 */
public class AssistantCardChosen extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "AssistantCardChosen";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}