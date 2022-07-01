package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that asks to a player to choose if he wants to create a new match or participate an existing one
 */
public class MatchResponse extends ClientMessage {
    private final boolean newMatch;

    public MatchResponse(boolean newMatch) {
        this.newMatch = newMatch;
    }

    @Override
    public String TypeOfMessage() {
        return "MatchResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public boolean isNewMatch() {
        return this.newMatch;
    }
}
