package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that notifies that the server lost the connection with a player
 */

public class ConnectionLost extends ServerMessage {
    @Override
    public String TypeOfMessage() {
        return "ConnectionLost";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}
