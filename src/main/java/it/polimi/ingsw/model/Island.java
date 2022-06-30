
package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IExtraInfluenceIsland;
import it.polimi.ingsw.model.Handled.INoEntry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;



public class Island implements IIsland, INoEntry, IExtraInfluenceIsland {

    private final int index;
    private Optional<Tower> tower;
    private boolean noEntry, extraInfluenceIsland;
    private final ArrayList<Pawn> students;

    /**
     * Constructor Of Island Object
     * @param index index of the island
     */
    public Island(int index){
        this.index = index;
        this.students = new ArrayList<>();
        this.noEntry = false;
        this.tower = Optional.empty();
    }


    /**
     * Methods that set a tower object on an island
     * @param tower the tower to be set
     */

    void setTower(Tower tower){
        this.tower = Optional.of(tower);
    }

    /**
     * Method that adds a student pawn on the island
     * @param student the pawn needed to be added
     */

    void addStudent(Pawn student){
        this.students.add(student);
    }

    /**
     * Method which returns the island tower if it's present, override of IIsland
     * @return tower
     */

    @Override
    public Optional<Tower> getTower() {
        return this.tower;
    }

    @Override
    /**
     * Method that gets the index of a island
     * @return index
     */

    public int getIndex() {
        return this.index;
    }

    /**
     * Method that says if an island has a ban
     * @return the island has a ban
     */

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


    /**
     * Method that sets a ban on an island
     * @param noEntry the ban
     */
    @Override
    public void setNoEntry(boolean noEntry) {
        this.noEntry = noEntry;
    }

    @Override
    public void setExtraInfluenceIsland (boolean extraInfluenceIsland){
        this.extraInfluenceIsland = extraInfluenceIsland;
    }

    public boolean isExtraInfluenceIsland (){
        return this.extraInfluenceIsland;
    }

}
