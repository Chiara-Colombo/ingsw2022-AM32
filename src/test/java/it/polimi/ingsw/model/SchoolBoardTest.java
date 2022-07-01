package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class SchoolBoardTest {
    /**
     * tests the correct behavior of adding students to the dining room
     */
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

    /**
     * tests the correct behavior of adding students to the entrance
     */
    @Test
    void addStudentInEntrance() {
        SchoolBoard schoolBoard = new SchoolBoard();
        int studentToadd = 4;
        for (int i = 0; i < studentToadd; i++) {
            Pawn student = new Pawn(PawnsColors.getRandom());
            schoolBoard.addStudent(student);
        }
        ArrayList<Pawn> entrance = schoolBoard.getStudentsInEntrance();
        assertEquals(studentToadd, entrance.size());
        schoolBoard.removeStudent(0);
        assertEquals(studentToadd - 1, entrance.size());

    }
    /**
     * tests the correct behavior of adding professor to  the schoolboard
     */
    @Test
    void addProfessor() {
        SchoolBoard schoolBoard = new SchoolBoard();
        int professorToadd = 4;
        for (int i = 0; i < professorToadd; i++) {
            Pawn professor = new Pawn(PawnsColors.getRandom());
            schoolBoard.addProfessor(professor);
        }
        ArrayList<Pawn> professorTable = schoolBoard.getProfessors();
        assertEquals(professorToadd, professorTable.size());

    }


}

