package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameTest {

    public Game game;


    /**
     * select the number of players
     */

    @Test
    void numOfPlayers() {

        game = new Game(3);
        Assertions.assertEquals(3, game.getNumOfPlayers());
    }
    /**
     * set name and check if nickname has been setted correctly
     **/
    @Test
    void setName() {
        game = new Game(2);
        Player player1 = new Player("Alana Kane", 0);
        Assertions.assertEquals("Alana Kane", player1.getNickname());
        Player player2 = new Player("something", 0);
        Assertions.assertEquals("something", player2.getNickname());
    }

    /**-Set a game with two player
     * -add the players to the game and check if they're in
     * -set the current player and check if the current player corresponds with the chosen one
     */
    @Test
    void addPlayer() {
        game = new Game(2);
        Player player1 = new Player("ellenripley", 0);
        Player player2 = new Player("Cannavaciuolo", 0);
        game.addPlayer(player1);
        game.addPlayer(player2);
        Assertions.assertTrue(game.getPlayers().contains(player1) && game.getPlayers().contains(player2));
        game.setCurrentPlayer(0);
        assertEquals("ellenripley",game.getCurrentPlayer().getNickname());
        System.out.println(game.getCurrentPlayer().getNickname());
        game.setCurrentPlayer(1);
        assertEquals("Cannavaciuolo",game.getCurrentPlayer().getNickname());

    }

    /**
     * add players to the game and check if they are contained in the player list of the game
     **/
    @Test
    void activePlayers() {
        game = new Game(2);
        Player player1 = new Player("Furiosa", 0);
        game.addPlayer(player1);
        Assertions.assertEquals(1, game.getActivePlayers());
        Player player2 = new Player("boh", 0);
        game.addPlayer(player2);
        Assertions.assertEquals(2, game.getActivePlayers());
    }

    /**
     * get character cards costs
     **/
    @Test
    void getCharactercost() {
        Game game = new Game(3);
        Assertions.assertEquals(3, game.getCharacterCost(Characters.CENTAUR));
        Assertions.assertEquals(2, game.getCharacterCost(Characters.GRANDMA_HERBS));
        Assertions.assertEquals(2, game.getCharacterCost(Characters.FARMER));
        Assertions.assertEquals(2, game.getCharacterCost(Characters.KNIGHT));
        Assertions.assertEquals(1, game.getCharacterCost(Characters.MAGIC_MAILMAN));
        Assertions.assertEquals(3, game.getCharacterCost(Characters.MUSHROOMS_MAN));
        Assertions.assertEquals(2, game.getCharacterCost(Characters.SPOILED_PRINCESS));
        Assertions.assertEquals(1, game.getCharacterCost(Characters.MONK));
    }



    @Test
    void getRandom(){
        PawnsColors color=PawnsColors.getRandom();
        Pawn student=new Pawn(color);
        Assertions.assertEquals(color, student.getColor());
    }
    /**
     * shows active players
     **/
}
