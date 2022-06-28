package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IJesterHandled;

import java.util.ArrayList;

public class JesterEffectHandler implements EffectHandler {

    private final IJesterHandled player;
    private final ArrayList<Pawn> pawns;

    public JesterEffectHandler(IJesterHandled player, ArrayList<Pawn> pawns){
        this.player = player;
        this.pawns = pawns;
    }

    @Override
    public void applyEffect() {
        this.player.addStudentsInEntrance(pawns);
    }

    @Override
    public void removeEffect() {

    }
}
