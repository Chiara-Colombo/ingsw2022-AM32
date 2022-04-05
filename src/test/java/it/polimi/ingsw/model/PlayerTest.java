package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class PlayerTest {

    Game game;


    /**add differents students pawns to the diningroom and check if the size of che key values which is the number of pawn of
     * each color is right
     */



    @Test
    void setTowersColors(){
        int numOfPlayers=3;
        int towers=6;
        game=new Game(numOfPlayers);

        Player player1=new Player("Anna",towers);
        Player player2=new Player("Beatrice",towers);
        Player player3=new Player("Clotilde",towers);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        player1.setColor(TowersColors.BLACK);
        player2.setColor(TowersColors.GREY);
        player3.setColor(TowersColors.WHITE);
        assertEquals(TowersColors.BLACK,player1.getColor());
        assertEquals(TowersColors.GREY,player2.getColor());
        assertEquals(TowersColors.WHITE,player3.getColor());

    }
}
