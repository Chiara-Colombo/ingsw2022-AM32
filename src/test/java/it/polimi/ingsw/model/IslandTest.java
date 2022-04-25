package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class IslandTest {

    @Test
    void setGroupOfIsland(){
        Island island = new Island(0);
        int islandGroupToSet = 3;
        assertEquals(0, island.getGroupOfIslands());
        island.setGroupOfIslands(islandGroupToSet);
        assertEquals(islandGroupToSet, island.getGroupOfIslands());
    }
    @Test
    void setNoEntry(){
        Island island = new Island(0);
        GrandmaHerbsEffectHandler handler = new GrandmaHerbsEffectHandler(island);
        handler.applyEffect();
        assertTrue(island.isNoEntry());
        handler.removeEffect();
        assertFalse(island.isNoEntry());
    }

}
