package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMushroomManHandled;

public class MushroomManEffectHandler implements EffectHandler{
    private final PawnsColors color;
    private final IMushroomManHandled game;

    /**
     * Constructor of MooshroomManEffectHandler Class
     */
    public MushroomManEffectHandler(PawnsColors color, IMushroomManHandled game) {
        this.color = color;
        this.game = game;
    }




    /**
     * Method that applies MooshroomMan Character card Effects
     */
    @Override
    public void applyEffect() {
        this.game.setInfluenceForColor(this.color, 0);
    }

    /**
     * Method that removes MooshroomMan Character card Effects
     */
    @Override
    public void removeEffect() {
        this.game.setInfluenceForColor(this.color, 1);
    }
}
