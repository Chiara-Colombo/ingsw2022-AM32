package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Ping message used to check the stability of the connection
 */

public class Ping  extends ServerMessage {
    @Override
    public String TypeOfMessage() {
        return "PingMessage";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {

    }
}
