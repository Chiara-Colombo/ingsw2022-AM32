package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.polimi.ingsw.utils.Utils.*;
import static org.junit.Assert.*;

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
    void changeOrder(){
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game game = new Game(3, true, jsonCards);

        Player player0 = new Player("Pippo", 7);
        Player player1 = new Player("Pluto", 7);
        Player player2 = new Player("Paperino", 7);

        game.addPlayer(player0);
        game.addPlayer(player1);
        game.addPlayer(player2);

        game.startGame();


        assertEquals("Pippo", game.getOrderOfPlay().get(0));
        assertEquals("Pluto", game.getOrderOfPlay().get(1));
        assertEquals("Paperino", game.getOrderOfPlay().get(2));

        List<String> players = Arrays.asList("Pluto", "Paperino", "Pippo");

        System.out.print("VETTORE GIOCATORI : [ ");
        for(Player player : game.getPlayers()) {
            System.out.print(player.getNickname() +  " ");
        }


        System.out.println("] ");
        System.out.print("COPIA PRIMA CAMBIO ORDINE : ");
        System.out.println(game.getOrderOfPlay());

        System.out.println("current player prima dei next: " + game.getcurrentplayerindex() + " " + game.getCurrentPlayer().getNickname() + " /// playerorderIndex " + game.getPlayerOrderIndex());

        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());


        game.ChangePlayersOrder(players);

        assertNotEquals("Pippo", game.getOrderOfPlay().get(0));
        assertNotEquals("Pluto", game.getOrderOfPlay().get(1));
        assertNotEquals("Paperino", game.getOrderOfPlay().get(2));

        assertEquals("Pluto", game.getOrderOfPlay().get(0));
        assertEquals("Paperino", game.getOrderOfPlay().get(1));
        assertEquals("Pippo", game.getOrderOfPlay().get(2));



        System.out.print("COPIA DOPO CAMBIO ORDINE : ");
        System.out.println(game.getOrderOfPlay());

        System.out.println("current player prima dei next: " + game.getcurrentplayerindex() + " " + game.getCurrentPlayer().getNickname() + " /// playerorderIndex " + game.getPlayerOrderIndex());

        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());

        List<String> players2 = Arrays.asList("Paperino", "Pippo", "Pluto");
        game.ChangePlayersOrder(players2);

        System.out.print("COPIA DOPO CAMBIO ORDINE : ");
        System.out.println(game.getOrderOfPlay());

        System.out.println("current player prima dei next: " + game.getcurrentplayerindex() + " " + game.getCurrentPlayer().getNickname() + " /// playerorderIndex " + game.getPlayerOrderIndex());

        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());
        game.nextPlayer();
        System.out.println(" CURRENT PLAYER DOPO AVER FATTO NEXT : " + game.getCurrentPlayer().getNickname());
        System.out.println("current player : " + game.getcurrentplayerindex() + " /// playerorder index : " + game.getPlayerOrderIndex());

    }



    @Test
    void numOfPlayers() {
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game = new Game(3, false, jsonCards);
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
        game = new Game(2, true, jsonCards);
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
        game = new Game(2, true, jsonCards);
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
        game = new Game(2, false, jsonCards);
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
    void expertMode(){
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        game = new Game(2, true, jsonCards);
        assertTrue(game.isExpertMode());
        game = new Game(2, false, jsonCards);
        assertFalse(game.isExpertMode());
    }

    @Test
    void getRandom(){
        PawnsColors color = PawnsColors.getRandom();
        Pawn student = new Pawn(color);
        Assertions.assertEquals(color, student.getColor());
    }

    @Test
    void MooshroomManEffectHandler() {
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game game = new Game(2, false, jsonCards);
        PawnsColors color= PawnsColors.getRandom();
        MooshroomManEffectHandler handler = new MooshroomManEffectHandler(color,game);
        handler.applyEffect();
        assertEquals(0,game.getInfluenceForColor(color));
        handler.removeEffect();
        assertEquals(1,game.getInfluenceForColor(color));

        PawnsColors color2= PawnsColors.getRandom();
        MooshroomManEffectHandler handler2 = new MooshroomManEffectHandler(color2,game);
        handler2.applyEffect();
        assertEquals(0,game.getInfluenceForColor(color2));
        handler2.removeEffect();
        assertEquals(1,game.getInfluenceForColor(color2));
        assertEquals(1,game.getInfluenceForColor(color));

    }


    @Test
    public void StarGame(){
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
       Game gametest = new Game(2,true, jsonCards );

        Player player1 = new Player("Paolo Tommaso",7);
        Player player2 = new Player("Gasparo Noe",7);

        gametest.addPlayer(player1);
        gametest.addPlayer(player2);

        gametest.startGame();

        assertEquals(GamePhase.START_PHASE,gametest.getGamePhase());
/**
        System.out.println(gametest.getValidCharacters().get(0).getCoinValue());
        System.out.println(gametest.getValidCharacters().get(1).getCoinValue());
        System.out.println(gametest.getValidCharacters().get(2).getCoinValue());
*/
        gametest.setGamePhase(GamePhase.ACTION_PHASE);
        assertEquals(GamePhase.ACTION_PHASE,gametest.getGamePhase());
    }

    @Test
    void character(){
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Game gametest = new Game(2,true, jsonCards );

        Player player1 = new Player("Paolo Tommaso",7);
        Player player2 = new Player("Gasparo Noe",7);

        gametest.addPlayer(player1);
        gametest.addPlayer(player2);

        gametest.startGame();
    }
}