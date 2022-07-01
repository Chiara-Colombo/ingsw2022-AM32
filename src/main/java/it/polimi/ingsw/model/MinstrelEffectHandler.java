package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMinstrelHandled;

import java.util.ArrayList;

public class MinstrelEffectHandler implements EffectHandler {

    private final IMinstrelHandled player;
    private final ArrayList<PawnsColors> diningRoomPawns;
    private final ArrayList<Integer> entrancePawns;


    /**
     * CLass Constructor
     */

    public MinstrelEffectHandler(IMinstrelHandled player, ArrayList<Integer> entrancePawns, ArrayList<PawnsColors> diningRoomPawns) {
        this.player = player;
        this.entrancePawns = entrancePawns;
        this.diningRoomPawns = diningRoomPawns;

    }

    /**
     * Method that applies Minstrel Card Effect
     */

    @Override
    public void applyEffect() {
        this.player.swapPawns(this.entrancePawns, this.diningRoomPawns);
    }

    /**
     * Method that remove Minstrel Class Effect
     */

    @Override
    public void removeEffect() {

    }
}
