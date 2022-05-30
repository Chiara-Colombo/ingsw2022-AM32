package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.util.ArrayList;

public class CloudRequest extends ServerMessage{
    private final ArrayList<Integer> validClouds;

    public CloudRequest(ArrayList<Integer> validClouds) {
        this.validClouds = validClouds;
    }

    @Override
    public String TypeOfMessage() {
        return "CloudRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public ArrayList<Integer> getValidClouds() {
        return this.validClouds;
    }
}