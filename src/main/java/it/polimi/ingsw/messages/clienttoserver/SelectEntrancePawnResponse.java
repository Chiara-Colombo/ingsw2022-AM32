package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

public class SelectEntrancePawnResponse extends ClientMessage {
    private final int pawnIndex;

    public SelectEntrancePawnResponse(int pawnIndex) {
        this.pawnIndex = pawnIndex;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectEntrancePawnResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public int getPawnIndex() {
        return this.pawnIndex;
    }
}
