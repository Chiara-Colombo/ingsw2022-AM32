package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.AssistantCard;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Message that asks to choose an Assistant Card
 * it contains the drawable Assistant Cards
 */
public class AssistantCardRequest extends ServerMessage{
    private final ArrayList<AssistantCard> availableCards;

    public AssistantCardRequest(ArrayList<AssistantCard> availableCards) {
        this.availableCards = availableCards;
    }

    @Override
    public String TypeOfMessage() {
        return "AssistantCardRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }

    public ArrayList<AssistantCard> getAvailableCards() {
        return this.availableCards;
    }
}