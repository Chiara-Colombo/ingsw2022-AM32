package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

public class MoveMNRequest extends ServerMessage{

    private int movements;

    public MoveMNRequest(int movements){
        this.movements = movements;
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