package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;
import java.io.Serializable;

/**
 * Message that return the Username chosen by the player
 */

public class SetUsername extends ClientMessage {

    public String username;
    public String typeOfMessage = "SetUsername";



    public SetUsername(String username){
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String TypeOfMessage() {
        return this.typeOfMessage;
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }
}