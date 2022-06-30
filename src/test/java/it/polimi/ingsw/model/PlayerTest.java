package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ISpoiledPrincessHandled;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import static it.polimi.ingsw.utils.Utils.*;

import static org.junit.Assert.*;

public class PlayerTest {

    Game game;

    private String readCards() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(CARDS_RESOURCE_PATH));
        return input.readLine();
    }

    /**
     * add differents students pawns to the diningroom and check if the size of che key values which is the number of pawn of
     * each color is right
     */


    @Test
    void setTowersColors() {
        int numOfPlayers = 3;
        int towers = 6;
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game = new Game(numOfPlayers, true, jsonCards);

        Player player1 = new Player("Anna", towers);
        Player player2 = new Player("Beatrice", towers);
        Player player3 = new Player("Clotilde", towers);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        player1.setColor(TowersColors.BLACK);
        player2.setColor(TowersColors.GREY);
        player3.setColor(TowersColors.WHITE);
        assertEquals(TowersColors.BLACK, player1.getColor());
        assertEquals(TowersColors.GREY, player2.getColor());
        assertEquals(TowersColors.WHITE, player3.getColor());

    }

    @Test
    void setStudentInEntrance() {
        int i = 0;
        Player player = new Player("prova", 3);
        ISchoolBoard schoolBoard = player.getSchoolBoard();
        Pawn student1 = new Pawn(PawnsColors.getRandom());
        Pawn student2 = new Pawn(PawnsColors.getRandom());

        player.addStudentInEntrance(student1);
        player.addStudentInEntrance(student2);

        ArrayList<Pawn> students = schoolBoard.getStudentsInEntrance();
        assertEquals(2, students.size());


    }


    @Test
    void Coins() {
        Player player = new Player("prova", 2);
        player.earnCoin();
        player.earnCoin();
        assertEquals(2, player.getCoins());

        player.payCoins(3);
        assertFalse(player.payCoins(3));
        player.payCoins(0);
        assertEquals(2, player.getCoins());
        player.payCoins(1);
        assertEquals(1, player.getCoins());
    }

    @Test
    void addProfessor() {
        int i = 0;
        Player player = new Player("prova", 3);
        ISchoolBoard schoolBoard = player.getSchoolBoard();
        PawnsColors color = PawnsColors.getRandom();
        Pawn professor = new Pawn(color);

        player.addProfessor(professor);

        ArrayList<Pawn> professors = schoolBoard.getProfessors();
        assertEquals(1, professors.size());
        player.removeProfessor(0);
        assertEquals(0, professors.size());
    }


    @Test
    void SpoiledPrincessEffectHandler() {
        Player player = new Player("player", 7);
        Pawn student = new Pawn(PawnsColors.BLUE);
        Board board = new Board(2);
        int i = 0;
        SpoiledPrincessEffectHandler handler = new SpoiledPrincessEffectHandler(student, player, board);
        handler.applyEffect();

        ArrayList<Pawn> students = player.getSchoolBoard().getStudentsOfColor(PawnsColors.BLUE);
        assertEquals(1, students.size());
    }

    @Test
    void FarmerEffecttHandler() {
        Player player = new Player("test", 5);
        FarmerEffectHandler handler = new FarmerEffectHandler(player);
        handler.applyEffect();
       assertEquals(1,player.getExtraStudent());
       handler.removeEffect();
       assertEquals(0,player.getExtraStudent());

    }

    @Test
    void KnightEffectHandler() {
        Player player = new Player("test", 7);
        KnightEffectHandler handler = new KnightEffectHandler(player);
        handler.applyEffect();

        assertEquals(2,player.getExtraInfluence());
        handler.removeEffect();
        assertEquals(0,player.getExtraStudent());

    }

    @Test
    void Jester() {

        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Game game = new Game(2,true,jsonCards);
        Player player = new Player("prova", 7);
        Player player2 = new Player("prov2", 7);

        game.addPlayer(player);
        game.addPlayer(player2);

        game.startGame();


        ArrayList<Pawn> pawns = new ArrayList<>();
        pawns.add(new Pawn(PawnsColors.BLUE));
        pawns.add(new Pawn(PawnsColors.RED));
        pawns.add(new Pawn(PawnsColors.YELLOW));


        JesterEffectHandler jesterEffectHandler = new JesterEffectHandler(player,pawns);

        assertEquals(7,player.getSchoolBoard().getStudentsInEntrance().size());

        player.removeStudent(0);
        player.removeStudent(1);
        player.removeStudent(2);

        assertEquals(4,player.getSchoolBoard().getStudentsInEntrance().size());

        jesterEffectHandler.applyEffect();
        assertEquals(7,player.getSchoolBoard().getStudentsInEntrance().size());

        ArrayList<Pawn> pawns2 = new ArrayList<>();
        pawns2.add(new Pawn(PawnsColors.BLUE));
        pawns2.add(new Pawn(PawnsColors.RED));

        JesterEffectHandler jesterEffectHandler2 = new JesterEffectHandler(player2,pawns2);

        assertEquals(7,player2.getSchoolBoard().getStudentsInEntrance().size());

        player2.removeStudent(0);
        player2.removeStudent(1);

        assertEquals(5,player2.getSchoolBoard().getStudentsInEntrance().size());


        jesterEffectHandler2.applyEffect();

        assertEquals(7,player2.getSchoolBoard().getStudentsInEntrance().size());







    }

    @Test
    void Towers(){

        int towers = 7;
        Player player = new Player("Stefano",towers);
        assertEquals(towers, player.getTowers());
        player.removeTower();
        assertEquals(towers - 1,player.getTowers());
        Tower tower = new Tower(TowersColors.NONE);
        player.addTower(tower);
        assertEquals(towers, player.getTowers());
    }
}

