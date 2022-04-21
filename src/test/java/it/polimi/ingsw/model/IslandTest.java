package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.Assert.*;

public class IslandTest {

    @Test
    void setGroupOfIsland(){
        Island island=new Island(0);
        int islandgroupToset=3;
        assertEquals(0,island.getGroupOfIslands());
        island.setGroupOfIslands(islandgroupToset);
        assertEquals(islandgroupToset,island.getGroupOfIslands());
    }
    @Test
    void setNoEntry(){
        Island island=new Island(0);
        GrandmaHerbsEffectHandler handler=new GrandmaHerbsEffectHandler(island);
        handler.applyEffect();
        assertTrue(island.isNoEntry());

    }

}
