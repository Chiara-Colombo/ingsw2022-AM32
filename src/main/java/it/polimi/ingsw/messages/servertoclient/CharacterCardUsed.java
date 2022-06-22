package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.Characters;

import java.io.IOException;

public class CharacterCardUsed extends ServerMessage {
    private final String username;
    private final Characters character;

    public CharacterCardUsed(String username, Characters character) {
        this.username = username;
        this.character = character;
    }

    @Override
    public String TypeOfMessage() {
        return "CharacterCardUsed";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public Characters getCharacter() {
        return this.character;
    }

    public String getUsername() {
        return this.username;
    }
}
