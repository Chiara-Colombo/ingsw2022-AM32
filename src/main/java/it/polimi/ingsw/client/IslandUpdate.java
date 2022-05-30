package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.model.TowersColors;

import java.io.Serializable;
import java.util.ArrayList;

public class IslandUpdate implements Serializable {
    private final int groupOfIslands, index;
    private final boolean tower, noEntry;
    private final TowersColors towerColor;
    private final ArrayList<PawnsColors> students;

    public IslandUpdate(int groupOfIslands, int index, boolean tower, boolean noEntry, TowersColors towerColor, ArrayList<PawnsColors> students) {
        this.index = index;
        this.groupOfIslands = groupOfIslands;
        this.tower = tower;
        this.noEntry = noEntry;
        this.towerColor = towerColor;
        this.students = students;
    }

    public int getGroupOfIslands() {
        return this.groupOfIslands;
    }

    public int getIndex() {
        return this.index;
    }

    public boolean hasTower() {
        return this.tower;
    }

    public boolean isNoEntry() {
        return this.noEntry;
    }

    public TowersColors getTowerColor() {
        return this.towerColor;
    }

    public ArrayList<PawnsColors> getStudents() {
        return this.students;
    }
}
