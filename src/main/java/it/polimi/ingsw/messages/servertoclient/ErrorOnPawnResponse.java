package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * Method that notifies that there is an error with the chosen pawn
 */

public class ErrorOnPawnResponse extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "ErrorOnPawnResponse";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}
