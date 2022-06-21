package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

public class Ping  extends ServerMessage {
    @Override
    public String TypeOfMessage() {
        return "PingMessage";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {

    }
}
