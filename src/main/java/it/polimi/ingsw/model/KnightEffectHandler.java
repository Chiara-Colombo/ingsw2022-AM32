package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IKnightHandled;

public class KnightEffectHandler implements EffectHandler{
    private final IKnightHandled player;

    /**
     * Constructor of KnightEffectHandler Class
     */

    public KnightEffectHandler(IKnightHandled player) {
        this.player = player;
    }


    @Override
    /**
     * Method that sets the extrainfluence for the selected player
     */

    public void applyEffect() {
        this.player.setExtraInfluence(2);
    }


    @Override
    /**
     * Method that removes the extra influence for the player
     */

    public void removeEffect() {
        this.player.resetExtraInfluence();
    }
}

