package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.BoardUpdateContent;
import it.polimi.ingsw.client.IslandUpdate;
import it.polimi.ingsw.model.PawnsColors;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static it.polimi.ingsw.utils.Utils.*;

public class GameScene extends Scene {
    private static Pane boardPane;
    private static ArrayList<Pane> playersPanes;

    private GameScene(Parent root) {
        super(root, GUI_WIDTH, GUI_HEIGHT);
    }

    public static GameScene getInstance() {
        Pane root = new Pane();
        boardPane = new Pane();
        playersPanes = new ArrayList<>();
        return new GameScene(root);
    }

    public static void showBoard(Pane pane, BoardUpdateContent boardUpdate) {
        boardPane.setBackground(Background.fill(Paint.valueOf("#69BAE9")));
        boardPane.getChildren().clear();
        final int MARGIN = 200, BOARD_PANE_WIDTH = GUI_WIDTH - 2 * MARGIN, BOARD_PANE_HEIGHT = GUI_HEIGHT - MARGIN, RADIUS = 190, DIM = 60;
        boardPane.setMaxWidth(BOARD_PANE_WIDTH);
        boardPane.setMinWidth(BOARD_PANE_WIDTH);
        boardPane.setMaxHeight(BOARD_PANE_HEIGHT);
        boardPane.setMinHeight(BOARD_PANE_HEIGHT);
        boardPane.setLayoutX(MARGIN);
        ArrayList<IslandUpdate> islands = boardUpdate.getIslands();
        for (int i = 0; i<islands.size(); i++) {
            IslandUpdate island = islands.get(i);
            // int groupOfIslands = island.getGroupOfIslands();
            double theta = (Math.PI / 6) * i;
            Rectangle islandShape = new Rectangle();
            double x = BOARD_PANE_WIDTH / 2 + RADIUS * Math.sin(theta) - DIM / 2, y = BOARD_PANE_HEIGHT / 2 + RADIUS * Math.cos(theta) - DIM / 2;
            islandShape.setX(x);
            islandShape.setY(y);
            islandShape.setWidth(DIM);
            islandShape.setHeight(DIM);
            islandShape.setFill(Paint.valueOf("#5dc092"));
            boardPane.getChildren().add(islandShape);
            ArrayList<PawnsColors> students = island.getStudents();
            for (int j = 0; j < students.size(); j++) {
                Circle studentShape = new Circle(
                        x + (DIM / 2),
                        y + (DIM / 2),
                        DIM / (3 + students.size()),
                        PAWNS_COLORS_PAINT_ENUM_MAP.get(students.get(j))
                );
                boardPane.getChildren().add(studentShape);
            }
            if (boardUpdate.getMotherNature() == i) {
                Circle motherNatureShape = new Circle(
                        x + (DIM / 2),
                        y + (DIM / 2),
                        DIM / 3,
                        Paint.valueOf("#6e3d0c")
                );
                boardPane.getChildren().add(motherNatureShape);
            }
        }
        pane.getChildren().add(boardPane);
    }
}
