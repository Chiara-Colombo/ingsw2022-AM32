package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.PawnsColors;

import java.io.IOException;
import java.util.ArrayList;

public class SelectColorRequest extends ServerMessage {
    private final Characters character;
    private final ArrayList<PawnsColors> values;
    public SelectColorRequest(Characters character, ArrayList<PawnsColors> values) {
        this.character = character;
        this.values = values;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectColorRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public ArrayList<PawnsColors> getValues() {
        return this.values;
    }

    public Characters getCharacter() {
        return this.character;
    }
}
