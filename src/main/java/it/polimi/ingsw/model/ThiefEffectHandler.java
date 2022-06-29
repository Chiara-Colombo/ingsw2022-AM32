package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IThiefHandled;

public class ThiefEffectHandler implements EffectHandler {

    private final IThiefHandled game;
    private final PawnsColors color;

    /**
     * Class Constructor
     */

    public ThiefEffectHandler(IThiefHandled game, PawnsColors color) {
        this.game = game;
        this.color = color;
    }

    /**
     * Method that applies Witch Card effect
     * (it removes students from dining room)
     */

    @Override
    public void applyEffect() {
        this.game.removeStudentsFromDiningRoom(color);
    }

    /**
     * Method that removes Witch Card effect
     */

    @Override
    public void removeEffect() {

    }
}
