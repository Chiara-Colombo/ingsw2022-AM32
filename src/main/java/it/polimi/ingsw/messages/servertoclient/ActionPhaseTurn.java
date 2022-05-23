package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import javax.swing.*;
import java.io.IOException;

public class ActionPhaseTurn extends ServerMessage{

    public String nickname;

    public ActionPhaseTurn(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String TypeOfMessage() {
        return "ActionPhaseTurn";
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);
    }
}