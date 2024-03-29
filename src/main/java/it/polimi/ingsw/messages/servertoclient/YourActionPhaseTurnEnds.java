package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that notifies that the Action Phase Turn ends
 */

public class YourActionPhaseTurnEnds extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "YourActionPhaseTurnEnds";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}