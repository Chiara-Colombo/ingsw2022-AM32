package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.messages.clienttoserver.AssistantCardResponse;
import it.polimi.ingsw.model.AssistantCard;
import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.model.TowersColors;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

import static it.polimi.ingsw.utils.Utils.*;

@SuppressWarnings("SuspiciousNameCombination")
public class GameScene extends Scene {
    private final Pane BOARD_PANE, MAIN_PANE, ASSISTANT_CARDS_PANE;
    private final Label GAME_PHASE_MESSAGE, TURN_MESSAGE;
    private final ArrayList<TileImage> CLOUDS;
    private final HashMap<Integer, TileImage> ISLANDS;
    private final HashMap<String, Pane> PLAYERS_PANES;
    private final ImageView MOTHER_NATURE;
    private final HashMap<TowersColors, ArrayList<ImageView>> TOWERS;
    //private final ArrayList<ImageView> ASSISTANT_CARDS;
    private final HashMap<PawnsColors, ImageView> professors;
    private final HashMap<PawnsColors, ArrayList<ImageView>> students;
    private final HashMap<PawnsColors, Integer> studentsIndexes;
    private final HashMap<TowersColors, Integer> towersIndexes;
    private final HashMap<Integer, HashMap<PawnsColors, Integer>> studentsOnIslands;
    private final ArrayList<TileImage> entrance;
    private final EnumMap<Characters, CharacterCardImage> characterCards;
    private int currentMNPosition;
    private boolean firstBoardUpdate, firstPlayersUpdate, firstGameUpdate;

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
        this.ISLANDS = new HashMap<>();
        this.CLOUDS = new ArrayList<>();
        this.currentMNPosition = -1;
        this.students = new HashMap<>();
        this.studentsIndexes = new HashMap<>();
        this.towersIndexes = new HashMap<>();
        this.entrance = new ArrayList<>();
        this.professors = new HashMap<>();
        this.studentsOnIslands = new HashMap<>();
        this.characterCards = new EnumMap<>(Characters.class);
        this.MOTHER_NATURE = new ImageView(MOTHER_NATURE_IMAGE);
        this.TOWERS = new HashMap<>();
        //this.ASSISTANT_CARDS = new ArrayList<>(ASSISTANT_CARDS_IMAGES.stream().map(ImageView::new).toList());
        this.firstBoardUpdate = true;
        this.firstPlayersUpdate = true;
        this.firstGameUpdate = true;
    }

    private void loadBoardImages(BoardUpdateContent boardUpdate) {
        this.MAIN_PANE.setBackground(Background.fill(Paint.valueOf("#69BAE9")));
        this.BOARD_PANE.setBackground(Background.fill(Paint.valueOf("#69BAE9")));
        this.BOARD_PANE.setMaxWidth(BOARD_PANE_WIDTH);
        this.BOARD_PANE.setMinWidth(BOARD_PANE_WIDTH);
        this.BOARD_PANE.setMaxHeight(BOARD_PANE_HEIGHT);
        this.BOARD_PANE.setMinHeight(BOARD_PANE_HEIGHT);
        this.BOARD_PANE.setLayoutX(SCHOOL_BOARD_WIDTH);
        this.MOTHER_NATURE.setFitWidth(2 * PAWNS_RADIUS);
        this.MOTHER_NATURE.setPreserveRatio(true);
        for (PawnsColors professor : boardUpdate.getAvailableProfessors()) {
            this.professors.put(professor, new ImageView(PROFESSOR_COLORS_IMAGE_ENUM_MAP.get(professor)));
            this.students.put(professor, new ArrayList<>());
            this.studentsIndexes.put(professor, 0);
        }
        for (ArrayList<IslandUpdate> groupOfIslands : boardUpdate.getIslands()) {
            for (IslandUpdate island : groupOfIslands) {
                ImageView islandImage = new ImageView(ISLAND_IMAGE);
                islandImage.setFitWidth(ISLAND_DIMENSION);
                islandImage.setFitHeight(ISLAND_DIMENSION);
                this.ISLANDS.put(island.getIndex(), new TileImage(island.getIndex(), islandImage));
                this.studentsOnIslands.put(island.getIndex(), new HashMap<>());
                for (PawnsColors professor : boardUpdate.getAvailableProfessors()) {
                    this.studentsOnIslands.get(island.getIndex()).put(professor, 0);
                }
                //island.getStudents().forEach(student -> this.students.get(student).add(new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(student))));
            }
        }
        for (int i = 0; i < boardUpdate.getClouds().size(); i++) {
            ImageView cloudImage = new ImageView(CLOUD_IMAGE);
            cloudImage.setFitHeight(CLOUD_DIMENSION);
            cloudImage.setFitWidth(CLOUD_DIMENSION);
            this.CLOUDS.add(new TileImage(i, cloudImage));
            boardUpdate.getClouds().get(i).getStudents().forEach(student -> this.students.get(student).add(new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(student))));
        }
        this.firstBoardUpdate = false;
    }

    private void loadPlayerImages(ArrayList<PlayerUpdate> playersUpdate) {
        for (int i = 0; i < playersUpdate.size(); i++) {
            PlayerUpdate update = playersUpdate.get(i);
            this.TOWERS.put(update.getTowersColor(), new ArrayList<>());
            this.towersIndexes.put(update.getTowersColor(), 0);
            for (int j = 0; j < update.getTowers(); j++) {
                ImageView towerImage = new ImageView(TOWERS_COLORS_IMAGE_ENUM_MAP.get(update.getTowersColor()));
                towerImage.setFitHeight(2 * PAWNS_RADIUS);
                towerImage.setFitWidth(2 * PAWNS_RADIUS);
                this.TOWERS.get(update.getTowersColor()).add(towerImage);
            }
            Pane schoolBoard = new Pane();
            if (i < 2) {
                schoolBoard.setMaxWidth(SCHOOL_BOARD_WIDTH);
                schoolBoard.setMinWidth(SCHOOL_BOARD_WIDTH);
                schoolBoard.setMaxHeight(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setMinHeight(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setLayoutX(GUI_WIDTH - SCHOOL_BOARD_WIDTH);
                schoolBoard.setBackground(
                        new Background(
                                new BackgroundImage(
                                        SCHOOL_BOARD_ROTATED,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,
                                        new BackgroundSize(SCHOOL_BOARD_WIDTH, SCHOOL_BOARD_HEIGHT, false, false, true, true)
                                )
                        )
                );
                if (i < 1) {
                    schoolBoard.setLayoutX(0);
                }
                schoolBoard.setLayoutY(0);
            } else {
                schoolBoard.setMaxWidth(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setMinWidth(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setMaxHeight(SCHOOL_BOARD_WIDTH);
                schoolBoard.setMinHeight(SCHOOL_BOARD_WIDTH);
                schoolBoard.setLayoutX((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
                schoolBoard.setLayoutY(GUI_HEIGHT - SCHOOL_BOARD_WIDTH);
                schoolBoard.setBackground(
                        new Background(
                                new BackgroundImage(
                                        SCHOOL_BOARD,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundPosition.CENTER,
                                        new BackgroundSize(SCHOOL_BOARD_HEIGHT, SCHOOL_BOARD_WIDTH, false, false, true, true)
                                )
                        )
                );
            }
            this.PLAYERS_PANES.put(playersUpdate.get(i).getNickname(), schoolBoard);
        }
        this.firstPlayersUpdate = false;
    }

    private void loadCharactersImages(ArrayList<Characters> validCharacters) {
        for (Characters character : validCharacters) {
            this.characterCards.put(character, new CharacterCardImage(character, new ImageView(CHARACTERS_IMAGE_ENUM_MAP.get(character))));
        }
        this.firstGameUpdate = false;
    }

    public void updateBoard(BoardUpdateContent boardUpdate) {
        this.towersIndexes.forEach((key, value) -> this.towersIndexes.put(key, 0));
        this.studentsIndexes.forEach((key, value) -> this.studentsIndexes.put(key, 0));
        if (this.firstBoardUpdate)
            loadBoardImages(boardUpdate);
        this.MAIN_PANE.getChildren().remove(this.BOARD_PANE);
        this.MAIN_PANE.getChildren().remove(this.ASSISTANT_CARDS_PANE);
        this.MAIN_PANE.getChildren().removeAll(this.PLAYERS_PANES.values());
        this.BOARD_PANE.getChildren().clear();
        ArrayList<ArrayList<IslandUpdate>> islands = boardUpdate.getIslands();
        for (ArrayList<IslandUpdate> groupOfIslandsUpdate : islands) {
            double centerIndex = (groupOfIslandsUpdate.get((int) Math.floor(groupOfIslandsUpdate.size() / 2.0)).getIndex() - (.5 * (1 - groupOfIslandsUpdate.size() % 2))) % 12.0;
            double theta = (Math.PI / 6) * centerIndex;
            double centerX = BOARD_PANE_WIDTH / 2.0 + ISLANDS_ELLIPSE_X_AXIS * Math.sin(theta) - ISLAND_DIMENSION / 2.0,
                    centerY = BOARD_PANE_HEIGHT / 2.0 - ISLANDS_ELLIPSE_Y_AXIS * Math.cos(theta) - ISLAND_DIMENSION / 2.0;
            if ((centerIndex <= 1 || centerIndex == 11) && groupOfIslandsUpdate.size() > 2) centerY += ISLAND_DIMENSION / 2.0;
            for (int j = 0; j < groupOfIslandsUpdate.size(); j++) {
                IslandUpdate island = groupOfIslandsUpdate.get(j);
                if (island.getIndex() == boardUpdate.getMotherNature())
                    this.currentMNPosition = groupOfIslandsUpdate.get((int)Math.floor(groupOfIslandsUpdate.size() / 2.0)).getIndex();
                if (theta % (Math.PI / 2) > 0.1) {
                    if (Math.signum(Math.cos(theta) * Math.sin(theta)) < 0)
                        theta += Math.PI / 40;
                    else
                        theta -= Math.PI / 40;
                }
                double x = centerX + GROUP_OF_ISLANDS_OFSSETS[groupOfIslandsUpdate.size() - 1].get(j).get('x'),
                        y = centerY + GROUP_OF_ISLANDS_OFSSETS[groupOfIslandsUpdate.size() - 1].get(j).get('y');
                this.ISLANDS.get(island.getIndex()).getImageView().setLayoutX(x);
                this.ISLANDS.get(island.getIndex()).getImageView().setLayoutY(y);
                this.BOARD_PANE.getChildren().add(this.ISLANDS.get(island.getIndex()).getImageView());
                HashMap<PawnsColors, Integer> studentsOnIsland = this.studentsOnIslands.get(island.getIndex());
                studentsOnIsland.forEach((key, value) -> studentsOnIsland.put(key, 0));
                ArrayList<PawnsColors> students = island.getStudents();
                for (PawnsColors student : students) {
                    studentsOnIsland.put(student, studentsOnIsland.get(student) + 1);
                }
                final double PAWN_DIST_RADIUS = ISLAND_DIMENSION / 3.0;
                studentsOnIsland.forEach((color, quantity) -> {
                    if (quantity > 0) {
                        ImageView pawnImage;
                        if (this.studentsIndexes.get(color) >= this.students.get(color).size()) {
                            pawnImage = new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(color));
                            this.students.get(color).add(pawnImage);
                        } else {
                            pawnImage = this.students.get(color).get(this.studentsIndexes.get(color));
                        }
                        double pawnTheta = PAWNS_COLORS_INTEGER_ENUM_MAP.get(color) * (2 * Math.PI) / 5 + Math.PI / 2;
                        pawnImage.setLayoutX(x + ISLAND_DIMENSION / 2.0 + PAWN_DIST_RADIUS * Math.cos(pawnTheta) - PAWNS_RADIUS);
                        pawnImage.setLayoutY(y + ISLAND_DIMENSION / 2.0 - PAWN_DIST_RADIUS * Math.sin(pawnTheta) - PAWNS_RADIUS);
                        pawnImage.setFitHeight(2 * PAWNS_RADIUS);
                        pawnImage.setFitWidth(2 * PAWNS_RADIUS);
                        this.BOARD_PANE.getChildren().add(this.students.get(color).get(this.studentsIndexes.get(color)));
                        this.studentsIndexes.put(color, this.studentsIndexes.get(color) + 1);
                        if (quantity > 1) {
                            Label qtyLabel = new Label(quantity + "");
                            qtyLabel.setLayoutX(x + ISLAND_DIMENSION / 2.0 + PAWN_DIST_RADIUS * Math.cos(pawnTheta) + PAWNS_RADIUS);
                            qtyLabel.setLayoutY(y + ISLAND_DIMENSION / 2.0 - PAWN_DIST_RADIUS * Math.sin(pawnTheta));
                            qtyLabel.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, 16));
                            this.BOARD_PANE.getChildren().add(qtyLabel);
                        }
                    }
                });
                if (boardUpdate.getMotherNature() == island.getIndex()) {
                    this.MOTHER_NATURE.setLayoutX(x + (ISLAND_DIMENSION / 5.0));
                    this.MOTHER_NATURE.setLayoutY(y);
                    this.BOARD_PANE.getChildren().add(this.MOTHER_NATURE);
                }
                if (island.hasTower()) {
                    ImageView towerImage = this.TOWERS.get(island.getTowerColor()).get(this.towersIndexes.get(island.getTowerColor()));
                    towerImage.setLayoutY(y + ISLAND_DIMENSION / 2.0 - PAWNS_RADIUS);
                    towerImage.setLayoutX(x + ISLAND_DIMENSION / 2.0 - PAWNS_RADIUS);
                    this.BOARD_PANE.getChildren().add(towerImage);
                    this.towersIndexes.put(island.getTowerColor(), this.towersIndexes.get(island.getTowerColor()) + 1);
                }
            }
        }
        ArrayList<CloudUpdate> cloudUpdates = boardUpdate.getClouds();
        final double CLOUDS_MARGIN = 15.0, CLOUDS_DIST_RADIUS = Math.sqrt(2) * (CLOUDS_MARGIN + CLOUD_DIMENSION) / 2;
        for (int i = 0; i < cloudUpdates.size(); i++) {
            double theta = (i * Math.PI / 2) + (Math.PI / 4);
            double x = CLOUD_DIMENSION + 3 * CLOUDS_MARGIN / 2 + CLOUDS_DIST_RADIUS * Math.cos(theta),
                    y = CLOUD_DIMENSION + 3 * CLOUDS_MARGIN / 2 - CLOUDS_DIST_RADIUS * Math.sin(theta);
            this.CLOUDS.get(i).getImageView().setLayoutX(x - CLOUD_DIMENSION / 2.0);
            this.CLOUDS.get(i).getImageView().setLayoutY(y - CLOUD_DIMENSION / 2.0);
            this.BOARD_PANE.getChildren().add(this.CLOUDS.get(i).getImageView());
            ArrayList<PawnsColors> students = cloudUpdates.get(i).getStudents();
            final double STUDENTS_DIST_RADIUS = CLOUD_DIMENSION / (Math.sqrt(2.0) * 3);
            for (int j = 0; j < students.size(); j++) {
                PawnsColors student = students.get(j);
                double studentTheta = (j * Math.PI / 2) + (Math.PI / 4);
                ImageView pawnImage;
                if (this.studentsIndexes.get(student) >= this.students.get(student).size()) {
                    pawnImage = new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(student));
                    this.students.get(student).add(pawnImage);
                } else {
                    pawnImage = this.students.get(student).get(this.studentsIndexes.get(student));
                }
                pawnImage.setFitHeight(1.6 * PAWNS_RADIUS);
                pawnImage.setFitWidth(1.6 * PAWNS_RADIUS);
                pawnImage.setLayoutX(x + Math.cos(studentTheta) * STUDENTS_DIST_RADIUS - .8 * PAWNS_RADIUS);
                pawnImage.setLayoutY(y - Math.sin(studentTheta) * STUDENTS_DIST_RADIUS - .8 * PAWNS_RADIUS);
                this.BOARD_PANE.getChildren().add(this.students.get(student).get(this.studentsIndexes.get(student)));
                this.studentsIndexes.put(student, this.studentsIndexes.get(student) + 1);
            }
        }
        ArrayList<PawnsColors> availableProfessors = boardUpdate.getAvailableProfessors();
        final int professorsMargin = 15;
        for (int i = 0; i < availableProfessors.size(); i++) {
            PawnsColors professor = availableProfessors.get(i);
            double x = BOARD_PANE_WIDTH / 2.0 + (i - (availableProfessors.size() - 1) / 2.0) * (2 * PAWNS_RADIUS + professorsMargin),
                    y = BOARD_PANE_HEIGHT / 2.0;
            ImageView professorImage = this.professors.get(professor);
            professorImage.setLayoutY(y - PAWNS_RADIUS / 2.0);
            professorImage.setLayoutX(x - PAWNS_RADIUS / 2.0);
            professorImage.setFitWidth(3 * PAWNS_RADIUS);
            professorImage.setPreserveRatio(true);
            this.BOARD_PANE.getChildren().add(professorImage);
        }
        this.MAIN_PANE.getChildren().add(this.BOARD_PANE);
    }

    public void showBoard() {
        this.MAIN_PANE.getChildren().remove(this.ASSISTANT_CARDS_PANE);
    }

    void updatePlayers(ArrayList<PlayerUpdate> playersUpdate, boolean isExpertMode) {
        if (this.firstPlayersUpdate)
            loadPlayerImages(playersUpdate);
        for (int player = 0; player < playersUpdate.size(); player++) {
            PlayerUpdate playerUpdate = playersUpdate.get(player);
            Pane schoolBoard = this.PLAYERS_PANES.get(playerUpdate.getNickname());
            schoolBoard.getChildren().clear();
            if (GUI.getController().getUsername().equals(playerUpdate.getNickname()))
                this.entrance.clear();
            for (int i = 0; i < playerUpdate.getEntranceStudents().size(); i++) {
                double x = ENTRANCE_X_MARGIN + Math.floor(i / 2.0) * PAWNS_RADIUS * 2 + Math.floor(i / 2.0) * ENTRANCE_X_SPACE,
                        y = (27.0 - PAWNS_RADIUS) + (1 - (i % 2)) * 31.0;
                if (player >= 2) {
                    double tmp_x = x;
                    x = y;
                    y = SCHOOL_BOARD_WIDTH - tmp_x;
                }
                PawnsColors color = playerUpdate.getEntranceStudents().get(i);
                ImageView pawnImage;
                if (this.studentsIndexes.get(color) >= this.students.get(color).size()) {
                    pawnImage = new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(color));
                    this.students.get(color).add(pawnImage);
                } else {
                    pawnImage = this.students.get(color).get(this.studentsIndexes.get(color));
                }
                pawnImage.setLayoutY(y);
                pawnImage.setLayoutX(x);
                pawnImage.setFitWidth(2 * PAWNS_RADIUS);
                pawnImage.setFitHeight(2 * PAWNS_RADIUS);
                if (GUI.getController().getUsername().equals(playerUpdate.getNickname()))
                    this.entrance.add(new TileImage(i, pawnImage));
                schoolBoard.getChildren().add(pawnImage);
                this.studentsIndexes.put(color, this.studentsIndexes.get(color) + 1);
            }
            int finalPlayer = player;
            playerUpdate.getDiningRoom().forEach((color, quantity) -> {
                for (int i = 0; i<quantity; i++) {
                    double x = (ENTRANCE_X_SPACE + 2 * PAWNS_RADIUS) * PAWNS_COLORS_INTEGER_ENUM_MAP.get(color) + ENTRANCE_X_MARGIN,
                            y = 99.0 - PAWNS_RADIUS / 2.0 + i * (3.7 + 2 * PAWNS_RADIUS);
                    if (finalPlayer >= 2) {
                        double tmp_x = x;
                        x = y;
                        y = SCHOOL_BOARD_WIDTH - tmp_x;
                    }
                    ImageView pawnImage;
                    if (this.studentsIndexes.get(color) >= this.students.get(color).size()) {
                        pawnImage = new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(color));
                        this.students.get(color).add(pawnImage);
                    } else {
                        pawnImage = this.students.get(color).get(this.studentsIndexes.get(color));
                    }
                    pawnImage.setFitWidth(2 * PAWNS_RADIUS);
                    pawnImage.setFitHeight(2 * PAWNS_RADIUS);
                    pawnImage.setLayoutY(y);
                    pawnImage.setLayoutX(x);
                    schoolBoard.getChildren().add(pawnImage);
                    this.studentsIndexes.put(color, this.studentsIndexes.get(color) + 1);
                }
            });
            playerUpdate.getProfessors().forEach(professor -> {
                double x = (ENTRANCE_X_SPACE + 2 * PAWNS_RADIUS) * PAWNS_COLORS_INTEGER_ENUM_MAP.get(professor) + 30.0 - PAWNS_RADIUS / 2.0,
                        y = SCHOOL_BOARD_HEIGHT - 144.5;
                if (finalPlayer >= 2) {
                    double tmp_x = x;
                    x = y;
                    y = SCHOOL_BOARD_WIDTH - tmp_x - 5.0;
                }
                ImageView professorImage = this.professors.get(professor);
                professorImage.setFitWidth(2.4 * PAWNS_RADIUS);
                professorImage.setPreserveRatio(true);
                professorImage.setLayoutY(y);
                professorImage.setLayoutX(x);
                schoolBoard.getChildren().add(professorImage);
            });
            for (int i = 0; i<playerUpdate.getTowers(); i++) {
                double x = TOWERS_X_MARGIN + Math.floor(i / 2.0) * (9.0 + 2 * PAWNS_RADIUS), y = SCHOOL_BOARD_HEIGHT - 115.0 + (100 / 3.0) * (1 + i % 2);
                if (finalPlayer >= 2) {
                    double tmp_x = x;
                    x = y;
                    y = SCHOOL_BOARD_WIDTH - tmp_x;
                }
                ImageView towerImage = this.TOWERS.get(playerUpdate.getTowersColor()).get(this.towersIndexes.get(playerUpdate.getTowersColor()));
                towerImage.setLayoutY(y);
                towerImage.setLayoutX(x);
                schoolBoard.getChildren().add(towerImage);
                this.towersIndexes.put(playerUpdate.getTowersColor(), this.towersIndexes.get(playerUpdate.getTowersColor()) + 1);
            }
            if (isExpertMode) {
                ImageView coinImage = new ImageView(COIN_IMAGE);
                coinImage.setFitWidth(3.2 * PAWNS_RADIUS);
                coinImage.setPreserveRatio(true);
                double x = SCHOOL_BOARD_WIDTH - 1.6 * PAWNS_RADIUS, y = SCHOOL_BOARD_HEIGHT - 115.0;
                if (player > 1) {
                    x = y;
                    y = 0.0;
                } else if (player > 0) {
                    x = 0.0 - 1.6 * PAWNS_RADIUS;
                }
                coinImage.setLayoutX(x);
                coinImage.setLayoutY(y);
                Label qty = new Label(playerUpdate.getCoins() + "");
                qty.setLayoutX(x + 3 * PAWNS_RADIUS);
                qty.setLayoutY(y + 1.6 * PAWNS_RADIUS);
                qty.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, 16));
                schoolBoard.getChildren().addAll(coinImage, qty);
            }
        }
        this.MAIN_PANE.getChildren().addAll(this.PLAYERS_PANES.values());
    }

    public void updateGame(GameUpdate gameUpdate) {
        if (gameUpdate.isExpertMode()) {
            if (this.firstGameUpdate) loadCharactersImages(gameUpdate.getValidCharacters());
            ArrayList<Characters> validCharacters = gameUpdate.getValidCharacters();
            final double CARDS_MARGIN_Y = 20.0,
                    CARDS_HEIGHT = SCHOOL_BOARD_WIDTH - 2 * CARDS_MARGIN_Y,
                    CARDS_MARGIN_X = 20.0, CARDS_SPACE_X = 10.0,
                    CARDS_WIDTH = (((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0) - 2 * CARDS_MARGIN_X - 2 * CARDS_SPACE_X) / 3.0;
            for (int i = 0; i < validCharacters.size(); i++) {
                ImageView cardImage = this.characterCards.get(validCharacters.get(i)).getImageView();
                cardImage.setFitHeight(CARDS_HEIGHT);
                cardImage.setFitWidth(CARDS_WIDTH);
                cardImage.setLayoutY(GUI_HEIGHT - CARDS_HEIGHT - CARDS_MARGIN_Y);
                cardImage.setLayoutX(CARDS_MARGIN_X + (CARDS_SPACE_X + CARDS_WIDTH) * i);
                this.MAIN_PANE.getChildren().add(cardImage);
            }
        }
        String message;
        if (GUI.getController().getUsername().equals(gameUpdate.getCurrentPlayer())) {
            message = "È il tuo turno";
        } else {
            message = "È il turno di " + gameUpdate.getCurrentPlayer();
        }
        this.showTurnMessage(message);
    }

    public void showTurnMessage(String message) {
        this.TURN_MESSAGE.setText(message);
        this.TURN_MESSAGE.setFont(Font.font("Berlin Sans FB", 20));
        this.TURN_MESSAGE.setMinWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.TURN_MESSAGE.setMaxWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.TURN_MESSAGE.setLayoutX(GUI_WIDTH - (GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.TURN_MESSAGE.setAlignment(Pos.CENTER);
        this.TURN_MESSAGE.setTextAlignment(TextAlignment.CENTER);
        this.TURN_MESSAGE.setLayoutY(GUI_HEIGHT - 150);
        this.TURN_MESSAGE.setPadding(new Insets(0, 5.0, 0, 5.0));
    }

    public void showGamePhaseMessage(String message) {
        this.GAME_PHASE_MESSAGE.setWrapText(true);
        this.GAME_PHASE_MESSAGE.setText(message);
        this.GAME_PHASE_MESSAGE.setFont(Font.font("Berlin Sans FB", 20));
        this.GAME_PHASE_MESSAGE.setMinWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.GAME_PHASE_MESSAGE.setMaxWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.GAME_PHASE_MESSAGE.setLayoutX(GUI_WIDTH - (GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.GAME_PHASE_MESSAGE.setAlignment(Pos.CENTER);
        this.GAME_PHASE_MESSAGE.setTextAlignment(TextAlignment.CENTER);
        this.GAME_PHASE_MESSAGE.setLayoutY(GUI_HEIGHT - 125);
        this.GAME_PHASE_MESSAGE.setPadding(new Insets(0, 5.0, 0, 5.0));
    }

    public void showAssistantCardsPane(ArrayList<AssistantCard> availableCards) {
        this.ASSISTANT_CARDS_PANE.setBackground(Background.fill(new Color(0.05, 0.05, 0.05, 0.8)));
        this.ASSISTANT_CARDS_PANE.getChildren().clear();
        this.ASSISTANT_CARDS_PANE.setMaxWidth(BOARD_PANE_WIDTH);
        this.ASSISTANT_CARDS_PANE.setMinWidth(BOARD_PANE_WIDTH);
        this.ASSISTANT_CARDS_PANE.setMaxHeight(BOARD_PANE_HEIGHT);
        this.ASSISTANT_CARDS_PANE.setMinHeight(BOARD_PANE_HEIGHT);
        this.ASSISTANT_CARDS_PANE.setLayoutX(SCHOOL_BOARD_WIDTH);
        for (int i = 0; i<availableCards.size(); i++) {
            double x = CARDS_MARGIN_X * (i % Math.ceil(availableCards.size() / 2.0) + 1) + (i % Math.ceil(availableCards.size() / 2.0)) * CARD_WIDTH,
                    y = CARDS_MARGIN_Y * (Math.floor((2.0 * i) / availableCards.size()) + 1) + Math.floor((2.0 * i) / availableCards.size()) * CARD_HEIGHT;
            ImageView cardImage = new ImageView(ASSISTANT_CARDS_IMAGES.get(availableCards.get(i).getValue() - 1));
            cardImage.setLayoutX(x);
            cardImage.setLayoutY(y);
            cardImage.setFitWidth(CARD_WIDTH);
            cardImage.setFitHeight(CARD_HEIGHT);
            cardImage.setCursor(Cursor.HAND);
            final int temp_i = i;
            cardImage.setOnMouseClicked(mouseEvent -> {
                AssistantCard card = availableCards.get(temp_i);
                AssistantCardResponse cardResponse = new AssistantCardResponse(card);
                GUI.getController().sendObjectMessage(cardResponse);
            });
            this.ASSISTANT_CARDS_PANE.getChildren().add(cardImage);
        }
        this.MAIN_PANE.getChildren().add(this.ASSISTANT_CARDS_PANE);
    }

    public void addMoveEntranceStudentsHandlers(MoveStudentsManager manager) {
        this.entrance.forEach(pawnImage -> {
            pawnImage.getImageView().setCursor(Cursor.HAND);
            pawnImage.getImageView().setOnMouseClicked(new ActionPhaseEntranceHandler(pawnImage, manager));
        });
        this.PLAYERS_PANES.get(GUI.getController().getUsername()).setCursor(Cursor.HAND);
        this.PLAYERS_PANES.get(GUI.getController().getUsername()).setOnMouseClicked(new DiningRoomHandler(manager));
        this.ISLANDS.forEach((index, island) -> {
            island.getImageView().setCursor(Cursor.HAND);
            island.getImageView().setOnMouseClicked(new ActionPhaseIslandHandler(island, manager));
        });
    }

    public void addMoveMNHandlers(MoveMNManager manager) {
        manager.setCurrentMNPosition(this.currentMNPosition);
        this.ISLANDS.forEach((index, island) -> {
            if (manager.getValidIslands().contains(index)) {
                island.getImageView().setCursor(Cursor.HAND);
                island.getImageView().setOnMouseClicked(new MoveMNIslandHandler(island, manager));
            }
        });
    }

    public void addChooseCloudHandler(ChooseCloudManager manager) {
        this.CLOUDS.forEach(cloud -> {
            cloud.getImageView().setCursor(Cursor.HAND);
            cloud.getImageView().setOnMouseClicked(new ChooseCloudHandler(cloud, manager));
        });
    }

    public void removeHandlers() {
        this.entrance.forEach(pawnImage -> {
            pawnImage.getImageView().setCursor(Cursor.DEFAULT);
            pawnImage.getImageView().setOnMouseClicked(null);
        });
        this.PLAYERS_PANES.get(GUI.getController().getUsername()).setOnMouseClicked(null);
        this.PLAYERS_PANES.get(GUI.getController().getUsername()).setCursor(Cursor.DEFAULT);
        this.ISLANDS.forEach((index, island) -> {
            island.getImageView().setCursor(Cursor.DEFAULT);
            island.getImageView().setOnMouseClicked(null);
        });
        this.CLOUDS.forEach(cloud -> {
            cloud.getImageView().setCursor(Cursor.DEFAULT);
            cloud.getImageView().setOnMouseClicked(null);
        });
    }
}

class ActionPhaseIslandHandler implements EventHandler<MouseEvent> {
    private final TileImage island;
    private final MoveStudentsManager manager;

    public ActionPhaseIslandHandler(TileImage island, MoveStudentsManager manager) {
        this.island = island;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        this.manager.setIslandIndex(this.island.getIndex());
    }
}

class MoveMNIslandHandler implements  EventHandler<MouseEvent> {
    private final TileImage island;
    private final MoveMNManager manager;

    MoveMNIslandHandler(TileImage island, MoveMNManager manager) {
        this.island = island;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        this.manager.chooseIsland(this.island.getIndex());
    }
}

class ChooseCloudHandler implements  EventHandler<MouseEvent> {
    private final TileImage cloud;
    private final ChooseCloudManager manager;

    ChooseCloudHandler(TileImage cloud, ChooseCloudManager manager) {
        this.cloud = cloud;
        this.manager = manager;
    }

    @Override
    public void handle(MouseEvent event) {
        this.manager.chooseCloud(this.cloud.getIndex());
    }
}
