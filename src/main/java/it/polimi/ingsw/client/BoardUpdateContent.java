package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnsColors;

import java.io.Serializable;
import java.util.ArrayList;

public class BoardUpdateContent implements Serializable {
    private final int motherNature, coinsSupply;
    private final ArrayList<IslandUpdate> islands;
    private final ArrayList<PawnsColors> availableProfessors;
    private final ArrayList<CloudUpdate> clouds;

    public BoardUpdateContent(int motherNature, int coinsSupply, ArrayList<IslandUpdate> islands, ArrayList<PawnsColors> availableProfessors, ArrayList<CloudUpdate> clouds) {
        this.motherNature = motherNature;
        this.coinsSupply = coinsSupply;
        this.islands = islands;
        this.availableProfessors = availableProfessors;
        this.clouds = clouds;
    }


    public int getMotherNature() {
        return this.motherNature;
    }

    public int getCoinsSupply() {
        return this.coinsSupply;
    }

    public ArrayList<IslandUpdate> getIslands() {
        return this.islands;
    }

    public ArrayList<PawnsColors> getAvailableProfessors() {
        return this.availableProfessors;
    }

    public ArrayList<CloudUpdate> getClouds() {
        return this.clouds;
    }
}
