package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ICentaurHandled;
import it.polimi.ingsw.model.Handled.IMonkHandled;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board implements IMonkHandled, ICentaurHandled {

    private final ArrayList<Island> islands;
    private int motherNature;
    private final EnumMap<PawnsColors, Integer> bag;
    private final ArrayList<Cloud> clouds;
    private final Collection<Pawn> availableProfessors;
    private int coinsSupply = 20;
    private HashMap<Integer,Integer> islandsTowerMultiplier;

    /**
     * Constructor of Board class
     * @param numOfPlayers the number of players
     */
    Board(int numOfPlayers) {

        /**initialize motherNature value to a random value beetween 0 and 11*/
        this.motherNature = ThreadLocalRandom.current().nextInt(0, 11);
        this.islands = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            this.islands.add(new Island(i));
        }
        this.bag = new EnumMap<>(PawnsColors.class);
        this.bag.put(PawnsColors.BLUE, 26);
        this.bag.put(PawnsColors.GREEN, 26);
        this.bag.put(PawnsColors.PINK, 26);
        this.bag.put(PawnsColors.RED, 26);
        this.bag.put(PawnsColors.YELLOW, 26);
        this.clouds = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            this.clouds.add(new Cloud());
        }
        this.availableProfessors = new ArrayList<>();
        this.availableProfessors.add(new Pawn(PawnsColors.BLUE));
        this.availableProfessors.add(new Pawn(PawnsColors.PINK));
        this.availableProfessors.add(new Pawn(PawnsColors.RED));
        this.availableProfessors.add(new Pawn(PawnsColors.GREEN));
        this.availableProfessors.add(new Pawn(PawnsColors.YELLOW));
        for (int i = 0; i < this.islands.size(); i++) {
            if (i == this.motherNature || i % 6 == this.motherNature % 6) continue;
            final int index = i;
            this.drawFromBag().ifPresent(pawn -> this.setStudentOnIsland(pawn, index));
        }
    }

    /**
     * Method that sets students tiles  on cloud
     * @param student  tile that needs to be setted
     * @param cloud  tile where student need to be placed
     */
    void setStudentOnCloud(Pawn student,int cloud){
        clouds.get(cloud).addStudent(student);
    }

    /**
     * Method that removes students from  cloud
     * @param index
     * @param cloud
     */
    void removeStudentFromCloud(int index,int cloud){
        clouds.get(cloud).removeStudent(index);}


    /**
     * Method that sets a Tower on an island
     * @param tower that needs to be placed
     * @param island index of the island where the tower needs to be placed
     */
    void setTowerOnIsland(Tower tower,int island){

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
     * Getter that returns the islands
     * @return collection of Island
     */
    public ArrayList<Island> getIslands() {
        return islands;
    }


    /**
     * Method for moving MotherNature
     * @param index the moves Mothernature has to do
     */
    public int moveMotherNature(int index){
        if (this.motherNature + index >= islands.size()) {
           return this.motherNature = (this.motherNature + index) % (islands.size());
        } else {
            return this.motherNature= this.motherNature + index;
        }
    }

    /**
     * Getter of motherNature,which is an index
     * @return the value of motherNature
     */
    public int getMotherNature() {
        return this.motherNature;
    }

    /**
     * Method that adds coins
     * @param coins the coins that are added to a player
     */
    void addCoins(int coins){
        if(this.coinsSupply + coins < 20)
            this.coinsSupply += coins;
        else this.coinsSupply = 20;
    }

    /**
     *  Method that gives coins
     */
    void giveCoin(){
        if (this.coinsSupply > 0)
            this.coinsSupply--;
        else this.coinsSupply = 0;
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
    void mergeIslands(Collection<Island> islands){
        int group = islands.stream().mapToInt( Island::getGroupOfIslands ).max().getAsInt();
        islands.forEach( island -> island.setGroupOfIslands(group));
    }

    /**
     * Check if the bag variable is empty
     * @return true if bag is empty
     *         false if bag is not empty
     */
    boolean isBagEmpty() {
        return this.bag.get(PawnsColors.RED) == 0 &&
                this.bag.get(PawnsColors.BLUE) == 0 &&
                this.bag.get(PawnsColors.PINK) == 0 &&
                this.bag.get(PawnsColors.GREEN) == 0 &&
                this.bag.get(PawnsColors.YELLOW) == 0;
    }

    Optional<Pawn> drawFromBag() {
        if (!this.isBagEmpty()) {
            PawnsColors color;
            final ArrayList<PawnsColors> colors = new ArrayList<>(Arrays.asList(PawnsColors.values()));
            do {
                color = colors.remove((int) Math.floor(Math.random() * colors.size()));
            } while(this.bag.get(color) == 0);
            this.bag.put(color, this.bag.get(color) - 1);
            return Optional.of(new Pawn(color));
        }
        return Optional.empty();
    }

    int getIslandSize(){
        return islands.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStudentOnIsland(Pawn student, int island) {
        this.islands.get(island).addStudent(student);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTowerInfluenceForIslands(int groupOfIslands, int influence) {
        this.islandsTowerMultiplier.replace(groupOfIslands, influence);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTowerInfluenceForIslands(int groupOfIslands) {
        return this.islandsTowerMultiplier.get(groupOfIslands);
    }
}
