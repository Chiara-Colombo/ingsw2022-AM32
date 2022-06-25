package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that return the color of a pawn chosen by a player
 */

public class SelectColorResponse extends ClientMessage {
    private final PawnsColors color;

    public SelectColorResponse(PawnsColors color) {
        this.color = color;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectColorResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public PawnsColors getColor() {
        return this.color;
    }
}
