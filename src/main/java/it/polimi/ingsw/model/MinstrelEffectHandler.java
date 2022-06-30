package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMinstrelHandled;

import java.util.ArrayList;

public class MinstrelEffectHandler implements EffectHandler {

    private final IMinstrelHandled player;
    private final ArrayList<Pawn> pawns;



    public MinstrelEffectHandler(IMinstrelHandled player,ArrayList<Pawn> pawns) {
        this.player = player;
        this.pawns = pawns;
    }

    @Override
    public void applyEffect() {
        this.player.fromDiningToEntrance(pawns);
        this.player.fromEntranceToDining(pawns);
    }

    @Override
    public void removeEffect() {

    }
}
