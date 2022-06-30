package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.*;
import it.polimi.ingsw.messages.servertoclient.MatchRequest;
import it.polimi.ingsw.messages.servertoclient.NumOfPlayersRequest;
import it.polimi.ingsw.messages.servertoclient.SelectIslandRequest;

import java.io.IOException;

public interface VisitorServer {

    void visitMessage(NumOfPlayersResponse numOfPlayersResponse);
    void visitMessage(GameModeResponse gameModeResponse);
    void visitMessage(SetUsername username);
    void visitMessage(AssistantCardResponse assistantCardResponse);
    void visitMessage(CloudResponse cloudResponse);
    void visitMessage(MatchResponse matchResponse) throws IOException;
    void visitMessage(MoveMNResponse moveMNResponse);
    void visitMessage(MovePawnResponse movePawnResponse);
    void visitMessage(SelectColorResponse selectColorResponse);
    void visitMessage(SelectIslandResponse selectIslandResponse);
    void visitMessage(SelectPawnResponse selectPawnResponse);
    void visitMessage(UseCharacterCard useCharacterCard);
    void visitMessage(WizardCardResponse wizardCardResponse);
    void visitMessage(Quit quit);
    void visitMessage(SelectPawnsResponse selectPawnsResponse);
    void visitMessage(SelectColorsResponse selectColorsResponse);
}
