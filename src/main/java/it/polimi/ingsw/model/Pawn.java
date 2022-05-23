package it.polimi.ingsw.model;

public class Pawn {

    private PawnsColors color;

    /**
     * Pawn object Constructor
     */
    Pawn(PawnsColors color){
        this.color=color;

    }

    /**
     * Method that gets the pawn object color
     * @return value of pawnsColors for the selected pawn
     */
    public PawnsColors getColor() {
        return color;
    }

}
