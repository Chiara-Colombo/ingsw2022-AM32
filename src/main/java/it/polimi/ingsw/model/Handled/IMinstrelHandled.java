package it.polimi.ingsw.model.Handled;

import it.polimi.ingsw.model.Pawn;
import it.polimi.ingsw.model.PawnsColors;

import java.util.ArrayList;

public interface IMinstrelHandled {

   void swapPawns(ArrayList<Integer> entrancePawnsIndexes, ArrayList<PawnsColors> diningRoomPawns);
}
