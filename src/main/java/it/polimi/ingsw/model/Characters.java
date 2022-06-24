package it.polimi.ingsw.model;

/**
 * Enumeration of Characters
 */

public enum Characters {
    GRANDMA_HERBS(2),
    MONK(1),
    KNIGHT(2),
    MUSHROOMS_MAN(3),
    CENTAUR(3),
    SPOILED_PRINCESS(2),
    FARMER(2),
    MAGIC_MAILMAN(1);


    private final int coinValue;
    /**
     * Assigns directly the coin value
     * @param coinValue is the coin value of a card
     */

    Characters (int coinValue){
        this.coinValue = coinValue;
    }

    /**
     * Getter for the coin value
     * @return the value of the coinValue
     */

    public int getCoinValue() {
        return this.coinValue;
    }
}
