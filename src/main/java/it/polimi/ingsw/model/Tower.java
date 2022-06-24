package it.polimi.ingsw.model;

public class Tower {

    private TowersColors color;

    /**
     * Constructor of Tower Class
     */
    public Tower(TowersColors color){
        this.color = color;
    }

    /**
     * Getter that returns the Tower color
     * @return the color value of Tower
     */
    public TowersColors getColor() {
        return color;
    }

    /**
     * Method that sets a certain color to the tower
     * @param color  the selected color
     */

    public void setColor(TowersColors color) {
        this.color = color;
    }
}
