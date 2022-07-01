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

        Game game = new Game(2, true, jsonCards);
        Player player = new Player("prova", 7);
        Player player2 = new Player("prov2", 7);

        game.addPlayer(player);
        game.addPlayer(player2);

        game.startGame();

        final int playerStudents = player.getSchoolBoard().getStudentsInEntrance().size(),
                player2Students = player2.getSchoolBoard().getStudentsInEntrance().size(),
                jesterStudents = game.getJesterStudents().size();

        for (int i = 0; i < playerStudents; i++) {
            player.removeStudent(0);
        }
        assertEquals(0, player.getSchoolBoard().getStudentsInEntrance().size());
        for (int i = 0; i < player2Students; i++) {
            player2.removeStudent(0);
        }
        assertEquals(0, player2.getSchoolBoard().getStudentsInEntrance().size());

        player.addStudentInEntrance(new Pawn(PawnsColors.BLUE));
        player.addStudentInEntrance(new Pawn(PawnsColors.BLUE));
        player.addStudentInEntrance(new Pawn(PawnsColors.BLUE));

        player2.addStudentInEntrance(new Pawn(PawnsColors.RED));
        player2.addStudentInEntrance(new Pawn(PawnsColors.RED));
        player2.addStudentInEntrance(new Pawn(PawnsColors.RED));

        for (int i = 0; i < jesterStudents; i++) {
            game.getJesterStudents().remove(0);
        }

        game.getJesterStudents().add(new Pawn(PawnsColors.GREEN));
        game.getJesterStudents().add(new Pawn(PawnsColors.GREEN));
        game.getJesterStudents().add(new Pawn(PawnsColors.GREEN));
        game.getJesterStudents().add(new Pawn(PawnsColors.GREEN));
        game.getJesterStudents().add(new Pawn(PawnsColors.GREEN));
        game.getJesterStudents().add(new Pawn(PawnsColors.GREEN));

        ArrayList<Integer> jesterIndexes = new ArrayList<>(List.of(0, 1, 2));
        ArrayList<Integer> player1Indexes = new ArrayList<>(List.of(2, 1));
        ArrayList<Integer> player2Indexes = new ArrayList<>(List.of(0, 1, 2));

        JesterEffectHandler jesterEffectHandler = new JesterEffectHandler(player, game, jesterIndexes, player1Indexes);
        JesterEffectHandler jesterEffectHandler2 = new JesterEffectHandler(player2, game, jesterIndexes, player2Indexes);

        jesterEffectHandler.applyEffect();
        jesterEffectHandler.removeEffect();

        assertEquals(PawnsColors.BLUE, player.getSchoolBoard().getStudentsInEntrance().get(2).getColor());
        assertEquals(PawnsColors.BLUE, player.getSchoolBoard().getStudentsInEntrance().get(1).getColor());

        assertEquals(PawnsColors.GREEN, game.getJesterStudents().get(0).getColor());
        assertEquals(PawnsColors.GREEN, game.getJesterStudents().get(1).getColor());
        assertEquals(PawnsColors.GREEN, game.getJesterStudents().get(2).getColor());

        jesterEffectHandler2.applyEffect();
        jesterEffectHandler2.removeEffect();

        assertEquals(PawnsColors.GREEN, player2.getSchoolBoard().getStudentsInEntrance().get(0).getColor());
        assertEquals(PawnsColors.GREEN, player2.getSchoolBoard().getStudentsInEntrance().get(1).getColor());
        assertEquals(PawnsColors.GREEN, player2.getSchoolBoard().getStudentsInEntrance().get(2).getColor());


        assertEquals(PawnsColors.GREEN, game.getJesterStudents().get(0).getColor());
        assertEquals(PawnsColors.GREEN, game.getJesterStudents().get(1).getColor());
        assertEquals(PawnsColors.GREEN, game.getJesterStudents().get(2).getColor());
        assertEquals(PawnsColors.RED, game.getJesterStudents().get(3).getColor());
        assertEquals(PawnsColors.RED, game.getJesterStudents().get(4).getColor());
        assertEquals(PawnsColors.RED, game.getJesterStudents().get(5).getColor());
    }

    @Test
    void Minstrel() {

        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Game game = new Game(2, true, jsonCards);
        Player player = new Player("prova", 7);
        Player player2 = new Player("prov2", 7);

        game.addPlayer(player);
        game.addPlayer(player2);

        game.startGame();


        ArrayList<PawnsColors> diningPawns = new ArrayList<>();
        diningPawns.add(PawnsColors.BLUE);
        diningPawns.add(PawnsColors.RED);

        ArrayList<Integer> entrancePawns = new ArrayList<>();

        entrancePawns.add(0);
        entrancePawns.add(1);

        Pawn pawn3 = new Pawn(PawnsColors.BLUE);
        game.getPlayers().get(0).addStudentInDiningRoom(pawn3);

        Pawn pawn4 = new Pawn(PawnsColors.RED);
        game.getPlayers().get(0).addStudentInDiningRoom(pawn4);

        assertEquals(1, player.getSchoolBoard().getStudentsOfColor(PawnsColors.BLUE).size());
        assertEquals(1, player.getSchoolBoard().getStudentsOfColor(PawnsColors.RED).size());

        assertEquals(7, player.getSchoolBoard().getStudentsInEntrance().size());

        MinstrelEffectHandler minstrelEffectHandler = new MinstrelEffectHandler(player, entrancePawns, diningPawns);
        minstrelEffectHandler.applyEffect();
        minstrelEffectHandler.removeEffect();

        assertEquals(7, player.getSchoolBoard().getStudentsInEntrance().size());
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

