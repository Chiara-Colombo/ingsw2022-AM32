package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Message that asks to select the position where to move the selected pawn
 */

public class MovePawnRequest extends ServerMessage{
    private final int numOfPawns;

    public MovePawnRequest(int numOfPawns) {
        this.numOfPawns = numOfPawns;
    }

    @Override
    public String TypeOfMessage() {
        return "MovePawnRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public int getNumOfPawns() {
        return this.numOfPawns;
    }
}