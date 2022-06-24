package it.polimi.ingsw.model;

import java.util.ArrayList;

/**
 * interface for schoolBoard
 */
public interface ISchoolBoard {

    /**
     * method that gets professors
     * @return list of professors
     */

    ArrayList<Pawn> getProfessors();

    /**
     * Method that gets students of a color
     * @param color color of the pawn
     * @return list of pawns
     */

    ArrayList<Pawn> getStudentsOfColor(PawnsColors color);

    /**
     * method that gets students in entrance
     * @return
     */

    ArrayList<Pawn> getStudentsInEntrance();
}
