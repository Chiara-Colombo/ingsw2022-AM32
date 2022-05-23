package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ICentaurHandled;

public class CentaurEffectHandler implements EffectHandler{
    private final int groupOfIslands;
    private final ICentaurHandled board;

    /**
     * Constructor of CentaurEffectHandler Class
     */
    public CentaurEffectHandler(int groupOfIslands, ICentaurHandled board) {
        this.board = board;
        this.groupOfIslands = groupOfIslands;
    }


    /*Methods that apply the Centaur Character card Effects*/

    /**
     * Method that applies Centaur Character card Effects
     */
    @Override
    public void applyEffect() {
        this.board.setTowerInfluenceForIslands(this.groupOfIslands, 0);
    }

    /**
     * Method that removes Centaur Character card Effects
     */
    @Override
    public void removeEffect() {
        this.board.setTowerInfluenceForIslands(this.groupOfIslands, 1);
    }
}
