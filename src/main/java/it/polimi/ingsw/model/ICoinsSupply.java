package it.polimi.ingsw.model;

/**
 * Interface for coins supply
 */
public interface ICoinsSupply {
    void addCoins(int coins);
    void giveCoin();
    int getCoinsSupply();
}
