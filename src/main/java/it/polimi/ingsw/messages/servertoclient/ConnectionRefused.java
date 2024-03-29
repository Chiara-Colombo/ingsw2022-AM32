package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Method that notifies that the connection is refused
 */

public class ConnectionRefused extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "ConnectionRefused";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}