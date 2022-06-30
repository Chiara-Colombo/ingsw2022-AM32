package it.polimi.ingsw.model.Handled;

import it.polimi.ingsw.model.Pawn;

import java.util.ArrayList;

public interface IMinstrelHandled {
   public void fromDiningToEntrance(ArrayList<Pawn> pawns);
   public void fromEntranceToDining(ArrayList<Pawn> pawns);

}
