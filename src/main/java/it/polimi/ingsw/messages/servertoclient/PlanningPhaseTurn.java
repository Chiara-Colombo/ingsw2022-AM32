package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

public class PlanningPhaseTurn extends ServerMessage{

    public String nickname;

    public PlanningPhaseTurn(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String TypeOfMessage() {
        return "PlanningPhaseTurn";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}