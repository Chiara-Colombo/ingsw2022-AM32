package it.polimi.ingsw.model;

/**
 * Pawn colors Enumeration
 */

public enum PawnsColors {
        PINK,
        GREEN,
        BLUE,
        YELLOW,
        RED;

        public static PawnsColors getRandom() {
                return values()[(int) (Math.random() * values().length)];
        }
}
