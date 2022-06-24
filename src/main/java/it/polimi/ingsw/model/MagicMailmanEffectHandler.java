package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMagicMailManHandled;

public class MagicMailmanEffectHandler implements EffectHandler{
    private final IMagicMailManHandled card;

    /**
     * Constructor of MagicMailmanEffectHandler Class
     */
    public MagicMailmanEffectHandler(IMagicMailManHandled card) {
        this.card = card;
    }




    /**
     * Method that applies MagicMailman Character card Effects
     */
    @Override
    public void applyEffect() {
        this.card.setExtraMotherNatureMovements(2);
    }

    /**
     * Method that removes MagicMailman Character card Effects
     */
    @Override
    public void removeEffect() {
        this.card.resetExtraMotherNatureMovements();
    }
}
