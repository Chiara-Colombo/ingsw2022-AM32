package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.Characters;

import java.io.IOException;
import java.util.ArrayList;

public class SelectIslandRequest extends ServerMessage {
    private final Characters character;
    private final ArrayList<Integer> validIndexes;

    public SelectIslandRequest(Characters character, ArrayList<Integer> validIndexes) {
        this.character = character;
        this.validIndexes = validIndexes;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectIslandRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public ArrayList<Integer> getValidIndexes() {
        return this.validIndexes;
    }

    public Characters getCharacter() {
        return this.character;
    }
}
