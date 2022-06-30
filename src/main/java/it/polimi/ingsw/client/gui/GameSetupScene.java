package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.WizardCardResponse;
import it.polimi.ingsw.model.Wizards;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.HashMap;

import static it.polimi.ingsw.utils.FXUtils.*;
import static it.polimi.ingsw.utils.Utils.*;

public class GameSetupScene extends Scene {
    private final Pane MAIN_PANE;

    /**
     * Class Constructor
     */

    public GameSetupScene(Pane MAIN_PANE) {
        super(MAIN_PANE, GUI_WIDTH, GUI_HEIGHT);
        this.MAIN_PANE = MAIN_PANE;
        this.MAIN_PANE.setBackground(Background.fill(Paint.valueOf("#69BAE9")));
    }

    /**
     * Method that shows in the GUI the available Wizard Cards
     * @param availableWizards cards that can be chosen by the player
     * @param gui
     */

    public void showWizardCards(ArrayList<Wizards> availableWizards, GUI gui) {
        this.MAIN_PANE.getChildren().clear();
        final HashMap<ImageView, Wizards> wizardsHashMap = new HashMap<>();
        Label title = new Label("Scegli una carta mago");
        title.setLayoutY(50);
        title.setAlignment(Pos.CENTER);
        title.setMinWidth(GUI_WIDTH);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Berlin Sans FB", 40));
        ArrayList<ImageView> imageViews = new ArrayList<>();
        availableWizards.forEach(wizard -> {
            ImageView imageView = new ImageView(WIZARDS_IMAGE_MAP.get(wizard));
            wizardsHashMap.put(imageView, wizard);
            imageViews.add(imageView);
        });
        final int MARGIN_X = 40, CARD_WIDTH = GUI_WIDTH / 4 - 2 * MARGIN_X;
        final int CARD_HEIGHT = (int) ((WIZARDS_IMAGE_MAP.get(Wizards.FIRST).getHeight() / WIZARDS_IMAGE_MAP.get(Wizards.FIRST).getWidth()) * CARD_WIDTH);
        final int MARGIN_Y = (GUI_HEIGHT - CARD_HEIGHT) / 2;
        for (int i = 0; i<imageViews.size(); i++) {
            imageViews.get(i).setCursor(Cursor.HAND);
            imageViews.get(i).setPreserveRatio(true);
            imageViews.get(i).setFitWidth(CARD_WIDTH);
            imageViews.get(i).setLayoutX(GUI_WIDTH / 4.0 * i + MARGIN_X + ((4 - availableWizards.size()) * (GUI_WIDTH / 4.0)) / 2);
            imageViews.get(i).setLayoutY(MARGIN_Y);
            imageViews.get(i).setOnMouseClicked(new ImageViewHandler(wizardsHashMap.get(imageViews.get(i)), gui));
        }
        this.MAIN_PANE.getChildren().add(title);
        this.MAIN_PANE.getChildren().addAll(imageViews);
    }

    /**
     * Method that shows that another player is choosing the Wizard Card
     */

    public void showPlayerChoosingWizard() {
        this.MAIN_PANE.getChildren().clear();
        Label title = new Label("Un altro giocatore sta scegliendo la carta mago");
        title.setLayoutY(50);
        title.setAlignment(Pos.CENTER);
        title.setMinWidth(GUI_WIDTH);
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(Font.font("Berlin Sans FB", 40));
        ProgressIndicator progressIndicator = new ProgressIndicator(-1.0);
        progressIndicator.setStyle("-fx-progress-color: #3040de;");
        progressIndicator.setLayoutX(GUI_WIDTH / 2.0);
        progressIndicator.setLayoutY(GUI_HEIGHT / 2.0);
        this.MAIN_PANE.getChildren().addAll(title, progressIndicator);
    }
}
class ImageViewHandler implements EventHandler<MouseEvent> {

    private final Wizards wizard;
    private final GUI gui;

    /**
     * Class Constructor
     */

    ImageViewHandler(Wizards wizard, GUI gui) {
        this.wizard = wizard;
        this.gui = gui;
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public void handle(MouseEvent event) {
        WizardCardResponse wizardCardResponse = new WizardCardResponse(this.wizard);
        this.gui.getController().sendObjectMessage(wizardCardResponse);
        this.gui.showPlayerChoosingWizard();
    }
}
