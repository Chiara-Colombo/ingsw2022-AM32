package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.io.Serializable;

/**
 * Message that notifies that there is an error on player number
 */

public class ErrorOnPlayerNumber extends ServerMessage{

    public String typeOfMessage = "ErrorOnPlayerNumber";

    @Override
    public String TypeOfMessage() {
        return this.typeOfMessage;
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}
