package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;

/**
 * message that notifies that a player doesn't have enough coins
 * for use a Character Card
 */

public class NotEnoughCoins extends ServerMessage{
    public String typeOfMessage = "NotEnoughCoins";

    @Override
    public String TypeOfMessage() {
        return this.typeOfMessage;
    }

    @Override
    public void accept(VisitorClient visitorClient) throws IOException {
        visitorClient.visitMessage(this);}
}
