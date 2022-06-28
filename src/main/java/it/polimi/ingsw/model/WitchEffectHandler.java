package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMushroomManHandled;
import it.polimi.ingsw.model.Handled.IWitchHandled;

import java.util.ArrayList;

public class WitchEffectHandler implements EffectHandler {

    private final IWitchHandled game;
    private final PawnsColors color;

    public WitchEffectHandler(IWitchHandled game, PawnsColors color) {
        this.game = game;
        this.color = color;
    }

    @Override
    public void applyEffect() {
        this.game.removeStudentsFromdiningRoom(color);
    }

    @Override
    public void removeEffect() {

    }
}
