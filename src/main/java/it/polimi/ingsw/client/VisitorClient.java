package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.servertoclient.*;

public interface VisitorClient {

    void visitMessage(NumOfPlayersRequest numOfPlayersRequest);
    void visitMessage(GameModeRequest gameModeRequest);
    void visitMessage(RequestUsername requestUsername);
    void visitMessage(GameIsStarting gameIsStarting);
    void visitMessage(UsernameCorrectlyAssigned usernameCorrectlyAssigned);
    void visitMessage( UsernameNotAssigned usernameNotAssigned);
    void visitMessage( ErrorOnPlayerNumber errorOnPlayerNumber);
    void visitMessage( WaitingForPlayers waitingForPlayers);



}
