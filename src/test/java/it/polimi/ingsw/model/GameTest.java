package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static it.polimi.ingsw.utils.Utils.*;

public class GameTest {

    public Game game;

    private String readCards() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(CARDS_RESOURCE_PATH));
        return input.readLine();
    }

    /**
     * select the number of players
     */

    @Test
    void numOfPlayers() {
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game = new Game(3, jsonCards);
        Assertions.assertEquals(3, game.getNumOfPlayers());
    }
    /**
     * set name and check if nickname has been setted correctly
     **/
    @Test
    void setName() {
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game = new Game(2, jsonCards);
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
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game = new Game(2, jsonCards);
        Player player1 = new Player("ellenripley", 0);
        Player player2 = null;
        game.addPlayer(player1);
        game.addPlayer(player2);
        Assertions.assertFalse(game.getPlayers().contains(player1) && game.getPlayers().contains(player2));
        game.setCurrentPlayer(0);
        assertEquals("ellenripley",game.getCurrentPlayer().getNickname());
        game.setCurrentPlayer(1);
        assertEquals("ellenripley",game.getCurrentPlayer().getNickname());

    }

    /**
     * get character cards costs
     **/

    @Test
    void getCharactercost() {
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game = new Game(2, jsonCards);
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
        PawnsColors color = PawnsColors.getRandom();
        Pawn student = new Pawn(color);
        Assertions.assertEquals(color, student.getColor());
    }
}