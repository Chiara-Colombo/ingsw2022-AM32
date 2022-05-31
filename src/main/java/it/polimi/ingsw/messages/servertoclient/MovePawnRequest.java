package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that asks to select the position where to move the selected pawn
 */
public class MovePawnRequest extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "MovePawnRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}