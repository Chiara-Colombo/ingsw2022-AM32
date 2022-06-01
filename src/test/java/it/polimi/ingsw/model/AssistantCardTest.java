package it.polimi.ingsw.model;
import static it.polimi.ingsw.utils.Utils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.junit.jupiter.api.Test;

public class AssistantCardTest {

    private String readCards() throws IOException {
        BufferedReader input = new BufferedReader(new FileReader(CARDS_RESOURCE_PATH));
        return input.readLine();
    }

    int value=3;
    AssistantCard card=new AssistantCard(value,2);

    @Test
    void getValue(){
        assertEquals(value,card.getValue());
    }

    @Test
    void getMotherNatureMovements(){
        assertEquals(2,card.getMotherNatureMovements());
    }

    @Test
    void MagicMailManEffectHandler(){
        AssistantCard assistantCard=new AssistantCard(7,3);
        MagicMailmanEffectHandler magicMailmanEffectHandler=new MagicMailmanEffectHandler(assistantCard);
        magicMailmanEffectHandler.applyEffect();
        assertEquals(2,assistantCard.getExtraMotherNatureMovements());
        magicMailmanEffectHandler.removeEffect();
        assertEquals(0,assistantCard.getExtraMotherNatureMovements());
    }

    @Test
    void TesttoString()
    {
        assertEquals("AssistantCard [value=3, motherNatureMovements=2]",(card.toString()));
    }

    /*
    @Test
    void getAssistantCards(){
        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AssistantCardsManager assistantCardsManager = new AssistantCardsManager(jsonCards);

        ArrayList<AssistantCard> ascm = new ArrayList<>();

        ascm = assistantCardsManager.getAssistantCards();
        System.out.println(ascm.size());

        assertEquals(10,ascm.size());
    }
    @Test
    void assistantCarddManager(){

        String jsonCards = null;
        try {
            jsonCards = readCards();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Player p1 = new Player("PROVA", 7);
        Player p2 = new Player("PROVA2",7);
        Player p3 = new Player("PROVA3",7);


        Game game = new Game(3,false,jsonCards);
        game.addPlayer(p1);
        game.addPlayer(p2);
        game.addPlayer(p3);

        AssistantCardsManager assistantCardsManager = new AssistantCardsManager(jsonCards);

        game.startGame();

        game.getPlayers().get(0).setWizard(Wizards.FIRST);

        game.getCardsManager().initializeCardsForPlayer(Wizards.FIRST);

        assistantCardsManager.setCurrentCardForPlayer(Wizards.FIRST,7);

        System.out.println(assistantCardsManager.getCurrentCardForPlayer(Wizards.FIRST));
    }

     */
}