package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

public interface VisitorClient {
    void visitMessage(ConnectionRefused connectionRefused);
    void visitMessage(NumOfPlayersRequest numOfPlayersRequest);
    void visitMessage(GameModeRequest gameModeRequest);
    void visitMessage(RequestUsername requestUsername);
    void visitMessage(GameIsStarting gameIsStarting);
    void visitMessage(UsernameNotAssigned usernameNotAssigned);
    void visitMessage(ErrorOnPlayerNumber errorOnPlayerNumber);
    void visitMessage(WaitingForPlayers waitingForPlayers);
    void visitMessage(PlayerDisconnected playerDisconnected);
    void visitMessage(PlayerWinner playerWinner);
}
