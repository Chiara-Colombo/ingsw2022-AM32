package it.polimi.ingsw.client.gui;

import javafx.scene.shape.Rectangle;

public class IslandRectangle extends Rectangle {
    private final int index;

    public IslandRectangle(int index) {
        super();
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
