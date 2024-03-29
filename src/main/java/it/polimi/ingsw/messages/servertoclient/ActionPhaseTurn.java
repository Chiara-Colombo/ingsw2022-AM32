package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import javax.swing.*;
import java.io.IOException;

/**
 * Message that notifies that the action Phase turn on a player is starting
 */

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