package it.polimi.ingsw.model.Handled;

import it.polimi.ingsw.model.PawnsColors;

public interface IMushroomManHandled {
    void setInfluenceForColor(PawnsColors color, int influence);
    int getInfluenceForColor(PawnsColors color);
}
