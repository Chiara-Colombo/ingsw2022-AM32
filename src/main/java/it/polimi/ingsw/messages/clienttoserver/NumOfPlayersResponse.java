package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;
import java.io.Serializable;

public class NumOfPlayersResponse extends ClientMessage {

    int numOfPlayers;
    public String typeOfMessage = "NumOfPlayersResponse";


    public NumOfPlayersResponse(int numOfPlayers){
        this.numOfPlayers=numOfPlayers;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
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
