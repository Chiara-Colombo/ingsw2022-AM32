package it.polimi.ingsw.model;

import java.util.ArrayList;

public interface ISchoolBoard {
    ArrayList<Pawn> getProfessors();
    ArrayList<Pawn> getStudentsOfColor(PawnsColors color);
    ArrayList<Pawn> getStudentsInEntrance();
}
