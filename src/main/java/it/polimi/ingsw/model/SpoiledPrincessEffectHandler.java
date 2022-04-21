package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ISpoiledPrincessHandled;

public class SpoiledPrincessEffectHandler implements EffectHandler{
    private final Pawn student;
    private final ISpoiledPrincessHandled player;

    public SpoiledPrincessEffectHandler(Pawn student, ISpoiledPrincessHandled player) {
        this.player = player;
        this.student = student;
    }



    /*Methods that applies the Spoiled Princess Character card Effects*/
    @Override
    public void applyEffect() {
        this.player.addStudentInDiningRoom(this.student);
    }

    @Override
    public void removeEffect() {

    }
}
