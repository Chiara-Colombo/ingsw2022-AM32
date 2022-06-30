package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMinstrelHandled;

import java.util.ArrayList;

public class MinstrelEffectHandler implements EffectHandler {

    private final IMinstrelHandled player;
    /*
    private final ArrayList<Pawn> pawns;
     */
    private final ArrayList<Pawn> diningPawns;
    private final ArrayList<Pawn> entrancePawns;



    public MinstrelEffectHandler(IMinstrelHandled player,ArrayList<Pawn> diningPawns, ArrayList<Pawn> entrancePawns) {
        this.player = player;
        this.diningPawns = diningPawns;
        this.entrancePawns = entrancePawns;

    }

    @Override
    public void applyEffect() {
        /*
        this.player.fromDiningToEntrance(pawns);
        this.player.fromEntranceToDining(pawns);
         */
        this.player.swapPawns(diningPawns,entrancePawns);
    }

    @Override
    public void removeEffect() {

    }
}
