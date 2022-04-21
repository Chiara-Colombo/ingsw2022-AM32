package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

class BoardTest {

    public final Board gameBoard=new Board(2);
    public final Board gameBoard2=new Board(2);
    ArrayList<Island> ex;


    /**Set students pawn on a selected cloud and removing one of them, check if the amount of students pawns is correct**/

    @Test
    void setStudentOnCloud(){

        Board gameBoard=new Board(2);
        int studentsToadd=3;
        for(int i=0;i<studentsToadd;i++) {
            Pawn student = new Pawn(PawnsColors.getRandom());
            gameBoard.setStudentOnCloud(student, 1);
        }
        gameBoard.removeStudentFromCloud(1,1);
        assertEquals(2,gameBoard.getClouds().get(1).studentsSize());
        assertEquals(0,gameBoard.getClouds().get(0).studentsSize());

    }

    @Test
    void  getClouds() {
        Board board=new Board(3);
        assertEquals(3,board.getClouds().size());
        Board board1=new Board(2);
        assertEquals(2,board1.getClouds().size());
    }

    /**Check if motherNature initial  value is beetween the setted values 0<x<12**/
    @Test
    void MotherNature(){

        assertTrue(-1<gameBoard.getMotherNature() && gameBoard.getMotherNature()<12);
        assertTrue(-1<gameBoard2.getMotherNature() && gameBoard2.getMotherNature()<12);

    }

    /**Check if after adding motherNature some value the value is beetween the number of the islands**/
    @Test
    void moveMotherNature(){

        int index=16;

        assertTrue(gameBoard.moveMotherNature(index)<12 && gameBoard.moveMotherNature(index)>=0);

        int index2=1;

        assertTrue(gameBoard.moveMotherNature(index2)<12 && gameBoard.moveMotherNature(index2)>=0);



    }


    //initial island size
    @Test
    void Islandsize(){
        assertEquals(12,gameBoard.getIslandSize());
        }





    /**sets students on island and check if the amaunt is correct**/

    @Test
    void setStudentOnIsland(){

        Board gameBoard=new Board(2);
        Pawn student1=new Pawn(PawnsColors.getRandom());
        Pawn student2=new Pawn(PawnsColors.getRandom());
        Pawn student3=new Pawn(PawnsColors.getRandom());
        int indexOfIsland=2;

        gameBoard.setStudentOnIsland(student1,indexOfIsland);
        gameBoard.setStudentOnIsland(student2,indexOfIsland);
        gameBoard.setStudentOnIsland(student3,indexOfIsland);
        Iterator<Pawn> iterator =gameBoard.getIslands().get(indexOfIsland).getStudents();
        int i=0;
        while(iterator.hasNext())
        {
            iterator.next();
            i++;
        }
        assertEquals(3,i);

    }







    /**
     * Checks for the functions of giving and removing coins from Coinsupply, the value of coinsuplly
     * must be always beetween 0 and 20
     */


    @Test
    void Coins(){
        Board board=new Board(3);
        for(int i=0;i<60;i++){
            board.giveCoin();
        }
        assertEquals(0,board.getCoinsSupply());
        int coinsToAdd=3;
        board.addCoins(coinsToAdd);
      assertEquals(3,board.getCoinsSupply());
        int coinsToAdd2=100;
        board.addCoins(coinsToAdd2);
        assertEquals(board.getCoinsSupply(),20);
    }

    @Test
    void monkEffectHandler(){
        Board boardtest=new Board(3);
        Pawn pawn=new Pawn(PawnsColors.getRandom());
        int islandIndex=3;
        MonkEffectHandler monkEffectHandler=new MonkEffectHandler(boardtest,pawn,islandIndex);
        monkEffectHandler.applyEffect();

        Iterator<Pawn> iterator =boardtest.getIslands().get(islandIndex).getStudents();
        int i=0;
        while(iterator.hasNext())
        {
            iterator.next();
            i++;
        }
        assertEquals(1,i);

    }

}