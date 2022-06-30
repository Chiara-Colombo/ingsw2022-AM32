package it.polimi.ingsw.client.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class TileImage {
    private final int index;
    private final ImageView imageView;

    /**
     * Class constructor
     */

    public TileImage(int index, ImageView imageView) {
        this.imageView = imageView;
        this.index = index;
    }

    /**
     * Getter for the tile index
     * @return the tile index
     */

    public int getIndex() {
        return this.index;
    }

    /**
     * Getter for the Image view
     */

    public ImageView getImageView() {
        return this.imageView;
    }
}
