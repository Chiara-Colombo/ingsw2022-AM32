package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message used to check the stability of the connection
 */

public class Pong extends ClientMessage {
    @Override
    public String TypeOfMessage() {
        return "PongMessage";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {

    }
}
