package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.Player;

import java.io.IOException;
import java.io.Serializable;

public class PlayerChoosingWizard extends ServerMessage{

    public String typeOfMessage = "WizardCardRequest";

    public String nickname;

    public PlayerChoosingWizard(String nickname){
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
