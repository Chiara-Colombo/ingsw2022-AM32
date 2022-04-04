package it.polimi.ingsw.model.Handled;

import it.polimi.ingsw.model.EffectHandler;

public interface ICentaurHandled {
    void setTowerInfluenceForIslands(int groupOfIslands, int influence);
    int getTowerInfluenceForIslands(int groupOfIslands);
}