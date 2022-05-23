package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public interface ISchoolBoard {
    ArrayList<Pawn> getProfessors();
    ArrayList<Pawn> getStudentsOfColor(PawnsColors color);
    ArrayList<Pawn> getStudentsInEntrance();
}
