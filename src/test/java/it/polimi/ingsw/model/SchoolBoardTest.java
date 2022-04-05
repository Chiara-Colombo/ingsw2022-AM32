package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SchoolBoardTest {

    @Test
    void addToDiningRoom() {

        SchoolBoard schoolBoard = new SchoolBoard();

        Pawn student1 = new Pawn(PawnsColors.getRandom());
        Pawn student2 = new Pawn(PawnsColors.getRandom());
        Pawn student3 = new Pawn(PawnsColors.getRandom());
        Pawn student4 = new Pawn(PawnsColors.getRandom());
        Pawn student5 = new Pawn(PawnsColors.getRandom());

        schoolBoard.addStudentToDiningRoom(student1);
        schoolBoard.addStudentToDiningRoom(student2);
        schoolBoard.addStudentToDiningRoom(student3);
        schoolBoard.addStudentToDiningRoom(student4);
        schoolBoard.addStudentToDiningRoom(student5);

        for (PawnsColors color : PawnsColors.values()) {
            System.out.println(schoolBoard.getDiningRoom().get(color).size());
        }

    }

    @Test
    void addStudentInEntrance() {
        SchoolBoard schoolBoard = new SchoolBoard();
        int studentToadd = 4;
        int j = 0;
        for (int i = 0; i < studentToadd; i++) {
            Pawn student = new Pawn(PawnsColors.getRandom());
            schoolBoard.addStudent(student);
        }
        Iterator<Pawn> entrance = schoolBoard.getStudentsInEntrance();
        while (entrance.hasNext()) {
            j++;
            entrance.next();
        }
        assertEquals(studentToadd, j);

    }

    @Test
    void addProfessor() {
        SchoolBoard schoolBoard = new SchoolBoard();
        int professorToadd = 4;
        int j = 0;
        for (int i = 0; i < professorToadd; i++) {
            Pawn professor = new Pawn(PawnsColors.getRandom());
            schoolBoard.addProfessor(professor);
        }
        Iterator<Pawn> professorTable = schoolBoard.getProfessors();
        while (professorTable.hasNext()) {
            j++;
            professorTable.next();
        }
        assertEquals(professorToadd, j);

    }
}

