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
}
