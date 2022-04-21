package it.polimi.ingsw.model;

import java.util.Collection;
import java.util.Iterator;

public interface ISchoolBoard {

    Iterator<Pawn> getProfessors();
    Iterator<Pawn> getStudentsOfColor(PawnsColors color);

    Iterator<Pawn> getStudentsInEntrance();

}
