package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that returns the position where the player wants to put Mother Nature
 */

public class MoveMNResponse extends ClientMessage{

    private final int position;

    public MoveMNResponse(int position){
        this.position = position;
    }

    @Override
    public String TypeOfMessage() {
        return "MoveMNResponse";
    }

    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public int getPosition() {
        return this.position;
    }
}
