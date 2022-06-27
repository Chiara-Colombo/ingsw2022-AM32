package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

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
