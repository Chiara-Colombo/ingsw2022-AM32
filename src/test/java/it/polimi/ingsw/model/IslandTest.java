package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class IslandTest {

    @Test
    void setGroupOfIsland(){
        Island island = new Island(0, 0);
        int islandGroupToSet = 3;
        assertEquals(0, island.getGroupOfIslands());
        island.setGroupOfIslands(islandGroupToSet);
        assertEquals(islandGroupToSet, island.getGroupOfIslands());
    }
    @Test
    void setNoEntry(){
        Island island = new Island(0, 0);
        GrandmaHerbsEffectHandler handler = new GrandmaHerbsEffectHandler(island);
        handler.applyEffect();
        assertTrue(island.isNoEntry());
        handler.removeEffect();
        assertFalse(island.isNoEntry());
    }

    @Test
    void setTower(){
        Island island = new Island(0, 0);
        Tower tower = new Tower(TowersColors.BLACK);
        assertTrue(island.getTower().isEmpty());
        island.setTower(tower);
        assertTrue(island.getTower().isPresent());
    }

    @Test
    void Indexing(){
        Board test = new Board(3);
        int index = 4;
        IIsland island = test.getIslands().get(index);
        assertEquals(4,island.getIndex());
    }
}
