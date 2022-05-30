package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;


public interface IIsland {
    /**
     * Method which returns the island tower if it's present
     */
    Optional<Tower> getTower();

    int getGroupOfIslands();
    int getIndex();
    boolean isNoEntry();

    /**
     * Method which returns an iterator of the students pawns on the island
     */
    Iterator<Pawn> getStudents();
}


