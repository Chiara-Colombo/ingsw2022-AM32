package it.polimi.ingsw.model;

/**
 * Enumeation of Characters
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

    /**
     * Assigns directly the coin value,funzione per incrementare se usata
     */
    private final int coinValue;

    Characters (int coinValue){
        this.coinValue = coinValue;
    }

    public int getCoinValue() {
        return coinValue;
    }
}
