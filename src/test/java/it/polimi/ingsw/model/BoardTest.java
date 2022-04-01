package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

class BoardTest {

    public final Board gameBoard=new Board();
    public final  Board gameBoard2=new Board();
    ArrayList<Island> ex;

    @Test
    void MotherNature(){

        System.out.println(gameBoard.getMotherNature());
        System.out.println(gameBoard2.getMotherNature());
        assertTrue(-1<gameBoard.getMotherNature() && gameBoard.getMotherNature()<12);
        assertTrue(-1<gameBoard2.getMotherNature() && gameBoard2.getMotherNature()<12);

    }

    @Test
    void Islandsize(){
        assertEquals(12,gameBoard.getIslandSize());
        assertEquals(12,gameBoard2.getIslandSize());
        }



    @Test
    void moveMotherNature(){

        int index=10;

        System.out.println(gameBoard.getMotherNature());
        System.out.println(gameBoard.getMotherNature()+index);
        System.out.println(gameBoard.moveMotherNature(index));

    }



    @Test
    void Bag(){
        System.out.println(gameBoard.isBagEmpty());
    }

/*
    @Test
    void Hashmap(){
        Map<PawnsColors,ArrayList<Integer>> bag=new HashMap<>();

            ArrayList<Integer> values=new ArrayList<>(Arrays.asList(1,2,3,4));

        bag.put(PawnsColors.YELLOW,values);
        bag.put(PawnsColors.PINK,values);
        bag.put(PawnsColors.BLUE,values);
        bag.put(PawnsColors.GREEN,values);
        System.out.println(bag);

    }*/
}