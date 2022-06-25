package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.model.Wizards;

import java.util.ArrayList;

/**
 * Message that requests a Wizard Card
 */

public class WizardCardRequest extends ServerMessage{

    private final ArrayList<Wizards> validWizards;

    public WizardCardRequest(ArrayList<Wizards> validWizards){
        this.validWizards = validWizards;
    }

    @Override
    public String TypeOfMessage() {
        return "WizardCardRequest";
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visitMessage(this);
    }

    public ArrayList<Wizards> getValidWizards() {
        return this.validWizards;
    }
}
