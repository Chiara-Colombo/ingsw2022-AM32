package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloudTest {

    @Test

    void isEmpty(){
        Pawn student=new Pawn(PawnsColors.getRandom());
        Cloud cloud=new Cloud();
        System.out.println(cloud.isEmpty());
        cloud.addStudent(student);
        System.out.println(cloud.isEmpty());
    }



}