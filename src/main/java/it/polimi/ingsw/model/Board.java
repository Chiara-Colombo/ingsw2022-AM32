package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ICentaurHandled;
import it.polimi.ingsw.model.Handled.IMonkHandled;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board implements IMonkHandled, ICentaurHandled {

    private final ArrayList<Island> islands;
    private int motherNature;
    /*private Map<PawnsColors,ArrayList<Integer>> bag?*/
    private final EnumMap<PawnsColors,ArrayList<Pawn>> bag;
    private ArrayList<Cloud> clouds;
    /**public Collection<Cloud> clouds;*/
    private Collection<Pawn> availableProfessors=new ArrayList<>();
    private int coinsSupply=20;
    private HashMap<Integer,Integer> islandsTowerMultiplier;

    /**
     * Constructor of Board class
     */

    public Board(int numOfPlayers) {

        /**initialize motherNature value to a random value beetween 0 and 11*/
        this.motherNature = ThreadLocalRandom.current().nextInt(0, 11);
        this.islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {this.islands.add(new Island(0));}
        this.bag = new EnumMap<>(PawnsColors.class);
        this.clouds = new ArrayList<>();
        for(int i=0;i<numOfPlayers;i++) {this.clouds.add(new Cloud()); }

    }

    /**
     * Method that sets students tiles  on cloud
     * @param student  tile that needs to be setted
     * @param cloud  tile where student need to be placed
     */

    public void setStudentOnCloud(Pawn student,int cloud){
        clouds.get(cloud).addStudent(student);
    }

    /**
     * Method that removes students from  cloud
     * @param index
     * @param cloud
     */

        /**maybe Pawn not void*/
    public void removeStudentFromCloud(int index,int cloud){
        clouds.get(cloud).removeStudent(index);}


    /**
     * Method that sets a Tower on an island
     * @param tower that needs to be placed
     * @param island index of the island where the tower needs to be placed
     */

    public void setTowerOnIsland(Tower tower,int island){

    }

    /**
     * Getter that returns the available professors pawns
     * @return collection of abailable professors pawns
     */

    public Collection<Pawn> getAvailableProfessors() {
        return availableProfessors;
    }

    /**
     * Getter that returns the collection of clouds
     * @return collection of clouds
     */

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     *
     * @return collection of Island
     */

    public ArrayList<Island> getIslands() {
        return islands;
    }


    /**
     * Method for moving MotherNature
     *
     * @param index the moves Mothernature has to do
     *
     */

    public int moveMotherNature(int index){
        if(getMotherNature()+index> islands.size()-1)
        {
           return motherNature=(getMotherNature()+index)%(islands.size());
        }
        else
         return motherNature= motherNature+index;}

    /**
     * Getter of motherNature,which is an index
     * @return the value of motherNature
     */

    public int getMotherNature() {
        return this.motherNature;
    }

    /**
     *
     */

    public void addCoins(int coins){
        if(this.coinsSupply+ coins<20)
            this.coinsSupply += coins;
        else this.coinsSupply=20;
    }

    /**
     *
     */

    public void giveCoin(){
        this.coinsSupply--;
    }

    /**
     * Getter that returns the coinsSupply
     * @return coinsSupply value
     */
    public int getCoinsSupply() {
        return this.coinsSupply;
    }


    /**
     * Method for merging islands whenever it's needed
     */

    public void mergeIslands(Collection<Island> islands){
        int group = islands.stream().mapToInt( Island::getGroupOfIslands ).max().getAsInt();
        islands.forEach( island -> island.setGroupOfIslands(group));
    }

    /**
     * Check if the bag variable is empty
     * @return true if bag is empty
     *         false if bag is not empty
     */
    public boolean isBagEmpty(){
        return bag.isEmpty();
    }

    public int getIslandSize(){
        return islands.size();
    }

    public void setStudentOnIsland(Pawn student, int island) {
        this.islands.get(island).addStudent(student);
    }




    @Override
    public void setTowerInfluenceForIslands(int groupOfIslands, int influence) {
        this.islandsTowerMultiplier.replace(groupOfIslands, influence);
    }

    @Override
    public int getTowerInfluenceForIslands(int groupOfIslands) {
        return this.islandsTowerMultiplier.get(groupOfIslands);
    }


    /**
     * Method used for drawings students tiles from bag variable
     * @param students
     * @return student tile
     */
/**
    public Pawn drawStudentFromBag(PawnsColors students){
        return ;
    }
 */
}
