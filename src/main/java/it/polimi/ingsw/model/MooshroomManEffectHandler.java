package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMooshroomManHandled;

public class MooshroomManEffectHandler implements EffectHandler{
    private final PawnsColors color;
    private final IMooshroomManHandled game;

    public MooshroomManEffectHandler(PawnsColors color, IMooshroomManHandled game) {
        this.color = color;
        this.game = game;
    }


    /*Methods that apply the MushroomMan Character card Effects*/
    @Override
    public void applyEffect() {
        this.game.setInfluenceForColor(this.color, 0);
    }

    @Override
    public void removeEffect() {
        this.game.setInfluenceForColor(this.color, 1);
    }
}
