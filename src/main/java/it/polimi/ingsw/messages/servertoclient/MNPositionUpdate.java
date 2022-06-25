package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that notifies the update of Mother Nature position
 */

public class MNPositionUpdate extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "MNPositionUpdate";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}