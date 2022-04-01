
package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.INoEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;


public class Island implements IIsland, INoEntry {

    private Optional<Tower> tower;
    private int groupOfIslands;
    private boolean noEntry;
    private final ArrayList<Pawn> students;

    /**
     * Constructor Of Island Object
     */

    public Island(int groupOfIslands){
        this.students = new ArrayList<>();
        this.noEntry = false;
        this.groupOfIslands = groupOfIslands;
        this.tower = Optional.empty();
    }


    /**
     * Methods that set a tower object on a island
     * @param tower the tower to be set
     */

    public void setTower(Tower tower){
        this.tower = Optional.of(tower);
    }

    /**
     *
     * @param groupOfIslands
     */

    public void setGroupOfIslands(int groupOfIslands){
       this.groupOfIslands = groupOfIslands;
    }

    /**
     * Method that adds a student pawn on the island
     * @param student the pawn needed to be added
     */
    public void addStudent(Pawn student){
        this.students.add(student);
    }

    /**
     * Method which returns the island tower if it's present, override of IIsland
     */
    @Override
    public Optional<Tower> getTower() {
        return this.tower;
    }


    /**
     *
     * @return
     */
    @Override
    public int getGroupOfIslands() {
        return this.groupOfIslands;
    }

    @Override
    public boolean isNoEntry() {
        return this.noEntry;
    }

    /**
     * Method which returns an iterator of the students pawns on the island   Override of IIsland
     * @return iterator of Pawn
     */
    @Override
    public Iterator<Pawn> getStudents() {
        return this.students.iterator();
    }

    @Override
    public void setNoEntry(boolean noEntry) {
        this.noEntry = noEntry;
    }
}
