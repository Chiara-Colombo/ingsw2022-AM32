package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnsColors;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardUpdateContent implements Serializable {
    private final int motherNature, coinsSupply;
    private final ArrayList<ArrayList<IslandUpdate>> islands;
    private final ArrayList<PawnsColors> availableProfessors;
    private final ArrayList<CloudUpdate> clouds;

    /**
     * Class constructor
     * @param motherNature mother nature
     * @param coinsSupply  coins
     * @param islands list of islands
     * @param availableProfessors list of available professors
     * @param clouds list of clouds
     */

    public BoardUpdateContent(int motherNature,
                              int coinsSupply,
                              ArrayList<ArrayList<IslandUpdate>> islands,
                              ArrayList<PawnsColors> availableProfessors,
                              ArrayList<CloudUpdate> clouds) {
        this.motherNature = motherNature;
        this.coinsSupply = coinsSupply;
        this.islands = islands;
        this.availableProfessors = availableProfessors;
        this.clouds = clouds;
    }

    /**
     * Getter for mother nature
     * @return mother nature
     */

    public int getMotherNature() {
        return this.motherNature;
    }

    /**
     * Getter for coins Supply
     * @return coins supply
     */

    public int getCoinsSupply() {
        return this.coinsSupply;
    }

    /**
     * Getter for the islands
     * @return list of islands
     */

    public ArrayList<ArrayList<IslandUpdate>> getIslands() {
        return this.islands;
    }

    /**
     * Getter for available professors
     * @return list of available professors
     */

    public ArrayList<PawnsColors> getAvailableProfessors() {
        return this.availableProfessors;
    }

    /**
     * Getter for clouds
     * @return list of clouds
     */

    public ArrayList<CloudUpdate> getClouds() {
        return this.clouds;
    }
}
