package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that a player sends when he wants to use a Character Card
 */

public class UseCharacterCard extends ClientMessage{
    private final Characters character;

    public UseCharacterCard(Characters character) {
        this.character = character;
    }

    @Override
    public String TypeOfMessage() {
        return "UseCharacterCard";
    }

    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public Characters getCharacter() {
        return this.character;
    }
}
