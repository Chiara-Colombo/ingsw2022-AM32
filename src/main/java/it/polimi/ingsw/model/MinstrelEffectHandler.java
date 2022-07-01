package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMinstrelHandled;

import java.util.ArrayList;

public class MinstrelEffectHandler implements EffectHandler {

    private final IMinstrelHandled player;
    private final ArrayList<Pawn> diningPawns;
    private final ArrayList<Pawn> entrancePawns;


    /**
     * CLass Constructor
     */

    public MinstrelEffectHandler(IMinstrelHandled player,ArrayList<Pawn> diningPawns, ArrayList<Pawn> entrancePawns) {
        this.player = player;
        this.diningPawns = diningPawns;
        this.entrancePawns = entrancePawns;

    }

    /**
     * Method that applies Minstrel Card Effect
     */

    @Override
    public void applyEffect() {

        this.player.swapPawns(diningPawns,entrancePawns);
    }

    /**
     * Method that remove Minstrel Class Effect
     */

    @Override
    public void removeEffect() {

    }
}
