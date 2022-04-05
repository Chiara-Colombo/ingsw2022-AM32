package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

class BoardTest {

    public final Board gameBoard=new Board(2);
    public final Board gameBoard2=new Board(2);
    ArrayList<Island> ex;

    /**Check if motherNature casual value is beetween the setted values 0<x<12**/
    @Test
    void MotherNature(){

        assertTrue(-1<gameBoard.getMotherNature() && gameBoard.getMotherNature()<12);
        assertTrue(-1<gameBoard2.getMotherNature() && gameBoard2.getMotherNature()<12);

    }

    @Test
    void Islandsize(){
        assertEquals(12,gameBoard.getIslandSize());
        assertEquals(12,gameBoard2.getIslandSize());
        }


    /**Check if after adding motherNature some value the value is beetween the number of the islands**/
    @Test
    void moveMotherNature(){

        int index=5;

        assertTrue(gameBoard.moveMotherNature(index)<12 && gameBoard.moveMotherNature(index)>=0);

    }

    /**Set students pawn on a selected cloud and removing one of them, check if the amount of students pawns is correct**/

    @Test
    void setStudentOnCloud(){

        Board gameBoard=new Board(2);
        assertEquals(2,gameBoard.getClouds().size());
        int studentsToadd=5;
        for(int i=0;i<studentsToadd;i++) {
            Pawn student = new Pawn(PawnsColors.getRandom());
            gameBoard.setStudentOnCloud(student, 1);
        }
        gameBoard.removeStudentFromCloud(1,1);
        assertEquals(4,gameBoard.getClouds().get(1).studentsSize());
    }


    /**sets students on island and check if the amaunt is correct**/

    @Test
    void setStudentOnIsland(){

        Board gameBoard=new Board(2);
        assertEquals(12,gameBoard.getIslands().size());
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

    @Test

    void  getClouds() {
        Board board=new Board(3);
        assertEquals(3,board.getClouds().size());
        Board board1=new Board(2);
        assertEquals(2,board1.getClouds().size());
    }

    @Test
    void getIslands() {
        Board board=new Board(3);
        assertEquals(12,board.getIslands().size());
    }

    @Test
    void coinsSupply(){
        Board board=new Board(3);
        int coinsToremove=6;
        for (int i=0;i<coinsToremove;i++){board.giveCoin();}
        assertEquals(20-coinsToremove,board.getCoinsSupply());
    }

}