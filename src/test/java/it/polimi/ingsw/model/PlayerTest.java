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
        game = new Game(numOfPlayers, jsonCards);

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

        Iterator<Pawn> iterator = schoolBoard.getStudentsInEntrance();
        while (iterator.hasNext()) {
            iterator.next();
            i++;
        }
        assertEquals(2, i);


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
        Pawn professor = new Pawn(PawnsColors.getRandom());

        player.addProfessor(professor);

        Iterator<Pawn> iterator = schoolBoard.getProfessors();
        while (iterator.hasNext()) {
            iterator.next();
            i++;
        }
        assertEquals(1, i);
    }


    @Test
    void SpoiledPrincessEffectHandler() {
        Player player = new Player("player", 7);
        Pawn student = new Pawn(PawnsColors.BLUE);
        int i=0;
        SpoiledPrincessEffectHandler handler = new SpoiledPrincessEffectHandler(student, player);
        handler.applyEffect();

        Iterator<Pawn> iterator= player.getSchoolBoard().getStudentsOfColor(PawnsColors.BLUE);
        while(iterator.hasNext()){
            i++;
            iterator.next();
        }
        assertEquals(1,i);



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
}

