package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.GUI;
import javafx.event.ActionEvent;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import static it.polimi.ingsw.utils.Utils.*;

public class MainScene extends Scene {
    private MainScene(Parent root) {
        super(root, GUI_WIDTH, GUI_HEIGHT);
    }

    public static MainScene getInstance() {
        GridPane root = new GridPane();
        initialize(root);
        return new MainScene(root);
    }

    private static void initialize(GridPane gridPane) {
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getColumnConstraints().add(new ColumnConstraints(10, 500, 1000, Priority.ALWAYS, HPos.CENTER, true));
        gridPane.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.ALWAYS, VPos.CENTER, true));
        gridPane.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.ALWAYS, VPos.CENTER, true));
        gridPane.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.ALWAYS, VPos.CENTER, true));
        gridPane.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.ALWAYS, VPos.CENTER, true));
        gridPane.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.ALWAYS, VPos.CENTER, true));
        gridPane.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.ALWAYS, VPos.CENTER, true));
        gridPane.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.ALWAYS, VPos.CENTER, true));
        gridPane.getRowConstraints().add(new RowConstraints(10, 50, 100, Priority.ALWAYS, VPos.CENTER, true));
        gridPane.setBackground(Background.fill(Paint.valueOf("#dedede")));
        Label title = new Label("Benvenuto su Eriantys");
        title.setPrefWidth(GUI_WIDTH);
        title.setAlignment(Pos.CENTER);
        title.setFont(new Font("Javanese Text", 40));
        Button playGameBtn = new Button("Gioca Online");
        playGameBtn.setFont(new Font("Berlin Sans FB", 25));
        playGameBtn.setCursor(Cursor.HAND);
        playGameBtn.setPrefWidth(GUI_WIDTH);
        playGameBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        Button exitBtn = new Button("Esci");
        exitBtn.setFont(new Font("Berlin Sans FB", 25));
        exitBtn.setCursor(Cursor.HAND);
        exitBtn.setPrefWidth(GUI_WIDTH);
        exitBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        Button rulesBtn = new Button("Regole");
        rulesBtn.setFont(new Font("Berlin Sans FB", 25));
        rulesBtn.setCursor(Cursor.HAND);
        rulesBtn.setPrefWidth(GUI_WIDTH);
        rulesBtn.setBackground(Background.fill(Paint.valueOf("#fafafa")));
        playGameBtn.addEventHandler(ActionEvent.ACTION, event -> {
            GUI.startGame();
        });
        exitBtn.addEventHandler(ActionEvent.ACTION, event -> {
            GUI.exit();
        });
        rulesBtn.addEventHandler(ActionEvent.ACTION, event -> {
            System.out.println("Rules");
        });
        gridPane.add(title, 1, 1, 8, 1);
        gridPane.add(playGameBtn, 2, 3, 6, 1);
        gridPane.add(rulesBtn, 1, 5, 2, 1);
        gridPane.add(exitBtn, 7, 5, 2,1);
    }
}
