package it.polimi.ingsw.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import org.junit.jupiter.api.Test;

public class AssistantCardTest {

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
}