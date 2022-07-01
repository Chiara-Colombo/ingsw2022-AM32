package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.PawnsColors;

import java.io.IOException;
import java.util.ArrayList;

public class SelectEntrancePawnRequest extends ServerMessage {
    private final ArrayList<PawnsColors> entranceStudents;

    public SelectEntrancePawnRequest(ArrayList<PawnsColors> entranceStudents) {
        this.entranceStudents = entranceStudents;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectEntrancePawnRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public ArrayList<PawnsColors> getEntranceStudents() {
        return this.entranceStudents;
    }
}
