package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.model.TowersColors;

import java.io.Serializable;
import java.util.ArrayList;

public class IslandUpdate implements Serializable {
    private final int index;
    private final boolean tower, noEntry;
    private final TowersColors towerColor;
    private final ArrayList<PawnsColors> students;

    /**
     * Class constructor
     * @param index index of the island
     * @param tower says if there is a tower on the island or not
     * @param noEntry says if there is a ban on an island
     * @param towerColor color of a tower
     * @param students list of students
     */

    public IslandUpdate(int index, boolean tower, boolean noEntry, TowersColors towerColor, ArrayList<PawnsColors> students) {
        this.index = index;
        this.tower = tower;
        this.noEntry = noEntry;
        this.towerColor = towerColor;
        this.students = students;
    }

    /**
     * Getter for the island index
     * @return the index
     */

    public int getIndex() {
        return this.index;
    }

    /**
     * Method that says if there is a tower on the island
     * @return true if it has a tower
     *          false if it hasn't
     */

    public boolean hasTower() {
        return this.tower;
    }

    /**
     * Method that says if there is a ban of the island
     * @return true if there is a ban
     *          fales if there isn't
     */

    public boolean isNoEntry() {
        return this.noEntry;
    }

    /**
     * Getter for the tower colors
     * @return tower colors
     */

    public TowersColors getTowerColor() {
        return this.towerColor;
    }

    /**
     * Getter for the students
     * @return list of students
     */

    public ArrayList<PawnsColors> getStudents() {
        return this.students;
    }
}
