package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.clienttoserver.*;
import it.polimi.ingsw.messages.servertoclient.NumOfPlayersRequest;

public interface VisitorServer {

    void visitMessage(NumOfPlayersResponse numOfPlayersResponse);
    void visitMessage(GameModeResponse gameModeResponse);
    void visitMessage(SetUsername username);
    void visitMessage(AssistantCardResponse assistantCardResponse);
    void visitMessage(CloudResponse cloudResponse);
    void visitMessage(MoveMNResponse moveMNResponse);
    void visitMessage(MovePawnResponse movePawnResponse);
    void visitMessage(SelectPawnResponse selectPawnResponse);
    void visitMessage(UseCharacterCard useCharacterCard);
    void visitMessage(WizardCardResponse wizardCardResponse);
}
