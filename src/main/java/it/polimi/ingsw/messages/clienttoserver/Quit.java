package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that notifies that a player has left the game voluntarily
 */

public class Quit extends ClientMessage {
    public String typeOfMessage = "Quit";

    @Override
    public String TypeOfMessage() {
        return this.typeOfMessage;
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }
}
