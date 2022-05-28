package it.polimi.ingsw.model;

/**
 * Pawn colors Enumeration
 */

public enum PawnsColors {
        PINK("\033[0;105m"),
        GREEN("\033[0;102m"),
        BLUE("\033[0;104m"),
        YELLOW("\033[0;103m"),
        RED("\033[0;101m");

        private final String colorANSI;

        public static PawnsColors getRandom() {
                return values()[(int) (Math.random() * values().length)];
        }

        PawnsColors (String colorANSI){
                this.colorANSI = colorANSI;
        }

        /**
         * Getter for the coin value
         * @return the value of the coinValue
         */
        public String getColorANSI() {
                return colorANSI;
        }
}
