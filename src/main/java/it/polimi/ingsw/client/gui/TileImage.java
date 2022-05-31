package it.polimi.ingsw.client.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class TileImage extends ImageView {
    private final int index;

    public TileImage(int index, Image image, double x, double y, double width, double height) {
        super(image);
        this.setLayoutY(y);
        this.setLayoutX(x);
        this.setFitHeight(height);
        this.setFitWidth(width);
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
