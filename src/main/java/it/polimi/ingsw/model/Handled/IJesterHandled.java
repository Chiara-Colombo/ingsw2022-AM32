package it.polimi.ingsw.model.Handled;

import it.polimi.ingsw.model.Pawn;

import java.util.ArrayList;

public interface IJesterHandled {
    void addStudentInEntrance(Pawn pawn);
    Pawn removeStudent(int studentIndex);
}
