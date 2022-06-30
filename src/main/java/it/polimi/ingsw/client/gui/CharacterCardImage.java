package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.Characters;
import javafx.scene.image.ImageView;

public class CharacterCardImage {
    private final Characters character;
    private final ImageView imageView;


    /**
     * Class Constructor
     * @param character Character Card that has to be showed
     * @param imageView view of the card
     */

    public CharacterCardImage(Characters character, ImageView imageView) {
        this.imageView = imageView;
        this.character = character;
    }

    /**
     * Getter for the Character
     */

    public Characters getCharacter() {
        return this.character;
    }

    /**
     * Getter for Image View
     */

    public ImageView getImageView() {
        return this.imageView;
    }
}
