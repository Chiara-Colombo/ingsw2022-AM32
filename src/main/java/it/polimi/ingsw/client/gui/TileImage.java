package it.polimi.ingsw.client.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class TileImage {
    private final int index;
    private final ImageView imageView;

    public TileImage(int index, ImageView imageView) {
        this.imageView = imageView;
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }

    public ImageView getImageView() {
        return this.imageView;
    }
}
