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

    public void setColor(TowersColors color) {
        this.color = color;
    }
}
