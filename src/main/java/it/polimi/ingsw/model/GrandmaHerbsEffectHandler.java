package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.INoEntry;

public class GrandmaHerbsEffectHandler implements EffectHandler{
    private final INoEntry island;

    public GrandmaHerbsEffectHandler(INoEntry island) {
        this.island = island;
    }

    /**
     * Method which implements the GrandmaHerbs characterCard effects
     */

    @Override
    public void applyEffect() {
        this.island.setNoEntry(true);
    }

    @Override
    public void removeEffect() {
        this.island.setNoEntry(false);
    }
}
