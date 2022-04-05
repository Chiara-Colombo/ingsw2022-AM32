package it.polimi.ingsw.model;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

import org.junit.jupiter.api.Test;

public class AssistantCardTest {

    AssistantCard card=new AssistantCard(1,2,1);

    @Test
    void getValue(){
        assertEquals(1,card.getValue());
    }
    @Test
    void getmotherNaturemov(){
        assertEquals(2,card.getMotherNatureMovements());
    }


}
