package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.AssistantCard;

import java.io.IOException;

public class AssistantsCardUpdate extends ServerMessage{

    public AssistantCard assistantCard;
    public String nickname;

    public AssistantsCardUpdate(AssistantCard assistantCard, String nickname){
        this.assistantCard = assistantCard;
        this.nickname = nickname;
    }

    public AssistantCard getAssistantCard() {
        return assistantCard;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String TypeOfMessage() {
        return "AssistantCardUpdate";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}