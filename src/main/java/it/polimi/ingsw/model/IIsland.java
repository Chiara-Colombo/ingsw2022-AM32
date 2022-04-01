package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;


public interface IIsland {
    Optional<Tower> getTower();
    int getGroupOfIslands();
    boolean isNoEntry();
    Iterator<Pawn> getStudents();
}


