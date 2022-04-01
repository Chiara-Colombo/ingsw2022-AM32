package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMagicMailManHandled;

public class MagicMailmanEffectHandler implements EffectHandler{
    private final IMagicMailManHandled card;

    public MagicMailmanEffectHandler(IMagicMailManHandled card) {
        this.card = card;
    }


    /*Methods that apply the MagicMailMan Character card Effects*/

    @Override
    public void applyEffect() {
        this.card.setExtraMotherNatureMovements(2);
    }

    @Override
    public void removeEffect() {
        this.card.resetExtraMotherNatureMovements();
    }
}
