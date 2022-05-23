package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ISpoiledPrincessHandled;

public class SpoiledPrincessEffectHandler implements EffectHandler{
    private final Pawn student;
    private final ISpoiledPrincessHandled player;

    /**
     * Constructor of SpoiledPrincessEffectHandler Class
     */
    public SpoiledPrincessEffectHandler(Pawn student, ISpoiledPrincessHandled player) {
        this.player = player;
        this.student = student;
    }



    /*Methods that applies the Spoiled Princess Character card Effects*/
    /**
     * Method that applies SpoiledPrincessMan Character card Effects
     */
    @Override
    public void applyEffect() {
        this.player.addStudentInDiningRoom(this.student);
    }

    /**
     * Method that removes SpoiledPrincessMan Character card Effects
     */
    @Override
    public void removeEffect() {

    }
}
