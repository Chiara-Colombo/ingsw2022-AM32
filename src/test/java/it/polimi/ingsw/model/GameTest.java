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
     * testing the change of order of play
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




        assertEquals("Pippo",game.getOrderOfPlay().get(0));
        assertEquals("Pluto",game.getOrderOfPlay().get(1));
        assertEquals("Paperino",game.getOrderOfPlay().get(2));



        assertEquals("Pippo",game.getCurrentPlayer().getNickname());


        game.nextPlayer();
        assertEquals("Pluto",game.getCurrentPlayer().getNickname());
        assertEquals(1,game.getcurrentplayerindex());
        assertEquals(1,game.getPlayerOrderIndex());



        game.nextPlayer();
        assertEquals("Paperino",game.getCurrentPlayer().getNickname());
        assertEquals(2,game.getcurrentplayerindex());
        assertEquals(2,game.getPlayerOrderIndex());

        game.nextPlayer();
        assertEquals("Pippo",game.getCurrentPlayer().getNickname());
        assertEquals(0,game.getcurrentplayerindex());
        assertEquals(0,game.getPlayerOrderIndex());
        game.nextPlayer();
        assertEquals("Pluto",game.getCurrentPlayer().getNickname());
        assertEquals(1,game.getcurrentplayerindex());
        assertEquals(1,game.getPlayerOrderIndex());
        game.nextPlayer();
        assertEquals("Paperino",game.getCurrentPlayer().getNickname());
        assertEquals(2,game.getcurrentplayerindex());
        assertEquals(2,game.getPlayerOrderIndex());
        game.nextPlayer();
        assertEquals("Pippo",game.getCurrentPlayer().getNickname());
        assertEquals(0,game.getcurrentplayerindex());
        assertEquals(0,game.getPlayerOrderIndex());
        game.nextPlayer();
        assertEquals("Pluto",game.getCurrentPlayer().getNickname());
        assertEquals(1,game.getcurrentplayerindex());
        assertEquals(1,game.getPlayerOrderIndex());
        game.nextPlayer();
        assertEquals("Paperino",game.getCurrentPlayer().getNickname());
        assertEquals(2,game.getcurrentplayerindex());
        assertEquals(2,game.getPlayerOrderIndex());


        game.ChangePlayersOrder(players);

        assertNotEquals("Pippo", game.getOrderOfPlay().get(0));
        assertNotEquals("Pluto", game.getOrderOfPlay().get(1));
        assertNotEquals("Paperino", game.getOrderOfPlay().get(2));

        assertEquals("Pluto", game.getOrderOfPlay().get(0));
        assertEquals("Paperino", game.getOrderOfPlay().get(1));
        assertEquals("Pippo", game.getOrderOfPlay().get(2));


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

        Assertions.assertEquals(3,Characters.CENTAUR.getCoinValue());
        Assertions.assertEquals(2, Characters.GRANDMA_HERBS.getCoinValue());
        Assertions.assertEquals(2, Characters.FARMER.getCoinValue());
        Assertions.assertEquals(2, Characters.KNIGHT.getCoinValue());
        Assertions.assertEquals(1, Characters.MAGIC_MAILMAN.getCoinValue());
        Assertions.assertEquals(3, Characters.MUSHROOMS_MAN.getCoinValue());
        Assertions.assertEquals(2, Characters.SPOILED_PRINCESS.getCoinValue());
        Assertions.assertEquals(1, Characters.MONK.getCoinValue());
        game = new Game(2, false, jsonCards);
        Player player1 = new Player("Paolo Tommaso",7);
        Player player2 = new Player("Gasparo Noe",7);
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.startGame();
        ArrayList<Characters> characters = game.getValidCharacters();
        int i = 0;
        for(Characters character : characters){
            i++;
        }
        Assertions.assertEquals(3, i);


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
        MushroomManEffectHandler handler = new MushroomManEffectHandler(color,game);
        handler.applyEffect();
        assertEquals(0,game.getInfluenceForColor(color));
        handler.removeEffect();
        assertEquals(1,game.getInfluenceForColor(color));

        PawnsColors color2= PawnsColors.getRandom();
        MushroomManEffectHandler handler2 = new MushroomManEffectHandler(color2,game);
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

    @Test
    void ThiefEffect(){

        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Game game = new Game(3,true,jsonCards);

        Player p1 = new Player("p1",7);
        Player p2 = new Player("p2",7);
        Player p3 = new Player("p3",7);

        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        game.startGame();


        Pawn pawn = new Pawn(PawnsColors.BLUE);
        game.getPlayers().get(0).addStudentInDiningRoom(pawn);


        game.getPlayers().get(1).addStudentInDiningRoom(pawn);
        game.getPlayers().get(1).addStudentInDiningRoom(pawn);

        game.getPlayers().get(2).addStudentInDiningRoom(pawn);
        game.getPlayers().get(2).addStudentInDiningRoom(pawn);
        game.getPlayers().get(2).addStudentInDiningRoom(pawn);


        ThiefEffectHandler thiefEffectHandler = new ThiefEffectHandler(game,PawnsColors.BLUE);

        thiefEffectHandler.applyEffect();

        assertEquals(0,game.getPlayers().get(1).getSchoolBoard().getStudentsOfColor(PawnsColors.BLUE).size());
        assertEquals(0,game.getPlayers().get(0).getSchoolBoard().getStudentsOfColor(PawnsColors.BLUE).size());
        assertEquals(0,game.getPlayers().get(2).getSchoolBoard().getStudentsOfColor(PawnsColors.BLUE).size());

    }

    @Test
    void ActivateCharacter(){
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

        gametest.activateCharacter(Characters.MONK);
        assertEquals(Characters.MONK,gametest.getActiveCharacter());

    }
}