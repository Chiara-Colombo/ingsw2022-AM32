package it.polimi.ingsw.model;

import java.util.Iterator;

/**
 * interface of a cloud
 */
public interface ICloud {

    boolean isEmpty();
    Iterator<Pawn> getStudents();

}
