package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

public class AssistantCardResponse extends ClientMessage{
    private final AssistantCard chosenCard;

    public AssistantCardResponse(AssistantCard chosenCard) {
        this.chosenCard = chosenCard;
    }

    @Override
    public String TypeOfMessage() {
        return "AssistantCardResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public AssistantCard getChosenCard() {
        return this.chosenCard;
    }
}
