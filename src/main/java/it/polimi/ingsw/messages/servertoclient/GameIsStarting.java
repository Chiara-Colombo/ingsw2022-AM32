package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.io.Serializable;

/**
 * Message that notify that the Game is Starting
 */
public class GameIsStarting extends ServerMessage{

    public String typeOfMessage = "GameIsStarting";



    @Override
    public String TypeOfMessage() {
        return this.typeOfMessage;
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}
