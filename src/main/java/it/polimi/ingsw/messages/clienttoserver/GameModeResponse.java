package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

public class GameModeResponse extends ClientMessage {

    boolean expertmode;
    
    public String typeOfMessage = "GameModeResponse";


    public GameModeResponse(boolean expertmode){
        this.expertmode=expertmode;
    }

    public boolean getExpertMode() {
        return expertmode;
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
