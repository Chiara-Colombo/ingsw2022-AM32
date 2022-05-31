package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that asks to select a pawn
 */
public class SelectPawnRequest extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "SelectPawnRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}