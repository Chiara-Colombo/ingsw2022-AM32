package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IExtraInfluenceIsland;
import it.polimi.ingsw.model.Handled.IMonkHandled;
import it.polimi.ingsw.model.Handled.INoEntry;

import java.util.*;

public class IslandsManager implements IMonkHandled {
    private final int islandsSize = 12;
    private final ArrayList<Island> islands;
    private final HashMap<Integer, Integer> groupOfIslands;

    /**
     * Class constructor
     */

    IslandsManager() {
        this.islands = new ArrayList<>();
        for (int i = 0; i < this.islandsSize; i++) {
            this.islands.add(new Island(i));
        }
        this.groupOfIslands = new HashMap<>();
        for (int i = 0; i < this.islandsSize; i++) {
            this.groupOfIslands.put(i, i);
        }
    }

    public int getIslandsSize() {
        return this.islandsSize;
    }

    /**
     * Method that adds students on an island
     * @param student pawn of a student
     * @param islandIndex index od an island
     */

    void addStudentOnIsland(Pawn student, int islandIndex) {
        this.islands.get(islandIndex).addStudent(student);
    }

    /**
     * Method that get the island
     * @param islandIndex index of an island
     * @return the island
     */

    public IIsland getIsland(int islandIndex) {
        return this.islands.get(islandIndex);
    }

    /**
     * Method that get an island with a ban
     * @param islandIndex index of an island
     * @return island with a ban
     */

    public INoEntry getNoEntryIsland(int islandIndex) {
        return this.islands.get(islandIndex);
    }

    public IExtraInfluenceIsland getExtraInfluenceIsland(int islandIndex){
        return this.islands.get(islandIndex);

    }
    /**
     * Method for getting the islands
     * @return islands, each  index of arraylist<arraylist<island>> it's a group and arraylist<island> represents
     * the island of that group
     */

    public ArrayList<ArrayList<IIsland>> getIslands() {
        int startIndex = 0, zGroup = this.groupOfIslands.get(0);
        boolean breakpoint = false;
        for (int i = 0; i < this.islandsSize; i++) {
            if (this.groupOfIslands.get(i) != zGroup) breakpoint = true;
            if (this.groupOfIslands.get(i) == zGroup && breakpoint) {
                startIndex = i;
                break;
            }
        }
        ArrayList<ArrayList<IIsland>> groups = new ArrayList<>();
        ArrayList<IIsland> islandsList = new ArrayList<>();
        int lastgroup = this.groupOfIslands.get(startIndex);
        for (int i = 0; i < this.islandsSize; i++) {
            int index = (i + startIndex) % this.islandsSize;
            if (this.groupOfIslands.get(index) != lastgroup) {
                groups.add(new ArrayList<>(islandsList));
                islandsList.clear();
            }
            islandsList.add(this.islands.get(index));
            lastgroup = this.groupOfIslands.get(index);
        }
        groups.add(new ArrayList<>(islandsList));
        return groups;
    }

    /**
     * Getter of all islands
     * @return ArrayList of islands
     */

    public ArrayList<IIsland> getAllIslands() {
        return new ArrayList<>(this.islands);
    }

    /**
     * Method that sets a Tower on an island
     * @param tower that needs to be placed
     * @param island index of the island where the tower needs to be placed
     */

    public void setTowerOnIsland(Tower tower, int island){
        this.islands.get(island).setTower(tower);
    }


    /**
     * Method for merging two islands
     * @param groupOfIslands1 index of first island group
     * @param groupOfIslands2 index of second island group
     */

    public void mergeIslands(int groupOfIslands1, int groupOfIslands2){
        final int group = Math.max(groupOfIslands1, groupOfIslands2);
        this.groupOfIslands.forEach((key, value) -> {
            if (value == groupOfIslands1 || value == groupOfIslands2) {
                this.groupOfIslands.put(key, group);
            }
        });
    }

    /**
     * Method for merging three islands
     * @param groupOfIslands1 index of first island group
     * @param groupOfIslands2 index of second island group
     * @param groupOfIslands3 index of third island group
     */

    public void mergeIslands(int groupOfIslands1, int groupOfIslands2, int groupOfIslands3){
        final int group = Math.max(Math.max(groupOfIslands1, groupOfIslands2), groupOfIslands3);
        this.groupOfIslands.forEach((key, value) -> {
            if (value == groupOfIslands1 || value == groupOfIslands2 || value == groupOfIslands3) {
                this.groupOfIslands.put(key, group);
            }
        });
    }

    /**
     * Method that gets a group of island
     * @param islandIndex index of an island
     * @return group of islands
     */

    public int getIslandGroup(int islandIndex) {
        return this.groupOfIslands.get(islandIndex);
    }

    /**
     * Method that gets the indexes of groups of islands
     * @param islandIndex index of an islands
     * @return
     */

    public List<Integer> getIslandsGroupIndexes(int islandIndex) {
        return this.islands
                .stream()
                .map(Island::getIndex)
                .filter(index -> this.groupOfIslands.get(index) == this.groupOfIslands.get(islandIndex))
                .toList();
    }

    /**
     * Method that set the students on an island
     * @param student pawn of a student
     * @param island index of an island
     */

    @Override
    public void setStudentOnIsland(Pawn student, int island) {
        this.islands.get(island).addStudent(student);
    }
}
