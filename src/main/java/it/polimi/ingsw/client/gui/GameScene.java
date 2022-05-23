package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.messages.clienttoserver.AssistantCardResponse;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.PawnsColors;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static it.polimi.ingsw.utils.Utils.*;

public class GameScene extends Scene {
    final int SCHOOL_BOARD_WIDTH = 200,
            SCHOOL_BOARD_HEIGHT = GUI_HEIGHT - SCHOOL_BOARD_WIDTH,
            BOARD_PANE_WIDTH = GUI_WIDTH - 2 * SCHOOL_BOARD_WIDTH,
            BOARD_PANE_HEIGHT = GUI_HEIGHT - SCHOOL_BOARD_WIDTH,
            RADIUS = 190,
            DIM = 60,
            CARD_WIDTH = 100,
            CARD_HEIGHT = 175,
            CARDS_MARGIN_X = (GUI_WIDTH - 2 * SCHOOL_BOARD_WIDTH - 5 * CARD_WIDTH) / 6,
            CARDS_MARGIN_Y = (GUI_HEIGHT - SCHOOL_BOARD_WIDTH - 2 * CARD_HEIGHT) / 3;
    private final Pane BOARD_PANE, MAIN_PANE, ASSISTANT_CARDS_PANE;
    private final ArrayList<Pane> PLAYERS_PANES;
    private final Label GAME_PHASE_MESSAGE, TURN_MESSAGE;

    public GameScene(Pane MAIN_PANE) {
        super(MAIN_PANE, GUI_WIDTH, GUI_HEIGHT);
        this.MAIN_PANE = MAIN_PANE;
        this.BOARD_PANE = new Pane();
        this.ASSISTANT_CARDS_PANE = new Pane();
        this.PLAYERS_PANES = new ArrayList<>();
        this.GAME_PHASE_MESSAGE = new Label();
        this.TURN_MESSAGE = new Label();
        this.MAIN_PANE.getChildren().add(TURN_MESSAGE);
        this.MAIN_PANE.getChildren().add(GAME_PHASE_MESSAGE);
    }

    public void updateBoard(BoardUpdateContent boardUpdate) {
        this.MAIN_PANE.getChildren().remove(this.ASSISTANT_CARDS_PANE);
        this.BOARD_PANE.setBackground(Background.fill(Paint.valueOf("#69BAE9")));
        this.BOARD_PANE.getChildren().clear();
        this.BOARD_PANE.setMaxWidth(BOARD_PANE_WIDTH);
        this.BOARD_PANE.setMinWidth(BOARD_PANE_WIDTH);
        this.BOARD_PANE.setMaxHeight(BOARD_PANE_HEIGHT);
        this.BOARD_PANE.setMinHeight(BOARD_PANE_HEIGHT);
        this.BOARD_PANE.setLayoutX(SCHOOL_BOARD_WIDTH);
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
            this.BOARD_PANE.getChildren().add(islandShape);
            ArrayList<PawnsColors> students = island.getStudents();
            for (PawnsColors student : students) {
                Circle studentShape = new Circle(
                        x + (DIM / 2),
                        y + (DIM / 2),
                        DIM / 6,
                        PAWNS_COLORS_PAINT_ENUM_MAP.get(student)
                );
                this.BOARD_PANE.getChildren().add(studentShape);
            }
            if (boardUpdate.getMotherNature() == i) {
                Circle motherNatureShape = new Circle(
                        x + (DIM / 2),
                        y + (DIM / 2),
                        DIM / 3,
                        Paint.valueOf("#6e3d0c")
                );
                this.BOARD_PANE.getChildren().add(motherNatureShape);
            }
        }
        this.MAIN_PANE.getChildren().add(this.BOARD_PANE);
    }

    public void showBoard() {
        this.MAIN_PANE.getChildren().remove(this.ASSISTANT_CARDS_PANE);
        this.MAIN_PANE.getChildren().add(this.BOARD_PANE);
    }

    public void showPlayers(ArrayList<PlayerUpdate> playersUpdate) {
        AtomicInteger player = new AtomicInteger(0);
        playersUpdate.forEach(playerUpdate -> {
            Pane schoolBoard = new Pane();
            schoolBoard.setMaxWidth(SCHOOL_BOARD_WIDTH);
            schoolBoard.setMinWidth(SCHOOL_BOARD_WIDTH);
            schoolBoard.setMaxHeight(SCHOOL_BOARD_HEIGHT);
            schoolBoard.setMinHeight(SCHOOL_BOARD_HEIGHT);
            schoolBoard.setBackground(Background.fill(Paint.valueOf("#" + (int)Math.floor(Math.random() * 89 + 10) + "f3" + (int)Math.floor(Math.random() * 89 + 10))));
            schoolBoard.setLayoutX(0);
            if (player.getAndIncrement() > 0)
                schoolBoard.setLayoutX(GUI_WIDTH - SCHOOL_BOARD_WIDTH);
            schoolBoard.setLayoutY(0);
            for (int i = 0; i<playerUpdate.getEntranceStudents().size(); i++) {
                Circle pawn = new Circle(
                        (200 / 6) * Math.floor((i + 2) / 2),
                        (100 / 3) * (1 + (i + 1) % 2),
                        10,
                        PAWNS_COLORS_PAINT_ENUM_MAP.get(playerUpdate.getEntranceStudents().get(i))
                );
                schoolBoard.getChildren().add(pawn);
            }
            for (int i = 0; i<playerUpdate.getTowers(); i++) {
                Circle tower = new Circle(
                        (200 / 5) * Math.floor((i + 2) / 2),
                        SCHOOL_BOARD_HEIGHT - 100 + (100 / 3) * (1 + i % 2),
                        10,
                        TOWERS_COLORS_PAINT_ENUM_MAP.get(playerUpdate.getTowersColor())
                );
                schoolBoard.getChildren().add(tower);
            }
            this.PLAYERS_PANES.add(schoolBoard);
        });
        this.MAIN_PANE.getChildren().addAll(this.PLAYERS_PANES);
    }

    public void showGameUpdate(GameUpdate gameUpdate) {
        String message;
        if (GUI.getController().getUsername().equals(gameUpdate.getCurrentPlayer())) {
            message = "È il tuo turno";
        } else {
            message = "È il turno di " + gameUpdate.getCurrentPlayer();
        }
        this.showTurnMessage(message);
    }

    public void showTurnMessage(String message) {
        TURN_MESSAGE.setText(message);
        TURN_MESSAGE.setFont(Font.font("Berlin Sans FB", 30));
        TURN_MESSAGE.setMinWidth(GUI_WIDTH);
        TURN_MESSAGE.setAlignment(Pos.CENTER);
        TURN_MESSAGE.setLayoutY(GUI_HEIGHT - 100);
    }

    public void showGamePhaseMessage(String message) {
        GAME_PHASE_MESSAGE.setText(message);
        GAME_PHASE_MESSAGE.setFont(Font.font("Berlin Sans FB", 30));
        GAME_PHASE_MESSAGE.setMinWidth(GUI_WIDTH);
        GAME_PHASE_MESSAGE.setAlignment(Pos.CENTER);
        GAME_PHASE_MESSAGE.setLayoutY(GUI_HEIGHT - 75);
    }

    public void showAssistantCardsPane(ArrayList<AssistantCard> availableCards) {
        this.MAIN_PANE.getChildren().remove(this.BOARD_PANE);
        this.ASSISTANT_CARDS_PANE.setBackground(Background.fill(Paint.valueOf("#dedede")));
        this.ASSISTANT_CARDS_PANE.getChildren().clear();
        this.ASSISTANT_CARDS_PANE.setMaxWidth(BOARD_PANE_WIDTH);
        this.ASSISTANT_CARDS_PANE.setMinWidth(BOARD_PANE_WIDTH);
        this.ASSISTANT_CARDS_PANE.setMaxHeight(BOARD_PANE_HEIGHT);
        this.ASSISTANT_CARDS_PANE.setMinHeight(BOARD_PANE_HEIGHT);
        this.ASSISTANT_CARDS_PANE.setLayoutX(SCHOOL_BOARD_WIDTH);
        for (int i = 0; i<availableCards.size(); i++) {
            Rectangle cardShape = new Rectangle(
                    CARDS_MARGIN_X * (i % Math.ceil(availableCards.size() / 2) + 1) + (i % Math.ceil(availableCards.size() / 2)) * CARD_WIDTH,
                    CARDS_MARGIN_Y * (Math.floor((2 * i) / availableCards.size()) + 1) + Math.floor((2 * i) / availableCards.size()) * CARD_HEIGHT,
                    CARD_WIDTH,
                    CARD_HEIGHT
            );
            cardShape.setCursor(Cursor.HAND);
            final int temp_i = i;
            cardShape.setOnMouseClicked(mouseEvent -> {
                AssistantCard card = availableCards.get(temp_i);
                AssistantCardResponse cardResponse = new AssistantCardResponse(card);
                GUI.getController().sendObjectMessage(cardResponse);
            });
            cardShape.setFill(Paint.valueOf("#f42e1a"));
            this.ASSISTANT_CARDS_PANE.getChildren().add(cardShape);
        }
        this.MAIN_PANE.getChildren().add(this.ASSISTANT_CARDS_PANE);
    }
}
