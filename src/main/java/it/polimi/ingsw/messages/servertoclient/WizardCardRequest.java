package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.io.Serializable;

public class WizardCardRequest extends ServerMessage{

    public String typeOfMessage = "WizardCardRequest";

    public String nickname;

    public WizardCardRequest(String nickname){
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public String TypeOfMessage() {
        return this.typeOfMessage;
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visitMessage(this);
    }
}
