package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.util.ArrayList;

public class MoveMNRequest extends ServerMessage{

    private final int movements;
    private final ArrayList<Integer> validIndexes;

    public MoveMNRequest(int movements, ArrayList<Integer> validIndexes){
        this.movements = movements;
        this.validIndexes = validIndexes;
    }

    public ArrayList<Integer> getValidIndexes() {
        return this.validIndexes;
    }

    public int getMovements() {
        return movements;
    }

    @Override
    public String TypeOfMessage() {
        return "MoveMNRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}