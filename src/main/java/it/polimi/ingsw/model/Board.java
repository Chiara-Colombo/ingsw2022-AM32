package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Handled.ICentaurHandled;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Board implements ICentaurHandled, ICoinsSupply {

    private final IslandsManager islandManager;
    private int motherNature;
    private final EnumMap<PawnsColors, Integer> bag;
    private final ArrayList<Cloud> clouds;
    private final ArrayList<Pawn> availableProfessors;
    private int coinsSupply = 20, islandsTowersMultiplier;

    /**
     * Constructor of Board class
     * @param numOfPlayers the number of players
     */
    Board(int numOfPlayers) {

        /**initialize motherNature value to a random value beetween 0 and 11*/
        this.islandManager = new IslandsManager();
        this.motherNature = ThreadLocalRandom.current().nextInt(0, this.islandManager.getIslandsSize() - 1);
        this.bag = new EnumMap<>(PawnsColors.class);
        this.bag.put(PawnsColors.BLUE, 26);
        this.bag.put(PawnsColors.GREEN, 26);
        this.bag.put(PawnsColors.PINK, 26);
        this.bag.put(PawnsColors.RED, 26);
        this.bag.put(PawnsColors.YELLOW, 26);
        for (int i = 0; i < this.islandManager.getIslandsSize(); i++) {
            if (i == this.motherNature || i == (this.motherNature + this.islandManager.getIslandsSize() / 2) % this.islandManager.getIslandsSize()) continue;
            int tmp_i = i;
            this.drawFromBag().ifPresent(pawn -> this.islandManager.addStudentOnIsland(pawn, tmp_i));
        }
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
        this.islandsTowersMultiplier = 1;
    }

    public int getBagSize() {
        return this.bag.values().stream().reduce(0, (temp, now) -> temp += now);
    }

    /**
     * Method that sets students tiles  on cloud
     * @param student  tile that needs to be setted
     * @param cloud  tile where student need to be placed
     */
    public void setStudentOnCloud(Pawn student, int cloud){
        this.clouds.get(cloud).addStudent(student);
    }

    /**
     * Method that removes students from  cloud
     * @param cloud
     */
    public Pawn removeStudentFromCloud(int cloud){
        return clouds.get(cloud).removeStudent(0);
    }

    /**
     * Getter that returns the available professors pawns
     * @return collection of abailable professors pawns
     */
    public ArrayList<PawnsColors> getAvailableProfessors() {
        return new ArrayList<>(this.availableProfessors.stream().map(Pawn::getColor).toList());
    }

    public Pawn removeProfessor(int index) {
        return this.availableProfessors.remove(index);
    }

    /**
     * Getter that returns the collection of clouds
     * @return collection of clouds
     */
    public ArrayList<ICloud> getClouds() {
        return new ArrayList<>(this.clouds);
    }


    /**
     * Method for moving MotherNature
     * @param index the moves Mothernature has to do
     */
    public void moveMotherNature(int index){
        if (index >= 0 && index < this.islandManager.getIslandsSize())
            this.motherNature = index;
    }

    public IslandsManager getIslandsManager() {
        return this.islandManager;
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
    @Override
    public void addCoins(int coins){
        if(this.coinsSupply + coins < 20)
            this.coinsSupply += coins;
        else this.coinsSupply = 20;
    }

    /**
     *  Method that gives coins
     */
    @Override
    public void giveCoin(){
        if (this.coinsSupply > 0)
            this.coinsSupply--;
        else this.coinsSupply = 0;
    }

    /**
     * Getter that returns the coinsSupply
     * @return coinsSupply value
     */
    @Override
    public int getCoinsSupply() {
        return this.coinsSupply;
    }

    /**
     * Check if the bag variable is empty
     * @return true if bag is empty
     *         false if bag is not empty
     */
    public boolean isBagEmpty() {
        return this.bag.get(PawnsColors.RED) == 0 &&
                this.bag.get(PawnsColors.BLUE) == 0 &&
                this.bag.get(PawnsColors.PINK) == 0 &&
                this.bag.get(PawnsColors.GREEN) == 0 &&
                this.bag.get(PawnsColors.YELLOW) == 0;
    }

    public Optional<Pawn> drawFromBag() {
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTowersInfluence(int influence) {
        this.islandsTowersMultiplier = influence;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getTowersInfluence() {
        return this.islandsTowersMultiplier;
    }
}
