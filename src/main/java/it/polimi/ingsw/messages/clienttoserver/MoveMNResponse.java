package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

public class MoveMNResponse extends ClientMessage{

    private final int movements;

    public MoveMNResponse(int movements){
        this.movements = movements;
    }

    @Override
    public String TypeOfMessage() {
        return "MoveMNResponse";
    }

    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public int getMovements() {
        return movements;
    }
}
