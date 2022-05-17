package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.messages.clienttoserver.WizardCardResponse;
import it.polimi.ingsw.model.Wizards;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
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
import java.util.Objects;

import static it.polimi.ingsw.utils.Utils.*;

public class GameSetupScene extends Scene {
    private GameSetupScene(Parent root) {
        super(root, GUI_WIDTH, GUI_HEIGHT);
    }

    public static GameSetupScene getInstance() {
        Pane root = new Pane();
        initialize(root);
        return new GameSetupScene(root);
    }

    private static void initialize(Pane pane) {
        pane.setBackground(Background.fill(Paint.valueOf("#69BAE9")));
    }

    public static void showWizardCards(ArrayList<Wizards> availableWizards, Pane pane, GUI gui) {
        if (Objects.nonNull(pane)) {
            pane.getChildren().clear();
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
                imageViews.get(i).setLayoutX(GUI_WIDTH / 4 * i + MARGIN_X + ((4 - availableWizards.size()) * (GUI_WIDTH / 4)) / 2);
                imageViews.get(i).setLayoutY(MARGIN_Y);
                imageViews.get(i).setOnMouseClicked(new ImageViewHandler(wizardsHashMap.get(imageViews.get(i)), gui));
            }
            pane.getChildren().add(title);
            pane.getChildren().addAll(imageViews);
        }
    }

    public static void showPlayerChoosingWizard(Pane pane) {
        if (Objects.nonNull(pane)) {
            pane.getChildren().clear();
            Label title = new Label("Un altro giocatore sta scegliendo la carta mago");
            title.setLayoutY(50);
            title.setAlignment(Pos.CENTER);
            title.setMinWidth(GUI_WIDTH);
            title.setTextAlignment(TextAlignment.CENTER);
            title.setFont(Font.font("Berlin Sans FB", 40));
            ProgressIndicator progressIndicator = new ProgressIndicator(-1.0);
            progressIndicator.setLayoutX(GUI_WIDTH / 2);
            progressIndicator.setLayoutY(GUI_HEIGHT / 2);
            pane.getChildren().addAll(title, progressIndicator);
        }
    }
}
class ImageViewHandler implements EventHandler<MouseEvent> {

    private final Wizards wizard;
    private final GUI gui;

    ImageViewHandler(Wizards wizard, GUI gui) {
        this.wizard = wizard;
        this.gui = gui;
    }

    @Override
    public void handle(MouseEvent event) {
        WizardCardResponse wizardCardResponse = new WizardCardResponse(this.wizard);
        GUI.getController().sendObjectMessage(wizardCardResponse);
        this.gui.showPlayerChoosingWizard();
    }
}
