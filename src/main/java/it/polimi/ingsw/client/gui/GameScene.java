package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.messages.clienttoserver.AssistantCardResponse;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.PawnsColors;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static it.polimi.ingsw.utils.Utils.*;

public class GameScene extends Scene {
    private final Pane BOARD_PANE, MAIN_PANE, ASSISTANT_CARDS_PANE;
    private final Label GAME_PHASE_MESSAGE, TURN_MESSAGE;
    private final ArrayList<TileRectangle> ISLANDS, CLOUDS;
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
        this.CLOUDS = new ArrayList<>();
        this.currentMNPosition = -1;
    }

    public void updateBoard(BoardUpdateContent boardUpdate) {
        this.MAIN_PANE.getChildren().remove(this.BOARD_PANE);
        this.MAIN_PANE.getChildren().remove(this.ASSISTANT_CARDS_PANE);
        this.MAIN_PANE.setBackground(Background.fill(Paint.valueOf("#69BAE9")));
        this.ISLANDS.clear();
        this.CLOUDS.clear();
        this.BOARD_PANE.setBackground(Background.fill(Paint.valueOf("#69BAE9")));
        this.BOARD_PANE.getChildren().clear();
        this.BOARD_PANE.setMaxWidth(BOARD_PANE_WIDTH);
        this.BOARD_PANE.setMinWidth(BOARD_PANE_WIDTH);
        this.BOARD_PANE.setMaxHeight(BOARD_PANE_HEIGHT);
        this.BOARD_PANE.setMinHeight(BOARD_PANE_HEIGHT);
        this.BOARD_PANE.setLayoutX(SCHOOL_BOARD_WIDTH);
        ArrayList<ArrayList<IslandUpdate>> islands = boardUpdate.getIslands();
        for (ArrayList<IslandUpdate> groupOfIslandsUpdate : islands) {
            double sumIndex = 0.0;
            boolean last = false, first = false;
            int firstHalf = 0;
            for (IslandUpdate islandUpdate : groupOfIslandsUpdate) {
                sumIndex += islandUpdate.getIndex();
                if (islandUpdate.getIndex() < 6) firstHalf++;
                if (islandUpdate.getIndex() == 11) last = true;
                if (islandUpdate.getIndex() == 0) first = true;
            }
            if (last && first) sumIndex += 12 * firstHalf;
            double theta = (Math.PI / 6) * ((sumIndex / (double) groupOfIslandsUpdate.size()) % 12.0);
            for (int j = 0; j < groupOfIslandsUpdate.size(); j++) {
                IslandUpdate island = groupOfIslandsUpdate.get(j);
                if (island.getIndex() == boardUpdate.getMotherNature())
                    this.currentMNPosition = island.getGroupOfIslands();
                double islandTheta = theta + (Math.PI / 15) * (j - (groupOfIslandsUpdate.size() - 1) / 2.0);
                if (islandTheta % (Math.PI / 2) > 0.1) {
                    if (Math.signum(Math.cos(islandTheta) * Math.sin(islandTheta)) < 0)
                        islandTheta += Math.PI / 40;
                    else
                        islandTheta -= Math.PI / 40;
                }
                TileRectangle islandShape = new TileRectangle(island.getIndex());
                double x = BOARD_PANE_WIDTH / 2 + ISLANDS_ELLIPSE_X_AXIS * Math.sin(islandTheta) - ISLAND_DIMENSION / 2,
                        y = BOARD_PANE_HEIGHT / 2 - ISLANDS_ELLIPSE_Y_AXIS * Math.cos(islandTheta) - ISLAND_DIMENSION / 2;
                islandShape.setX(x);
                islandShape.setY(y);
                islandShape.setWidth(ISLAND_DIMENSION);
                islandShape.setHeight(ISLAND_DIMENSION);
                islandShape.setFill(Paint.valueOf("#5dc092"));
                this.ISLANDS.add(islandShape);
                this.BOARD_PANE.getChildren().add(islandShape);
                ArrayList<PawnsColors> students = island.getStudents();
                for (PawnsColors student : students) {
                    Circle studentShape = new Circle(
                            x + (ISLAND_DIMENSION / 2),
                            y + (ISLAND_DIMENSION / 2),
                            ISLAND_DIMENSION / 6,
                            PAWNS_COLORS_PAINT_ENUM_MAP.get(student)
                    );
                    this.BOARD_PANE.getChildren().add(studentShape);
                }
                if (boardUpdate.getMotherNature() == island.getIndex()) {
                    Circle motherNatureShape = new Circle(
                            x + (ISLAND_DIMENSION / 2),
                            y + (ISLAND_DIMENSION / 2),
                            ISLAND_DIMENSION / 3,
                            Paint.valueOf("#6e3d0c")
                    );
                    this.BOARD_PANE.getChildren().add(motherNatureShape);
                }
                if (island.hasTower()) {
                    Circle towerShape = new Circle(
                            x + ISLAND_DIMENSION,
                            y,
                            PAWNS_RADIUS,
                            TOWERS_COLORS_PAINT_ENUM_MAP.get(island.getTowerColor())
                    );
                    this.BOARD_PANE.getChildren().add(towerShape);
                }
            }
        }
        ArrayList<CloudUpdate> cloudUpdates = boardUpdate.getClouds();
        final double CLOUDS_MARGIN = 20.0, CLOUDS_DIST_RADIUS = Math.sqrt(2) * (CLOUDS_MARGIN + CLOUD_DIMENSION) / 2;
        for (int i = 0; i < cloudUpdates.size(); i++) {
            double theta = (i * Math.PI / 2) + (Math.PI / 4);
            double x = CLOUD_DIMENSION + 3 * CLOUDS_MARGIN / 2 + CLOUDS_DIST_RADIUS * Math.cos(theta),
                    y = CLOUD_DIMENSION + 3 * CLOUDS_MARGIN / 2 - CLOUDS_DIST_RADIUS * Math.sin(theta);
            TileRectangle cloudShape = new TileRectangle(i);
            cloudShape.setX(x - CLOUD_DIMENSION / 2);
            cloudShape.setY(y - CLOUD_DIMENSION / 2);
            cloudShape.setWidth(CLOUD_DIMENSION);
            cloudShape.setHeight(CLOUD_DIMENSION);
            cloudShape.setFill(Color.WHITE);
            this.CLOUDS.add(cloudShape);
            this.BOARD_PANE.getChildren().add(cloudShape);
            ArrayList<PawnsColors> students = cloudUpdates.get(i).getStudents();
            for (PawnsColors student : students) {
                Circle studentShape = new Circle(
                        x,
                        y,
                        CLOUD_DIMENSION / 6,
                        PAWNS_COLORS_PAINT_ENUM_MAP.get(student)
                );
                this.BOARD_PANE.getChildren().add(studentShape);
            }
        }
        ArrayList<PawnsColors> availableProfessors = boardUpdate.getAvailableProfessors();
        final int professorsMargin = 15;
        for (int i = 0; i < availableProfessors.size(); i++) {
            PawnsColors professor = availableProfessors.get(i);
            double x = BOARD_PANE_WIDTH / 2 + (i - (availableProfessors.size() - 1) / 2.0) * (2 * PAWNS_RADIUS + professorsMargin),
                    y = BOARD_PANE_HEIGHT / 2;
            Rectangle pawn = new Rectangle(
                    x - PAWNS_RADIUS / 2,
                    y - PAWNS_RADIUS / 2,
                    2 * PAWNS_RADIUS,
                    2 * PAWNS_RADIUS
            );
            pawn.setFill(PAWNS_COLORS_PAINT_ENUM_MAP.get(professor));
            this.BOARD_PANE.getChildren().add(pawn);
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
            if (player.get() < 2) {
                schoolBoard.setMaxWidth(SCHOOL_BOARD_WIDTH);
                schoolBoard.setMinWidth(SCHOOL_BOARD_WIDTH);
                schoolBoard.setMaxHeight(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setMinHeight(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setLayoutX(GUI_WIDTH - SCHOOL_BOARD_WIDTH);
                if (player.get() < 1) {
                    schoolBoard.setLayoutX(0);
                }
                schoolBoard.setLayoutY(0);
            } else {
                schoolBoard.setMaxWidth(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setMinWidth(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setMaxHeight(SCHOOL_BOARD_WIDTH);
                schoolBoard.setMinHeight(SCHOOL_BOARD_WIDTH);
                schoolBoard.setLayoutX((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2);
                schoolBoard.setLayoutY(GUI_HEIGHT - SCHOOL_BOARD_WIDTH);
            }
            schoolBoard.setBackground(Background.fill(Paint.valueOf("#aaf342")));
            if (!this.ENTRANCE_PAWNS.containsKey(playerUpdate.getNickname()))
                this.ENTRANCE_PAWNS.put(playerUpdate.getNickname(), new ArrayList<>());
            else
                this.ENTRANCE_PAWNS.get(playerUpdate.getNickname()).clear();
            this.PLAYERS_PANES.put(playerUpdate.getNickname(), schoolBoard);
            for (int i = 0; i<playerUpdate.getEntranceStudents().size(); i++) {
                double x, y = (100 / 3) * (1 + (i + 1) % 2);
                if (player.get() < 2) {
                    x = (SCHOOL_BOARD_WIDTH / 6) * Math.floor((i + 2) / 2);
                } else {
                    x = y;
                    y = (SCHOOL_BOARD_WIDTH / 6) * Math.floor(6 - (i + 2) / 2);
                }
                this.ENTRANCE_PAWNS.get(playerUpdate.getNickname()).add(
                        new PawnCircle(
                                i,
                                x,
                                y,
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
                    double x, y = (diningRoomMarginY + 2 * PAWNS_RADIUS) * i + diningRoomMarginY - PAWNS_RADIUS;
                    if (player.get() < 2) {
                        x = (diningRoomMarginX + PAWNS_RADIUS) * PAWNS_COLORS_INTEGER_ENUM_MAP.get(color) + diningRoomMarginX;
                        y += 100;
                    } else {
                        x = y + 100;
                        y = (diningRoomMarginX + PAWNS_RADIUS) * (4 - PAWNS_COLORS_INTEGER_ENUM_MAP.get(color)) + diningRoomMarginX;
                    }
                    Circle pawn = new Circle(
                            x,
                            y,
                            PAWNS_RADIUS,
                            PAWNS_COLORS_PAINT_ENUM_MAP.get(color)
                    );
                    schoolBoard.getChildren().add(pawn);
                }
            });
            playerUpdate.getProfessors().forEach(professor -> {
                double x,
                        y = SCHOOL_BOARD_HEIGHT - 125;
                if (player.get() < 2) {
                    x = (diningRoomMarginX + PAWNS_RADIUS) * PAWNS_COLORS_INTEGER_ENUM_MAP.get(professor) + diningRoomMarginX;
                } else {
                    x = y;
                    y = (diningRoomMarginX + PAWNS_RADIUS) * (4 - PAWNS_COLORS_INTEGER_ENUM_MAP.get(professor)) + diningRoomMarginX;
                }
                Rectangle pawn = new Rectangle(
                        x - PAWNS_RADIUS / 2,
                        y - PAWNS_RADIUS / 2,
                        2 * PAWNS_RADIUS,
                        2 * PAWNS_RADIUS
                );
                pawn.setFill(PAWNS_COLORS_PAINT_ENUM_MAP.get(professor));
                schoolBoard.getChildren().add(pawn);
            });
            for (int i = 0; i<playerUpdate.getTowers(); i++) {
                double x, y = SCHOOL_BOARD_HEIGHT - 100 + (100 / 3) * (1 + i % 2);
                if (player.get() < 2) {
                    x = (200 / 5) * Math.floor((i + 2) / 2);
                } else {
                    x = y;
                    y = (200 / 5) * Math.floor(5 - (i + 2) / 2);
                }
                Circle tower = new Circle(
                        x,
                        y,
                        PAWNS_RADIUS,
                        TOWERS_COLORS_PAINT_ENUM_MAP.get(playerUpdate.getTowersColor())
                );
                schoolBoard.getChildren().add(tower);
            }
            player.getAndIncrement();
        });
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
        TURN_MESSAGE.setFont(Font.font("Berlin Sans FB", 20));
        TURN_MESSAGE.setMinWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2);
        TURN_MESSAGE.setMaxWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2);
        TURN_MESSAGE.setLayoutX(GUI_WIDTH - (GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2);
        TURN_MESSAGE.setAlignment(Pos.CENTER);
        TURN_MESSAGE.setTextAlignment(TextAlignment.CENTER);
        TURN_MESSAGE.setLayoutY(GUI_HEIGHT - 150);
        TURN_MESSAGE.setPadding(new Insets(0, 5.0, 0, 5.0));
    }

    public void showGamePhaseMessage(String message) {
        GAME_PHASE_MESSAGE.setWrapText(true);
        GAME_PHASE_MESSAGE.setText(message);
        GAME_PHASE_MESSAGE.setFont(Font.font("Berlin Sans FB", 20));
        GAME_PHASE_MESSAGE.setMinWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2);
        GAME_PHASE_MESSAGE.setMaxWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2);
        GAME_PHASE_MESSAGE.setLayoutX(GUI_WIDTH - (GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2);
        GAME_PHASE_MESSAGE.setAlignment(Pos.CENTER);
        GAME_PHASE_MESSAGE.setTextAlignment(TextAlignment.CENTER);
        GAME_PHASE_MESSAGE.setLayoutY(GUI_HEIGHT - 125);
        GAME_PHASE_MESSAGE.setPadding(new Insets(0, 5.0, 0, 5.0));
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
            double x = CARDS_MARGIN_X * (i % Math.ceil(availableCards.size() / 2.0) + 1) + (i % Math.ceil(availableCards.size() / 2.0)) * CARD_WIDTH,
                    y = CARDS_MARGIN_Y * (Math.floor((2 * i) / availableCards.size()) + 1) + Math.floor((2 * i) / availableCards.size()) * CARD_HEIGHT;
            Rectangle cardShape = new Rectangle(
                    x,
                    y,
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
            Label value = new Label(availableCards.get(i).getValue() + "");
            value.setLayoutX(x);
            value.setLayoutY(y + 20);
            value.setMinWidth(CARD_WIDTH);
            value.setMaxWidth(CARD_WIDTH);
            value.setFont(Font.font("Berilin Sans FB", 15));
            value.setTextAlignment(TextAlignment.CENTER);
            this.ASSISTANT_CARDS_PANE.getChildren().add(value);
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
        manager.setIslands(new ArrayList<>(this.ISLANDS.stream().map(TileRectangle::getIndex).toList()));
        this.ISLANDS.forEach(island -> {
            island.setCursor(Cursor.HAND);
            island.setOnMouseClicked(new MoveMNIslandHandler(island, manager));
        });
    }

    public void addChooseCloudHandler(ChooseCloudManager manager) {
        this.CLOUDS.forEach(cloud -> {
            cloud.setCursor(Cursor.HAND);
            cloud.setOnMouseClicked(new ChooseCloudHandler(cloud, manager));
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
        this.CLOUDS.forEach(cloud -> {
            cloud.setCursor(Cursor.DEFAULT);
            cloud.setOnMouseClicked(null);
        });
    }
}

class ActionPhaseIslandHandler implements EventHandler<MouseEvent> {
    private final TileRectangle island;
    private final MoveStudentsManager manager;

    public ActionPhaseIslandHandler(TileRectangle island, MoveStudentsManager manager) {
        this.island = island;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        this.manager.setIslandIndex(this.island.getIndex());
    }
}

class MoveMNIslandHandler implements  EventHandler<MouseEvent> {
    private final TileRectangle island;
    private final MoveMNManager manager;

    MoveMNIslandHandler(TileRectangle island, MoveMNManager manager) {
        this.island = island;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        this.manager.chooseIsland(this.island.getIndex());
    }
}

class ChooseCloudHandler implements  EventHandler<MouseEvent> {
    private final TileRectangle cloud;
    private final ChooseCloudManager manager;

    ChooseCloudHandler(TileRectangle cloud, ChooseCloudManager manager) {
        this.cloud = cloud;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        this.manager.chooseCloud(this.cloud.getIndex());
    }
}
