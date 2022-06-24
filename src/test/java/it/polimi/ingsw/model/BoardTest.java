package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

class BoardTest {

    public final Board gameBoard=new Board(2);
    public final Board gameBoard2=new Board(2);


    /**Set students pawn on a selected cloud and removing one of them, check if the amount of students pawns is correct**/

    @Test
    void setStudentOnCloud(){

        Board gameBoard = new Board(2);
        int studentsToadd = 3;
        for(int i = 0; i < studentsToadd; i++) {
            Pawn student = new Pawn(PawnsColors.getRandom());
            gameBoard.setStudentOnCloud(student, 1);
        }
        gameBoard.removeStudentFromCloud(1);

        Iterator<Pawn> iterator0 = gameBoard.getClouds().get(0).getStudents();
        Iterator<Pawn> iterator1 = gameBoard.getClouds().get(1).getStudents();

        int cloud0Size = 0, cloud1Size = 0;
        while (iterator0.hasNext()) {
            cloud0Size++;
            iterator0.next();
        }
        while (iterator1.hasNext()) {
            cloud1Size++;
            iterator1.next();
        }
        assertEquals(2, cloud1Size);
        assertEquals(0, cloud0Size);

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
        gameBoard.moveMotherNature(index);
        assertTrue(gameBoard.getMotherNature() < 12 && gameBoard.getMotherNature() >= 0);

        int index2=1;
        gameBoard.moveMotherNature(index2);
        assertTrue(gameBoard.getMotherNature() < 12 && gameBoard.getMotherNature() >= 0);
    }


    //initial island size
    @Test
    void IslandsSize(){
        assertEquals(12, gameBoard.getIslandsManager().getIslandsSize());
        }


    /**sets students on island and check if the amaunt is correct**/

    @Test
    void setStudentOnIsland(){

        Board gameBoard=new Board(2);
        Pawn student1=new Pawn(PawnsColors.getRandom());
        Pawn student2=new Pawn(PawnsColors.getRandom());
        Pawn student3=new Pawn(PawnsColors.getRandom());
        int indexOfIslandwithMotherNature = gameBoard.getMotherNature();

        gameBoard.getIslandsManager().setStudentOnIsland(student1,indexOfIslandwithMotherNature);
        gameBoard.getIslandsManager().setStudentOnIsland(student2,indexOfIslandwithMotherNature);
        gameBoard.getIslandsManager().setStudentOnIsland(student3,indexOfIslandwithMotherNature);
        Iterator<Pawn> iterator = gameBoard.getIslandsManager().getIsland(indexOfIslandwithMotherNature).getStudents();
        int i=0;
        while(iterator.hasNext())
        {
            iterator.next();
            i++;
        }
        //When the board is created, the island arleady has a pawn

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
        int coinsToAdd = 3;
        board.addCoins(coinsToAdd);
        assertEquals(3,board.getCoinsSupply());
        int coinsToAdd2 = 100;
        board.addCoins(coinsToAdd2);
        assertEquals(board.getCoinsSupply(),20);
    }

    @Test
    void monkEffectHandler(){
        Board boardTest = new Board(3);
        Pawn pawn = new Pawn(PawnsColors.getRandom());
        int indexOfIslandWithMotherNature = boardTest.getMotherNature();
        MonkEffectHandler monkEffectHandler = new MonkEffectHandler(boardTest.getIslandsManager(), pawn, indexOfIslandWithMotherNature);
        monkEffectHandler.applyEffect();

        Iterator<Pawn> iterator = boardTest.getIslandsManager().getIsland(indexOfIslandWithMotherNature).getStudents();
        int i = 0;
        while(iterator.hasNext()) {
            iterator.next();
            i++;
        }

        //when the board is created it arleady has a  student pawn on the island
        assertEquals(1,i);
    }

    @Test
    void CentaurEffect(){
        Board boardTest = new Board(3);

        CentaurEffectHandler centaurEffectHandler = new CentaurEffectHandler(boardTest);
        assertEquals(1,boardTest.getTowersInfluence());
        centaurEffectHandler.applyEffect();
        assertEquals(0,boardTest.getTowersInfluence());
        centaurEffectHandler.removeEffect();
        assertEquals(1,boardTest.getTowersInfluence());
    }


    @Test
    void Bag(){
        Board board = new Board(3);
        assertEquals(120,board.getBagSize());
        assertFalse(board.isBagEmpty());
    }

    @Test
    void Tower(){
        Board board = new Board(2);
        Tower tower = new Tower(TowersColors.GREY);
        board.getIslandsManager().setTowerOnIsland(tower,3);
        IIsland islandWithTower = board.getIslandsManager().getIsland(3);
        assertTrue(islandWithTower.getTower().isPresent());
        IIsland islandWithoutTower = board.getIslandsManager().getIsland(4);
        assertFalse(islandWithoutTower.getTower().isPresent());
    }
}