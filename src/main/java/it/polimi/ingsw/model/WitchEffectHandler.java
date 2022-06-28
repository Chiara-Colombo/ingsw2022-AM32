package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMushroomManHandled;
import it.polimi.ingsw.model.Handled.IWitchHandled;

import java.util.ArrayList;

public class WitchEffectHandler implements EffectHandler {

    private final IWitchHandled game;
    private final PawnsColors color;

    /**
     * Class Constructor
     */

    public WitchEffectHandler(IWitchHandled game, PawnsColors color) {
        this.game = game;
        this.color = color;
    }

    /**
     * Method that applies Witch Card effect
     * (it removes students from dining room)
     */

    @Override
    public void applyEffect() {
        this.game.removeStudentsFromdiningRoom(color);
    }

    /**
     * Method that removes Witch Card effect
     */

    @Override
    public void removeEffect() {

    }
}
