package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;


public interface IIsland {
    /**
     * Method which returns the island tower if it's present
     */

    Optional<Tower> getTower();

    /**
     * Method that get the index of an island
     * @return the index
     */

    int getIndex();

    /**
     * Method that says if there is a ban tile
     * @return True if is a ban tile
     *          False is it is not
     */
    boolean isNoEntry();

    /**
     * Method which returns an iterator of the students pawns on the island
     */

    Iterator<Pawn> getStudents();
}


