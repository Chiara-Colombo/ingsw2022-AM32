package it.polimi.ingsw.model;

/**
 * Pawn colors Enumeration
 */

public enum PawnsColors {
        PINK(),
        GREEN(),
        BLUE(),
        YELLOW(),
        RED();
        /**
         * Method for getting a random color of pawn
         */
        public static PawnsColors getRandom() {
                return values()[(int) (Math.random() * values().length)];
        }
}
