package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IFarmerHandled;

public class FarmerEffectHandler implements EffectHandler{
    private final IFarmerHandled player;

    public FarmerEffectHandler(IFarmerHandled player) {
        this.player = player;
    }



    /*Methods that applies the Farmer Character card Effects*/

    @Override
    public void applyEffect() {
        this.player.setExtraStudent(1);
    }

    @Override
    public void removeEffect() {
        this.player.setExtraStudent(0);
    }
}