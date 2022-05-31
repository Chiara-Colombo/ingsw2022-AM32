package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TowerTest {

    @Test
    void getColor(){
        Tower tower = new Tower(TowersColors.BLACK);
        assertEquals(TowersColors.BLACK,tower.getColor());
    }
    @Test
    void getTowerSymbol(){
        Tower tower0 = new Tower(TowersColors.BLACK);
        assertEquals("B",tower0.getColor().getTowerSymbol());
        Tower tower1 = new Tower(TowersColors.GREY);
        assertEquals("G",tower1.getColor().getTowerSymbol());
        Tower tower2 = new Tower(TowersColors.WHITE);
        assertEquals("W",tower2.getColor().getTowerSymbol());

    }

}
