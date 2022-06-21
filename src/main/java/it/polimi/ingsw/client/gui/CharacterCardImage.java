package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.model.Characters;
import javafx.scene.image.ImageView;

public class CharacterCardImage {
    private final Characters character;
    private final ImageView imageView;

    public CharacterCardImage(Characters character, ImageView imageView) {
        this.imageView = imageView;
        this.character = character;
    }

    public Characters getCharacter() {
        return this.character;
    }

    public ImageView getImageView() {
        return this.imageView;
    }
}
