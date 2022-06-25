package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 *Message that notify to the other players the chosen card
 */

public class ChosenWizardCard extends ServerMessage{
    @Override
    public String TypeOfMessage() {
        return "ChosenWizardCard";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}