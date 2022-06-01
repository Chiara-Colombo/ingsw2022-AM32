package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    @Test
    void isEmpty(){
        Pawn student = new Pawn(PawnsColors.getRandom());
        Cloud cloud = new Cloud();
        assertTrue(cloud.isEmpty());
        cloud.addStudent(student);
        assertFalse(cloud.isEmpty());
    }

    @Test
    void numofStudents(){

        Cloud cloud = new Cloud();
        for(int i = 0; i < 3; i++) {
            Pawn student = new Pawn(PawnsColors.getRandom());
            cloud.addStudent(student);
        }
        assertEquals(3,cloud.studentsSize());
    }
}