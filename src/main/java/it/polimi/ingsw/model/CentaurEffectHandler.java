package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ICentaurHandled;

public class CentaurEffectHandler implements EffectHandler{
    private final ICentaurHandled board;

    /**
     * Constructor of CentaurEffectHandler Class
     */
    public CentaurEffectHandler(ICentaurHandled board) {
        this.board = board;
    }

    /**
     * Method that applies Centaur Character card Effects
     */
    @Override
    public void applyEffect() {
        this.board.setTowersInfluence(0);
    }

    /**
     * Method that removes Centaur Character card Effects
     */
    @Override
    public void removeEffect() {
        this.board.setTowersInfluence(1);
    }
}
