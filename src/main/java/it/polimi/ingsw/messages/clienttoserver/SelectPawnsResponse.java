package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;
import java.util.ArrayList;

public class SelectPawnsResponse extends ClientMessage{

    private final ArrayList<Integer> pawnsindex;

    SelectPawnsResponse(ArrayList<Integer> pawnsindex){
        this.pawnsindex = pawnsindex;
    }

    public ArrayList<Integer> getPawnsindex() {
        return pawnsindex;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectPawnsResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {
        serverVisitor.visitMessage(this);
    }
}
