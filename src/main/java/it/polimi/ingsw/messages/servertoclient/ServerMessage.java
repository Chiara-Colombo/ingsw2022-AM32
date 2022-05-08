package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.VisitorClient;

import java.io.IOException;
import java.io.Serializable;

public abstract class ServerMessage implements Serializable {


    public abstract String TypeOfMessage();

    public abstract void accept(VisitorClient visitorClient) throws IOException;
}
