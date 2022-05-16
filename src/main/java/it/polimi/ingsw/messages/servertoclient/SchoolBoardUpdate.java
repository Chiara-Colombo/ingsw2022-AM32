package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

public class SchoolBoardUpdate extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "SchoolBoardUpdate";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}