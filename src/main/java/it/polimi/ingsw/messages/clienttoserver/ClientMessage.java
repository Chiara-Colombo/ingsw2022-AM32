package it.polimi.ingsw.messages.clienttoserver;


import it.polimi.ingsw.client.VisitorClient;
import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;
import java.io.Serializable;

public abstract class ClientMessage implements Serializable {

    public abstract String TypeOfMessage();

    public abstract void accept(VisitorServer serverVisitor) throws IOException;

}
