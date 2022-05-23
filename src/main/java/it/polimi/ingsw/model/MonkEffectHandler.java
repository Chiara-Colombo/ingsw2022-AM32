package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMonkHandled;

public class MonkEffectHandler implements EffectHandler{
    private final IMonkHandled board;
    private final Pawn student;
    private final int island;

    /**
     * Constructor of MonkEffectHandler Class
     */
    public MonkEffectHandler(IMonkHandled board, Pawn student, int island) {
        this.board = board;
        this.student = student;
        this.island = island;
    }

    /*Methods that apply the Monk Character card Effects*/

    /**
     * Method that applies MonkMailman Character card Effects
     */
    @Override
    public void applyEffect() {
        this.board.setStudentOnIsland(this.student, this.island);
    }

    /**
     * Method that removes MonkMailman Character card Effects
     */
    @Override
    public void removeEffect() {
        return;
    }
}
