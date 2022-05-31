package it.polimi.ingsw.model;

/**
 * Enumerator for the Towers Colors
 */
public enum TowersColors {
        NONE("N"),
        BLACK("B"),
        WHITE("W"),
        GREY("G");


        private final String towerSymbol;
        /**
         *
         */
        TowersColors (String towerSymbol){
                this.towerSymbol = towerSymbol;
        }

        /**
         * Getter for the Tower Symbol
         * @return the value of the towerSymbol
         */
        public String getTowerSymbol() {
                return towerSymbol;
        }
}
