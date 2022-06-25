package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that returns the pawn selected by a player
 */

public class SelectPawnResponse extends ClientMessage{
    private final int pawnIndex;

    public SelectPawnResponse(int pawnIndex) {
        this.pawnIndex = pawnIndex;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectPawnResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public int getPawnIndex() {
        return this.pawnIndex;
    }
}
