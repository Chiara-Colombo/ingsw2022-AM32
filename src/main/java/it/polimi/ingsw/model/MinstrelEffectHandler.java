package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMinstrelHandled;

public class MinstrelEffectHandler implements EffectHandler {

    private final IMinstrelHandled player;
    private final Pawn pawn;



    public MinstrelEffectHandler(IMinstrelHandled player, Pawn pawn) {
        this.player = player;
        this.pawn = pawn;
    }

    @Override
    public void applyEffect() {
        this.player.fromDiningToEntrance(pawn);
        this.player.fromEntranceToDining(pawn);
    }

    @Override
    public void removeEffect() {

    }
}
