package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that notify the earned coins in the round
 */
public class CoinsUpdate extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "CoinsUpdate";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}