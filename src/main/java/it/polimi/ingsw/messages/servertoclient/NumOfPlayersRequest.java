package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.io.Serializable;

public class NumOfPlayersRequest extends ServerMessage{

    public String typeOfMessage = "NumOfPlayersRequest";


    @Override
    public String TypeOfMessage() {
        return this.typeOfMessage;
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visitMessage(this);
    }
}
