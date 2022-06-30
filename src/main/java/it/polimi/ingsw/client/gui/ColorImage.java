package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.PawnsColors;
import javafx.scene.image.ImageView;

public class ColorImage {
    private final PawnsColors color;
    private final ImageView imageView;

    /**
     * Class constructor
     */

    public ColorImage(PawnsColors color, ImageView imageView) {
        this.imageView = imageView;
        this.color = color;
    }

    /**
     * Getter for Pawns color
     * @return the color of the pawn
     */

    public PawnsColors getColor() {
        return this.color;
    }

    /**
     * Getter for image View
     */

    public ImageView getImageView() {
        return this.imageView;
    }
}
