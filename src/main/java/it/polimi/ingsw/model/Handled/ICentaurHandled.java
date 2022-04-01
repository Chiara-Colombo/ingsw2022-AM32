package it.polimi.ingsw.model.Handled;

public interface ICentaurHandled {
    void setTowerInfluenceForIslands(int groupOfIslands, int influence);
    int getTowerInfluenceForIslands(int groupOfIslands);
}