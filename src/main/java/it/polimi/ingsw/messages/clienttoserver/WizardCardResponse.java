package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.model.Wizards;
import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Message that return the chosen Wizard Card from the player
 */

public class WizardCardResponse extends ClientMessage{
    private final Wizards chosenWizard;

    public WizardCardResponse(Wizards chosenWizard) {
        this.chosenWizard = chosenWizard;
    }

    @Override
    public String TypeOfMessage() {
        return "WizardCardResponse";
    }

    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public Wizards getChosenWizard() {
        return this.chosenWizard;
    }
}
