package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;

/**
 * Method that return the cloud chosen by a player
 */

public class CloudResponse extends ClientMessage{
    private final int cloudIndex;

    public CloudResponse(int cloudIndex) {
        this.cloudIndex = cloudIndex;
    }

    @Override
    public String TypeOfMessage() {
        return "CloudResponse";
    }

    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }

    public int getCloudIndex() {
        return this.cloudIndex;
    }
}
