package it.polimi.ingsw.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameTest {

    public Game game;


    /**legati a Player players vista come arraylist*/

    @Test
    void numOfPlayers() {

    game=new Game(3);
    assertEquals(3,game.getNumOfPlayers());
    }


    @Test
    void setName() {
        game=new Game(2);
        Player player1 = new Player("Alana Kane",0);
        assertEquals("Alana Kane", player1.getNickname());
        Player player2 = new Player("something",0);
        assertEquals("something", player2.getNickname());
    }

    /**-Set a game with two player
     * -add the players to the game and check if they're in
     * -set the current player and check if the current player corresponds with the chosen one
     */
    @Test
    void addPlayer(){
        game=new Game(2);
        Player player1=new Player("ellenripley",0);
        Player player2=new Player("Cannavaciuolo",0);
        game.addPlayer(player1);
        game.addPlayer(player2);
        assertTrue(game.getPlayers().contains(player1)&& game.getPlayers().contains(player2));
        game.setCurrentPlayer(0);
        assertEquals("ellenripley",game.getCurrentPlayer().getNickname());
        System.out.println(game.getCurrentPlayer().getNickname());
        game.setCurrentPlayer(1);
        assertEquals("Cannavaciuolo",game.getCurrentPlayer().getNickname());

    }

    @Test
    void activePlayers(){
        game=new Game(2);
        Player player1=new Player("Furiosa",0);
        game.addPlayer(player1);
        assertEquals(1,game.getActivePlayers());
        Player player2=new Player("someone",0);
        game.addPlayer(player2);
        assertEquals(2,game.getActivePlayers());
    }


    @Test
    void setTowersColors(){
        game=new Game(3);
        Player player1=new Player("A",0);
        Player player2=new Player("B",0);
        Player player3=new Player("C",0);
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

/*
    @Test
    void assistantCardManager(){
        AssistantCardsManager deck1=new AssistantCardsManager();
        ArrayList<AssistantCard> assistantCards=deck1.getAssistantCards();

        assertEquals(1,assistantCards.get(0).getValue());
        assertEquals(2,assistantCards.get(1).getValue());

    }

 */

/**
    @Test
    void initbag(){
        Integer index;
        Random random;
        ArrayList<Integer> values;
        Map<PawnsColors,ArrayList<Integer>> bag=new HashMap<>();
        values=new ArrayList<>(Arrays.asList(0,1));
        bag.put(PawnsColors.GREEN,values);
        bag.put(PawnsColors.BLUE,values);
        bag.put(PawnsColors.PINK,values);
        bag.put(PawnsColors.YELLOW,values);
        bag.put(PawnsColors.RED,values);
        bag.forEach((key, value) -> System.out.println(key + ":" + value));
        index=ThreadLocalRandom.current().nextInt(0, values.size());
        List<PawnsColors> keysAsArray = new ArrayList<>(bag.keySet());
*/

        /**
         * List<PawnsColors> keysAsArray = new ArrayList<>(bag.keySet());
        Random r = new Random();




        bag.forEach((key, value) -> System.out.println(key + ":" + value));




        System.out.println(bag.get(keysAsArray.get(r.nextInt(keysAsArray.size()))));


        bag.forEach((key, value) -> System.out.println(key + ":" + value));


    }
    **/
}
