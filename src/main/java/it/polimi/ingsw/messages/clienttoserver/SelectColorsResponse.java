package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.server.VisitorServer;

import java.io.IOException;
import java.util.ArrayList;

public class SelectColorsResponse extends ClientMessage{

    private final ArrayList<PawnsColors> colors;

    public SelectColorsResponse(ArrayList<PawnsColors> colors){
        this.colors = colors;
    }

    @Override
    public String TypeOfMessage() {
        return "SelectColorsResponse";
    }

    @Override
    public void accept(VisitorServer serverVisitor) throws IOException {

    }

    public ArrayList<PawnsColors> getColor() {
        return this.colors;
    }

}
