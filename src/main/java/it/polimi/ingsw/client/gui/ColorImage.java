package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.PawnsColors;
import javafx.scene.image.ImageView;

public class ColorImage {
    private final PawnsColors color;
    private final ImageView imageView;

    public ColorImage(PawnsColors color, ImageView imageView) {
        this.imageView = imageView;
        this.color = color;
    }

    public PawnsColors getColor() {
        return this.color;
    }

    public ImageView getImageView() {
        return this.imageView;
    }
}
