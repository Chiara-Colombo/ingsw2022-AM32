package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IJesterHandled;

import java.util.ArrayList;

public class JesterEffectHandler implements EffectHandler {

    private final IJesterHandled player;
    private final ArrayList<Pawn> pawns;

    /**
     * Class Constructor
     */

    public JesterEffectHandler(IJesterHandled player, ArrayList<Pawn> pawns){
        this.player = player;
        this.pawns = pawns;
    }

    /**
     * Method that applies Jester Card Effect
     * (it adds students in school board entrance)
     */

    @Override
    public void applyEffect() {
        this.player.addStudentsInEntrance(pawns);
    }

    /**
     * Method that remove Jester Class Effect
     */

    @Override
    public void removeEffect() {

    }
}
