package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IExtraInfluenceIsland;

public class HeraldEffectHandler implements EffectHandler {
    private final IExtraInfluenceIsland island;

    /**
     * Constructor of HeraldEffectHandler Class
     */

    public HeraldEffectHandler(IExtraInfluenceIsland island) {
        this.island = island;
    }

    /**
     * Method which implements the HeraldEffectHandler characterCard effects
     */

    @Override
    public void applyEffect() {
        this.island.setExtraInfluenceIsland(true);
    }

    /**
     * Method which removes the HeraldEffectHandler characterCard effects
     */

    @Override
    public void removeEffect() {
        this.island.setExtraInfluenceIsland(false);
    }
}
