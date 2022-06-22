package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.INoEntry;

public class GrandmaHerbsEffectHandler implements EffectHandler{
    private final INoEntry island;

    /**
     * Constructor of CentaurEffectHandler Class
     */
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

    /**
     * Method which removes the GrandmaHerbs characterCard effects
     */
    @Override
    public void removeEffect() {

    }

    public void restoreIsland() {
        this.island.setNoEntry(false);
    }
}
