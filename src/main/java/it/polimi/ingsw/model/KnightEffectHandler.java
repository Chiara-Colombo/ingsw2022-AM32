package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IKnightHandled;

public class KnightEffectHandler implements EffectHandler{
    private final IKnightHandled player;

    public KnightEffectHandler(IKnightHandled player) {
        this.player = player;
    }


    /*Methods that apply the Knight Character card Effects*/

    @Override
    public void applyEffect() {
        this.player.setExtraInfluence(2);
    }

    @Override
    public void removeEffect() {
        this.player.resetExtraInfluence();
    }
}

