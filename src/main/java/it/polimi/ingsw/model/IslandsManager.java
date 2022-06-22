package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.IMonkHandled;
import it.polimi.ingsw.model.Handled.INoEntry;

import java.util.*;

public class IslandsManager implements IMonkHandled {
    private final int islandsSize = 12;
    private final ArrayList<Island> islands;
    private final HashMap<Integer, Integer> groupOfIslands;

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

    void addStudentOnIsland(Pawn student, int islandIndex) {
        this.islands.get(islandIndex).addStudent(student);
    }

    public IIsland getIsland(int islandIndex) {
        return this.islands.get(islandIndex);
    }
    public INoEntry getNoEntryIsland(int islandIndex) {
        return this.islands.get(islandIndex);
    }

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
     * Method for merging islands whenever it's needed
     */
    public void mergeIslands(int groupOfIslands1, int groupOfIslands2){
        final int group = Math.max(groupOfIslands1, groupOfIslands2);
        this.groupOfIslands.forEach((key, value) -> {
            if (value == groupOfIslands1 || value == groupOfIslands2) {
                this.groupOfIslands.put(key, group);
            }
        });
    }

    public void mergeIslands(int groupOfIslands1, int groupOfIslands2, int groupOfIslands3){
        final int group = Math.max(Math.max(groupOfIslands1, groupOfIslands2), groupOfIslands3);
        this.groupOfIslands.forEach((key, value) -> {
            if (value == groupOfIslands1 || value == groupOfIslands2 || value == groupOfIslands3) {
                this.groupOfIslands.put(key, group);
            }
        });
    }

    public int getIslandGroup(int islandIndex) {
        return this.groupOfIslands.get(islandIndex);
    }

    public List<Integer> getIslandsGroupIndexes(int islandIndex) {
        return this.islands
                .stream()
                .map(Island::getIndex)
                .filter(index -> this.groupOfIslands.get(index) == this.groupOfIslands.get(islandIndex))
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStudentOnIsland(Pawn student, int island) {
        this.islands.get(island).addStudent(student);
    }
}
