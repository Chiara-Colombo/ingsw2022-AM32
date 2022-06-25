package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that notifies that the Planning Phase turn ends
 */

public class YourPlanningPhaseTurnEnds extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "YourPlanningPhaseTurnEnds";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}