package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.PawnsColors;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Message that asks to select a pawn
 */

public class SelectPawnRequest extends ServerMessage {
    private final boolean multipleRequests;
    private final Characters character;
    private final ArrayList<PawnsColors> validPawns;

    public SelectPawnRequest(boolean multipleRequests, Characters character, ArrayList<PawnsColors> validPawns) {
        this.multipleRequests = multipleRequests;
        this.character = character;
        this.validPawns = validPawns;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectPawnRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public ArrayList<PawnsColors> getValidPawns() {
        return this.validPawns;
    }

    public Characters getCharacter() {
        return this.character;
    }

    public boolean isMultipleRequests() {
        return this.multipleRequests;
    }
}