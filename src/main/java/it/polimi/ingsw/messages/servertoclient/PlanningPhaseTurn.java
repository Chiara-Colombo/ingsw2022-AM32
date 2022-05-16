package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

public class PlanningPhaseTurn extends ServerMessage{

    public String nick;

    public PlanningPhaseTurn(String nick){
        this.nick = nick;
    }

    public String getNick() {
        return nick;
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