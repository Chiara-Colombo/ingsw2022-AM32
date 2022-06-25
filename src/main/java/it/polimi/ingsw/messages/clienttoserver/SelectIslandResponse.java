package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that notifies the island selected by a player
 */

public class SelectIslandResponse extends ClientMessage {
    private final int islandIndex;

    public SelectIslandResponse(int islandIndex) {
        this.islandIndex = islandIndex;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectIslandResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public int getIslandIndex() {
        return this.islandIndex;
    }
}
