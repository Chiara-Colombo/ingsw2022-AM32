package it.polimi.ingsw.client.gui;

import javafx.scene.shape.Rectangle;

public class TileRectangle extends Rectangle {
    private final int index;

    public TileRectangle(int index) {
        super();
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
