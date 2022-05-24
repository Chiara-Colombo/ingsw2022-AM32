package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.messages.clienttoserver.AssistantCardResponse;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.PawnsColors;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static it.polimi.ingsw.utils.Utils.*;

public class GameScene extends Scene {
    private final Pane BOARD_PANE, MAIN_PANE, ASSISTANT_CARDS_PANE;
    private final Label GAME_PHASE_MESSAGE, TURN_MESSAGE;
    private final ArrayList<IslandRectangle> ISLANDS;
    private final HashMap<String, Pane> PLAYERS_PANES;
    private final HashMap<String, ArrayList<PawnCircle>> ENTRANCE_PAWNS;
    private int currentMNPosition;

    public GameScene(Pane MAIN_PANE) {
        super(MAIN_PANE, GUI_WIDTH, GUI_HEIGHT);
        this.MAIN_PANE = MAIN_PANE;
        this.BOARD_PANE = new Pane();
        this.ASSISTANT_CARDS_PANE = new Pane();
        this.PLAYERS_PANES = new HashMap<>();
        this.GAME_PHASE_MESSAGE = new Label();
        this.TURN_MESSAGE = new Label();
        this.MAIN_PANE.getChildren().add(TURN_MESSAGE);
        this.MAIN_PANE.getChildren().add(GAME_PHASE_MESSAGE);
        this.ENTRANCE_PAWNS = new HashMap<>();
        this.ISLANDS = new ArrayList<>();
        this.currentMNPosition = -1;
    }

    public void updateBoard(BoardUpdateContent boardUpdate) {
        this.currentMNPosition = boardUpdate.getMotherNature();
        this.MAIN_PANE.getChildren().remove(this.BOARD_PANE);
        this.MAIN_PANE.getChildren().remove(this.ASSISTANT_CARDS_PANE);
        this.ISLANDS.clear();
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
            IslandRectangle islandShape = new IslandRectangle(i);
            double x = BOARD_PANE_WIDTH / 2 + RADIUS * Math.sin(theta) - DIM / 2, y = BOARD_PANE_HEIGHT / 2 + RADIUS * Math.cos(theta) - DIM / 2;
            islandShape.setX(x);
            islandShape.setY(y);
            islandShape.setWidth(DIM);
            islandShape.setHeight(DIM);
            islandShape.setFill(Paint.valueOf("#5dc092"));
            this.ISLANDS.add(islandShape);
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
            schoolBoard.setBackground(Background.fill(Paint.valueOf("#aaf342")));
            schoolBoard.setLayoutX(0);
            if (player.getAndIncrement() > 0)
                schoolBoard.setLayoutX(GUI_WIDTH - SCHOOL_BOARD_WIDTH);
            schoolBoard.setLayoutY(0);
            if (!this.ENTRANCE_PAWNS.containsKey(playerUpdate.getNickname()))
                this.ENTRANCE_PAWNS.put(playerUpdate.getNickname(), new ArrayList<>());
            else
                this.ENTRANCE_PAWNS.get(playerUpdate.getNickname()).clear();
            this.PLAYERS_PANES.put(playerUpdate.getNickname(), schoolBoard);
            for (int i = 0; i<playerUpdate.getEntranceStudents().size(); i++) {
                this.ENTRANCE_PAWNS.get(playerUpdate.getNickname()).add(
                        new PawnCircle(
                                i,
                                (200 / 6) * Math.floor((i + 2) / 2),
                                (100 / 3) * (1 + (i + 1) % 2),
                                PAWNS_RADIUS,
                                PAWNS_COLORS_PAINT_ENUM_MAP.get(playerUpdate.getEntranceStudents().get(i))
                        )
                );
            }
            schoolBoard.getChildren().addAll(this.ENTRANCE_PAWNS.get(playerUpdate.getNickname()));
            final int diningRoomMarginX = (SCHOOL_BOARD_WIDTH - 5 * PAWNS_RADIUS) / 6,
                    diningRoomMarginY = (SCHOOL_BOARD_HEIGHT - 2 * 100 - 50 - 10 * PAWNS_RADIUS) / 11;
            playerUpdate.getDiningRoom().forEach((color, quantity) -> {
                for (int i = 0; i<quantity; i++) {
                    int x = (diningRoomMarginX + PAWNS_RADIUS ) * PAWNS_COLORS_INTEGER_ENUM_MAP.get(color) + diningRoomMarginX,
                            y = (diningRoomMarginY + 2 * PAWNS_RADIUS) * i + diningRoomMarginY - PAWNS_RADIUS;
                    Circle pawn = new Circle(
                            x,
                            y + 100,
                            PAWNS_RADIUS,
                            PAWNS_COLORS_PAINT_ENUM_MAP.get(color)
                    );
                    schoolBoard.getChildren().add(pawn);
                }
            });
            for (int i = 0; i<playerUpdate.getTowers(); i++) {
                Circle tower = new Circle(
                        (200 / 5) * Math.floor((i + 2) / 2),
                        SCHOOL_BOARD_HEIGHT - 100 + (100 / 3) * (1 + i % 2),
                        PAWNS_RADIUS,
                        TOWERS_COLORS_PAINT_ENUM_MAP.get(playerUpdate.getTowersColor())
                );
                schoolBoard.getChildren().add(tower);
            }
        });
        //this.PLAYERS_PANES.forEach((key, value) -> this.MAIN_PANE.getChildren().add(value));
        this.MAIN_PANE.getChildren().addAll(this.PLAYERS_PANES.values());
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
        GAME_PHASE_MESSAGE.setWrapText(true);
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

    public void addMoveEntranceStudentsHandlers(MoveStudentsManager manager) {
        this.ENTRANCE_PAWNS.get(GUI.getController().getUsername()).forEach(circle -> {
            circle.setCursor(Cursor.HAND);
            circle.setOnMouseClicked(new ActionPhaseEntranceHandler(circle, manager));
        });
        this.PLAYERS_PANES.get(GUI.getController().getUsername()).setCursor(Cursor.HAND);
        this.PLAYERS_PANES.get(GUI.getController().getUsername()).setOnMouseClicked(new DiningRoomHandler(manager));
        this.ISLANDS.forEach(island -> {
            island.setCursor(Cursor.HAND);
            island.setOnMouseClicked(new ActionPhaseIslandHandler(island, manager));
        });
    }

    public void addMoveMNHandlers(MoveMNManager manager) {
        manager.setCurrentMNPosition(this.currentMNPosition);
        manager.setIslands(new ArrayList<>(this.ISLANDS.stream().map(IslandRectangle::getIndex).toList()));
        this.ISLANDS.forEach(island -> {
            island.setCursor(Cursor.HAND);
            island.setOnMouseClicked(new MoveMNIslandHandler(island, manager));
        });
    }

    public void removeHandlers() {
        this.ENTRANCE_PAWNS.get(GUI.getController().getUsername()).forEach(circle -> {
            circle.setCursor(Cursor.DEFAULT);
            circle.setOnMouseClicked(null);
        });
        this.PLAYERS_PANES.get(GUI.getController().getUsername()).setOnMouseClicked(null);
        this.PLAYERS_PANES.get(GUI.getController().getUsername()).setCursor(Cursor.DEFAULT);
        this.ISLANDS.forEach(island -> {
            island.setCursor(Cursor.DEFAULT);
            island.setOnMouseClicked(null);
        });
    }
}

class ActionPhaseIslandHandler implements EventHandler<MouseEvent> {
    private final IslandRectangle island;
    private final MoveStudentsManager manager;

    public ActionPhaseIslandHandler(IslandRectangle island, MoveStudentsManager manager) {
        this.island = island;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        this.manager.setIslandIndex(this.island.getIndex());
    }
}

class MoveMNIslandHandler implements  EventHandler<MouseEvent> {
    private final IslandRectangle island;
    private final MoveMNManager manager;

    MoveMNIslandHandler(IslandRectangle island, MoveMNManager manager) {
        this.island = island;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        this.manager.chooseIsland(this.island.getIndex());
    }
}
