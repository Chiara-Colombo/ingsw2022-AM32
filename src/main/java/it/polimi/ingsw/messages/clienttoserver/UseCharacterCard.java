package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

public class UseCharacterCard extends ClientMessage{
    @Override
    public String TypeOfMessage() {
        return "UseCharacterCard";
    }

    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }
}
