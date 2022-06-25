package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.io.Serializable;

/**
 * Message that notify that the Username has been correctly assigned
 */

public class UsernameCorrectlyAssigned extends ServerMessage{

    public String typeOfMessage = "UsernameCorrectlyAssigned";


    @Override
    public String TypeOfMessage() {
        return this.typeOfMessage;
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
    }
}
