package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.GameModeResponse;
import it.polimi.ingsw.messages.clienttoserver.NumOfPlayersResponse;
import it.polimi.ingsw.messages.clienttoserver.SetUsername;
import it.polimi.ingsw.messages.servertoclient.NumOfPlayersRequest;

public interface VisitorServer {

    void visitMessage(NumOfPlayersResponse numOfPlayersResponse);
    void visitMessage(GameModeResponse gameModeResponse);
    void visitMessage(SetUsername username);
}
