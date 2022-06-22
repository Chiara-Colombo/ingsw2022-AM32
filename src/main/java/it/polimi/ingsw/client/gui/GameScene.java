package it.polimi.ingsw.client.gui;

import it.polimi.ingsw.client.*;
import it.polimi.ingsw.messages.clienttoserver.*;
import it.polimi.ingsw.messages.servertoclient.SelectColorRequest;
import it.polimi.ingsw.messages.servertoclient.SelectIslandRequest;
import it.polimi.ingsw.messages.servertoclient.SelectPawnRequest;
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
    private final Pane BOARD_PANE, MAIN_PANE, ASSISTANT_CARDS_PANE, CHARACTER_PARAMS_PANE;
    private final Label GAME_PHASE_MESSAGE, TURN_MESSAGE, CHARACTER_USED_MESSAGE;
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
    private final ArrayList<TileImage> entrance, characterPawnsList;
    private final ArrayList<ColorImage> characterColorsList;
    private final EnumMap<Characters, CharacterCardImage> characterCards, characterParams;
    private final ArrayList<ImageView> coins, noEntryTiles;
    private ClientController controller;
    private int currentMNPosition, coinsIndex, noEntryTilesIndex;
    private boolean firstBoardUpdate, firstPlayersUpdate, firstGameUpdate;

    public GameScene(Pane MAIN_PANE) {
        super(MAIN_PANE, GUI_WIDTH, GUI_HEIGHT);
        this.MAIN_PANE = MAIN_PANE;
        this.BOARD_PANE = new Pane();
        this.ASSISTANT_CARDS_PANE = new Pane();
        this.CHARACTER_PARAMS_PANE = new Pane();
        this.PLAYERS_PANES = new HashMap<>();
        this.GAME_PHASE_MESSAGE = new Label();
        this.TURN_MESSAGE = new Label();
        this.CHARACTER_USED_MESSAGE = new Label();
        this.MAIN_PANE.getChildren().add(TURN_MESSAGE);
        this.MAIN_PANE.getChildren().add(GAME_PHASE_MESSAGE);
        this.ISLANDS = new HashMap<>();
        this.CLOUDS = new ArrayList<>();
        this.currentMNPosition = -1;
        this.students = new HashMap<>();
        this.studentsIndexes = new HashMap<>();
        this.towersIndexes = new HashMap<>();
        this.entrance = new ArrayList<>();
        this.characterPawnsList = new ArrayList<>();
        this.characterColorsList = new ArrayList<>();
        this.professors = new HashMap<>();
        this.studentsOnIslands = new HashMap<>();
        this.characterCards = new EnumMap<>(Characters.class);
        this.characterParams = new EnumMap<>(Characters.class);
        this.coins = new ArrayList<>();
        this.noEntryTiles = new ArrayList<>();
        this.MOTHER_NATURE = new ImageView(MOTHER_NATURE_IMAGE);
        this.TOWERS = new HashMap<>();
        this.coinsIndex = 0;
        this.noEntryTilesIndex = 0;
        this.firstBoardUpdate = true;
        this.firstPlayersUpdate = true;
        this.firstGameUpdate = true;
    }

    void setController(ClientController controller) {
        this.controller = controller;
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
            }
        }
        for (int i = 0; i < boardUpdate.getClouds().size(); i++) {
            ImageView cloudImage = new ImageView(CLOUD_IMAGE);
            cloudImage.setFitHeight(CLOUD_DIMENSION);
            cloudImage.setFitWidth(CLOUD_DIMENSION);
            this.CLOUDS.add(new TileImage(i, cloudImage));
            boardUpdate.getClouds().get(i).getStudents().forEach(student -> this.students.get(student).add(new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(student))));
        }
        this.coins.add(new ImageView(COIN_IMAGE));
        this.firstBoardUpdate = false;
    }

    private void loadPlayerImages(ArrayList<PlayerUpdate> playersUpdate) {
        int index = 0;
        for (PlayerUpdate update : playersUpdate) {
            this.TOWERS.put(update.getTowersColor(), new ArrayList<>());
            this.towersIndexes.put(update.getTowersColor(), 0);
            for (int j = 0; j < update.getTowers(); j++) {
                ImageView towerImage = new ImageView(TOWERS_COLORS_IMAGE_ENUM_MAP.get(update.getTowersColor()));
                towerImage.setFitHeight(2 * PAWNS_RADIUS);
                towerImage.setFitWidth(2 * PAWNS_RADIUS);
                this.TOWERS.get(update.getTowersColor()).add(towerImage);
            }
            Pane schoolBoard = new Pane();
            if (this.controller.getUsername().equals(update.getNickname())) {
                schoolBoard.setMaxWidth(SCHOOL_BOARD_WIDTH);
                schoolBoard.setMinWidth(SCHOOL_BOARD_WIDTH);
                schoolBoard.setMaxHeight(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setMinHeight(SCHOOL_BOARD_HEIGHT);
                schoolBoard.setLayoutX(0.0);
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
                schoolBoard.setLayoutY(0.0);
            } else {
                if (index < 1) {
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
                index++;
            }
            this.coins.add(new ImageView(COIN_IMAGE));
            this.PLAYERS_PANES.put(update.getNickname(), schoolBoard);
        }
        this.firstPlayersUpdate = false;
    }

    private void loadCharactersImages(GameUpdate gameUpdate) {
        ArrayList<Characters> validCharacters = gameUpdate.getValidCharacters();
        validCharacters
                .stream()
                .filter(character -> character.equals(Characters.SPOILED_PRINCESS) || character.equals(Characters.MONK) || character.equals(Characters.MUSHROOMS_MAN))
                .forEach(character -> this.characterParams.put(character, new CharacterCardImage(character, new ImageView(EMPTY_CHARACTERS_IMAGE_ENUM_MAP.get(character)))));
        for (Characters character : validCharacters) {
            this.characterCards.put(character, new CharacterCardImage(character, new ImageView(CHARACTERS_IMAGE_ENUM_MAP.get(character))));
            if (character.equals(Characters.GRANDMA_HERBS)) {
                for (int i = 0; i < gameUpdate.getGrandmaHerbsNoEntryTiles(); i++) {
                    this.noEntryTiles.add(new ImageView(NO_ENTRY_TILE_IMAGE));
                }
            }
        }
        this.firstGameUpdate = false;
    }

    void updateBoard(BoardUpdateContent boardUpdate, boolean isExpertMode) {
        this.towersIndexes.forEach((key, value) -> this.towersIndexes.put(key, 0));
        this.studentsIndexes.forEach((key, value) -> this.studentsIndexes.put(key, 0));
        this.coinsIndex = 0;
        this.noEntryTilesIndex = 0;
        if (this.firstBoardUpdate)
            loadBoardImages(boardUpdate);
        this.MAIN_PANE.getChildren().clear();
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
                if (isExpertMode) {
                    if (island.isNoEntry()) {
                        ImageView noEntryImage = this.noEntryTiles.get(this.noEntryTilesIndex);
                        noEntryImage.setFitWidth(50.0);
                        noEntryImage.setFitHeight(50.0);
                        noEntryImage.setLayoutX(x + (ISLAND_DIMENSION - 50.0) / 2.0);
                        noEntryImage.setLayoutY(y + (ISLAND_DIMENSION - 50.0) / 2.0);
                        this.noEntryTilesIndex++;
                        this.BOARD_PANE.getChildren().add(noEntryImage);
                    }
                }
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
        if (isExpertMode) {
            ImageView coinImage = this.coins.get(this.coinsIndex);
            coinImage.setFitWidth(6 * PAWNS_RADIUS);
            coinImage.setPreserveRatio(true);
            coinImage.setLayoutX(CLOUD_DIMENSION / 2.0);
            coinImage.setLayoutY(SCHOOL_BOARD_HEIGHT - 100.0);
            Label qty = new Label(boardUpdate.getCoinsSupply() + "");
            qty.setLayoutX(CLOUD_DIMENSION / 2.0 + 5.2 * PAWNS_RADIUS);
            qty.setLayoutY(SCHOOL_BOARD_HEIGHT - 100.0 + 5.2 * PAWNS_RADIUS);
            qty.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, 16));
            this.coinsIndex++;
            this.BOARD_PANE.getChildren().addAll(coinImage, qty);
        }
        this.MAIN_PANE.getChildren().add(this.BOARD_PANE);
    }

    void showBoard() {
        this.MAIN_PANE.getChildren().remove(this.ASSISTANT_CARDS_PANE);
    }

    void updatePlayers(ArrayList<PlayerUpdate> playersUpdate, boolean isExpertMode) {
        if (this.firstPlayersUpdate)
            loadPlayerImages(playersUpdate);
        int index = 0;
        for (PlayerUpdate playerUpdate : playersUpdate) {
            Pane schoolBoard = this.PLAYERS_PANES.get(playerUpdate.getNickname());
            schoolBoard.getChildren().clear();
            if (this.controller.getUsername().equals(playerUpdate.getNickname()))
                this.entrance.clear();
            for (int i = 0; i < playerUpdate.getEntranceStudents().size(); i++) {
                double x = ENTRANCE_X_MARGIN + Math.floor(i / 2.0) * PAWNS_RADIUS * 2 + Math.floor(i / 2.0) * ENTRANCE_X_SPACE,
                        y = (27.0 - PAWNS_RADIUS) + (1 - (i % 2)) * 31.0;
                if (!playerUpdate.getNickname().equals(this.controller.getUsername()) && index >= 1) {
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
                if (this.controller.getUsername().equals(playerUpdate.getNickname()))
                    this.entrance.add(new TileImage(i, pawnImage));
                schoolBoard.getChildren().add(pawnImage);
                this.studentsIndexes.put(color, this.studentsIndexes.get(color) + 1);
            }
            int finalIndex = index;
            playerUpdate.getDiningRoom().forEach((color, quantity) -> {
                for (int i = 0; i<quantity; i++) {
                    double x = (ENTRANCE_X_SPACE + 2 * PAWNS_RADIUS) * PAWNS_COLORS_INTEGER_ENUM_MAP.get(color) + ENTRANCE_X_MARGIN,
                            y = 99.0 - PAWNS_RADIUS / 2.0 + i * (3.7 + 2 * PAWNS_RADIUS);
                    if (!playerUpdate.getNickname().equals(this.controller.getUsername()) && finalIndex >= 1) {
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
                if (!playerUpdate.getNickname().equals(this.controller.getUsername()) && finalIndex >= 1) {
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
                if (!playerUpdate.getNickname().equals(this.controller.getUsername()) && index >= 1) {
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
                ImageView coinImage = this.coins.get(this.coinsIndex);
                coinImage.setFitWidth(3.2 * PAWNS_RADIUS);
                coinImage.setPreserveRatio(true);
                double x = SCHOOL_BOARD_WIDTH - 1.6 * PAWNS_RADIUS, y = SCHOOL_BOARD_HEIGHT - 115.0;
                if (!playerUpdate.getNickname().equals(this.controller.getUsername()) && index < 1) {
                    x = 0.0 - 1.6 * PAWNS_RADIUS;
                } else if (!playerUpdate.getNickname().equals(this.controller.getUsername()) && index >= 1) {
                    x = y;
                    y = 0.0 - 1.6 * PAWNS_RADIUS;
                }
                coinImage.setLayoutX(x);
                coinImage.setLayoutY(y);
                Label qty = new Label(playerUpdate.getCoins() + "");
                qty.setLayoutX(x + 3 * PAWNS_RADIUS);
                qty.setLayoutY(y + 1.6 * PAWNS_RADIUS);
                qty.setFont(Font.font("Berlin Sans FB", FontWeight.BOLD, 16));
                schoolBoard.getChildren().addAll(coinImage, qty);
                this.coinsIndex++;
            }
            if (!playerUpdate.getNickname().equals(this.controller.getUsername())) index++;
        }
        this.MAIN_PANE.getChildren().addAll(this.PLAYERS_PANES.values());
    }

    void updateGame(GameUpdate gameUpdate) {
        if (gameUpdate.isExpertMode()) {
            if (this.firstGameUpdate) loadCharactersImages(gameUpdate);
            ArrayList<Characters> validCharacters = gameUpdate.getValidCharacters();
            final double CARDS_MARGIN_Y = 20.0,
                    CARDS_HEIGHT = SCHOOL_BOARD_WIDTH - 2 * CARDS_MARGIN_Y,
                    CARDS_MARGIN_X = 20.0, CARDS_SPACE_X = 10.0,
                    CARDS_WIDTH = (((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0) - 2 * CARDS_MARGIN_X - 2 * CARDS_SPACE_X) / 3.0;
            for (int i = 0; i < validCharacters.size(); i++) {
                final double CARD_X = CARDS_MARGIN_X + (CARDS_SPACE_X + CARDS_WIDTH) * i, CARD_Y = GUI_HEIGHT - CARDS_HEIGHT - CARDS_MARGIN_Y;
                Characters character = validCharacters.get(i);
                ImageView cardImage = this.characterCards.get(character).getImageView();
                cardImage.setFitHeight(CARDS_HEIGHT);
                cardImage.setFitWidth(CARDS_WIDTH);
                cardImage.setLayoutY(CARD_Y);
                cardImage.setLayoutX(CARD_X);
                this.MAIN_PANE.getChildren().add(cardImage);
                if (character.equals(Characters.MONK) || character.equals(Characters.SPOILED_PRINCESS)) {
                    ArrayList<PawnsColors> pawns = character.equals(Characters.MONK) ? gameUpdate.getMonkStudents() : gameUpdate.getSpoiledPrincessStudents();
                    for (int j = 0; j < pawns.size(); j++) {
                        ImageView pawnImage;
                        PawnsColors color = pawns.get(j);
                        if (this.studentsIndexes.get(color) >= this.students.get(color).size()) {
                            pawnImage = new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(color));
                            this.students.get(color).add(pawnImage);
                        } else pawnImage = this.students.get(color).get(this.studentsIndexes.get(color));
                        pawnImage.setFitWidth(1.8 * PAWNS_RADIUS);
                        pawnImage.setFitHeight(1.8 * PAWNS_RADIUS);
                        pawnImage.setLayoutY(CARD_Y + PAWNS_RADIUS + (1.8 * PAWNS_RADIUS + 5.0) * j);
                        pawnImage.setLayoutX(CARD_X + CARDS_WIDTH - 2.8 * PAWNS_RADIUS);
                        this.MAIN_PANE.getChildren().add(pawnImage);
                        this.studentsIndexes.put(color, this.studentsIndexes.get(color) + 1);
                    }
                }
                if (character.equals(Characters.GRANDMA_HERBS)) {
                    for (int j = 0; j < gameUpdate.getGrandmaHerbsNoEntryTiles(); j++) {
                        ImageView noEntryImage = this.noEntryTiles.get(this.noEntryTilesIndex);
                        noEntryImage.setFitWidth(2 * PAWNS_RADIUS);
                        noEntryImage.setPreserveRatio(true);
                        noEntryImage.setLayoutY(CARD_Y + PAWNS_RADIUS + 1.4 * PAWNS_RADIUS * j);
                        noEntryImage.setLayoutX(CARD_X + CARDS_WIDTH - 3 * PAWNS_RADIUS);
                        this.noEntryTilesIndex++;
                        this.MAIN_PANE.getChildren().add(noEntryImage);
                    }
                }
            }
        }
        String message;
        if (this.controller.getUsername().equals(gameUpdate.getCurrentPlayer())) {
            message = "È il tuo turno";
        } else {
            message = "È il turno di " + gameUpdate.getCurrentPlayer();
        }
        this.showTurnMessage(message);
    }

    void showTurnMessage(String message) {
        this.MAIN_PANE.getChildren().remove(this.TURN_MESSAGE);
        this.TURN_MESSAGE.setText(message);
        this.TURN_MESSAGE.setFont(Font.font("Berlin Sans FB", 20));
        this.TURN_MESSAGE.setMinWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.TURN_MESSAGE.setMaxWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.TURN_MESSAGE.setLayoutX(GUI_WIDTH - (GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.TURN_MESSAGE.setAlignment(Pos.CENTER);
        this.TURN_MESSAGE.setTextAlignment(TextAlignment.CENTER);
        this.TURN_MESSAGE.setLayoutY(GUI_HEIGHT - 150);
        this.TURN_MESSAGE.setPadding(new Insets(0, 5.0, 0, 5.0));
        this.MAIN_PANE.getChildren().add(this.TURN_MESSAGE);
    }

    void showGamePhaseMessage(String message) {
        this.MAIN_PANE.getChildren().remove(this.GAME_PHASE_MESSAGE);
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
        this.MAIN_PANE.getChildren().add(this.GAME_PHASE_MESSAGE);
    }

    void showCharacterUsedMessage(String message) {
        this.MAIN_PANE.getChildren().remove(this.CHARACTER_USED_MESSAGE);
        this.CHARACTER_USED_MESSAGE.setWrapText(true);
        this.CHARACTER_USED_MESSAGE.setText(message);
        this.CHARACTER_USED_MESSAGE.setFont(Font.font("Berlin Sans FB", 20));
        this.CHARACTER_USED_MESSAGE.setMinWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.CHARACTER_USED_MESSAGE.setMaxWidth((GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.CHARACTER_USED_MESSAGE.setLayoutX(GUI_WIDTH - (GUI_WIDTH - SCHOOL_BOARD_HEIGHT) / 2.0);
        this.CHARACTER_USED_MESSAGE.setAlignment(Pos.CENTER);
        this.CHARACTER_USED_MESSAGE.setTextAlignment(TextAlignment.CENTER);
        this.CHARACTER_USED_MESSAGE.setLayoutY(GUI_HEIGHT - 100);
        this.CHARACTER_USED_MESSAGE.setPadding(new Insets(0, 5.0, 0, 5.0));
        this.MAIN_PANE.getChildren().add(this.CHARACTER_USED_MESSAGE);
    }

    void showAssistantCardsPane(ArrayList<AssistantCard> availableCards) {
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
                this.controller.sendObjectMessage(cardResponse);
            });
            this.ASSISTANT_CARDS_PANE.getChildren().add(cardImage);
        }
        this.MAIN_PANE.getChildren().add(this.ASSISTANT_CARDS_PANE);
    }

    void addRequestColorHandler(SelectColorRequest selectColorRequest) {
        this.CHARACTER_PARAMS_PANE.setBackground(Background.fill(new Color(0.05, 0.05, 0.05, 0.55)));
        this.CHARACTER_PARAMS_PANE.getChildren().clear();
        this.CHARACTER_PARAMS_PANE.setMaxWidth(BOARD_PANE_WIDTH);
        this.CHARACTER_PARAMS_PANE.setMinWidth(BOARD_PANE_WIDTH);
        this.CHARACTER_PARAMS_PANE.setMaxHeight(BOARD_PANE_HEIGHT);
        this.CHARACTER_PARAMS_PANE.setMinHeight(BOARD_PANE_HEIGHT);
        this.CHARACTER_PARAMS_PANE.setLayoutX(SCHOOL_BOARD_WIDTH);
        final double CARD_X = (BOARD_PANE_WIDTH - 280.0) / 2.0, CARD_Y = 50.0, CARD_WIDTH = 280.0, CARD_HEIGHT = 410.0;
        Characters character = selectColorRequest.getCharacter();
        ImageView cardImage = this.characterParams.get(character).getImageView();
        cardImage.setFitWidth(CARD_WIDTH);
        cardImage.setFitHeight(CARD_HEIGHT);
        cardImage.setLayoutY(CARD_Y);
        cardImage.setLayoutX(CARD_X);
        this.CHARACTER_PARAMS_PANE.getChildren().add(cardImage);
        ArrayList<PawnsColors> validColors = selectColorRequest.getValues();
        final double PAWN_WIDTH = 6 * PAWNS_RADIUS,
                PAWN_HEIGHT = 6 * PAWNS_RADIUS,
                UPPER_SPACE = (CARD_WIDTH - 3 * PAWN_WIDTH) / 4.0,
                DOWN_SPACE = (((CARD_WIDTH - 2 * PAWN_WIDTH) / 2.0 - 60.0) * 2) / ((int)Math.floor(validColors.size() / 2.0) - 1);
        for (PawnsColors color : validColors) {
            int n = PAWNS_COLORS_INTEGER_ENUM_MAP.get(color);
            double x = CARD_X +
                    (UPPER_SPACE + (PAWN_WIDTH + UPPER_SPACE) * ((int)Math.floor(n / 2.0))) * (1 - n % 2) +
                    (60.0 + (PAWN_WIDTH + DOWN_SPACE) * ((int)Math.floor(n / 2.0))) * (n % 2),
                    y = CARD_Y + CARD_HEIGHT / 2.0 + 30.0 + (PAWN_HEIGHT + 25.0) * (n % 2);
            ImageView pawnImage;
            if (this.studentsIndexes.get(color) >= this.students.get(color).size()) {
                pawnImage = new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(color));
                this.students.get(color).add(pawnImage);
            } else pawnImage = this.students.get(color).get(this.studentsIndexes.get(color));
            pawnImage.setFitWidth(PAWN_WIDTH);
            pawnImage.setFitHeight(PAWN_HEIGHT);
            pawnImage.setLayoutY(y);
            pawnImage.setLayoutX(x);
            this.characterColorsList.add(new ColorImage(color, pawnImage));
            this.CHARACTER_PARAMS_PANE.getChildren().add(pawnImage);
            this.studentsIndexes.put(color, this.studentsIndexes.get(color) + 1);
        }
        this.MAIN_PANE.getChildren().add(this.CHARACTER_PARAMS_PANE);
        this.addCharacterListsListeners();
    }

    void addRequestIslandHandler(SelectIslandRequest selectIslandRequest) {
        this.ISLANDS.forEach((key, island) -> {
            if (selectIslandRequest.getValidIndexes().contains(island.getIndex())) {
                island.getImageView().setCursor(Cursor.HAND);
                island.getImageView().setOnMouseClicked(event -> {
                    SelectIslandResponse response = new SelectIslandResponse(island.getIndex());
                    this.controller.sendObjectMessage(response);
                    this.removeHandlers();
                });
            }
        });
    }

    void addRequestPawnHandler(SelectPawnRequest selectPawnRequest) {
        this.CHARACTER_PARAMS_PANE.setBackground(Background.fill(new Color(0.05, 0.05, 0.05, 0.55)));
        this.CHARACTER_PARAMS_PANE.getChildren().clear();
        this.CHARACTER_PARAMS_PANE.setMaxWidth(BOARD_PANE_WIDTH);
        this.CHARACTER_PARAMS_PANE.setMinWidth(BOARD_PANE_WIDTH);
        this.CHARACTER_PARAMS_PANE.setLayoutX(SCHOOL_BOARD_WIDTH);
        this.CHARACTER_PARAMS_PANE.setMaxHeight(BOARD_PANE_HEIGHT);
        this.CHARACTER_PARAMS_PANE.setMinHeight(BOARD_PANE_HEIGHT);
        final double CARD_X = (BOARD_PANE_WIDTH - 280.0) / 2.0, CARD_Y = 50.0, CARD_WIDTH = 280.0, CARD_HEIGHT = 410.0;
        Characters character = selectPawnRequest.getCharacter();
        ImageView cardImage = this.characterParams.get(character).getImageView();
        cardImage.setFitWidth(CARD_WIDTH);
        cardImage.setFitHeight(CARD_HEIGHT);
        cardImage.setLayoutY(CARD_Y);
        cardImage.setLayoutX(CARD_X);
        this.CHARACTER_PARAMS_PANE.getChildren().add(cardImage);
        ArrayList<PawnsColors> validPawns = selectPawnRequest.getValidPawns();
        final double PAWN_WIDTH = 6 * PAWNS_RADIUS,
                PAWN_HEIGHT = 6 * PAWNS_RADIUS,
                X_SPACE = (CARD_WIDTH - 2 * PAWN_WIDTH) / 3.0,
                Y_SPACE = 20.0;
        for (int i = 0; i < validPawns.size(); i++) {
            double x = CARD_X + X_SPACE + (i % 2) * (PAWN_WIDTH + X_SPACE),
                    y = CARD_Y + CARD_HEIGHT / 2.0 + 30.0 + (PAWN_HEIGHT + Y_SPACE) * ((int) Math.floor(i / 2.0));
            ImageView pawnImage;
            PawnsColors color = validPawns.get(i);
            if (this.studentsIndexes.get(color) >= this.students.get(color).size()) {
                pawnImage = new ImageView(PAWNS_COLORS_IMAGE_ENUM_MAP.get(color));
                this.students.get(color).add(pawnImage);
            } else pawnImage = this.students.get(color).get(this.studentsIndexes.get(color));
            pawnImage.setFitWidth(PAWN_WIDTH);
            pawnImage.setFitHeight(PAWN_HEIGHT);
            pawnImage.setLayoutY(y);
            pawnImage.setLayoutX(x);
            this.characterPawnsList.add(new TileImage(i, pawnImage));
            this.CHARACTER_PARAMS_PANE.getChildren().add(pawnImage);
            this.studentsIndexes.put(color, this.studentsIndexes.get(color) + 1);
        }
        this.addCharacterListsListeners();
        this.MAIN_PANE.getChildren().add(this.CHARACTER_PARAMS_PANE);
    }

    void addCharacterListsListeners() {
        this.characterColorsList.forEach(color -> {
            color.getImageView().setCursor(Cursor.HAND);
            color.getImageView().setOnMouseClicked(event -> {
                SelectColorResponse response = new SelectColorResponse(color.getColor());
                this.controller.sendObjectMessage(response);
                this.removeCharacterListsListeners();
            });
        });
        this.characterPawnsList.forEach(pawn -> {
            pawn.getImageView().setCursor(Cursor.HAND);
            pawn.getImageView().setOnMouseClicked(event -> {
                SelectPawnResponse response = new SelectPawnResponse(pawn.getIndex());
                this.controller.sendObjectMessage(response);
                this.removeCharacterListsListeners();
            });
        });
    }
    void removeCharacterListsListeners() {
        this.MAIN_PANE.getChildren().remove(this.CHARACTER_PARAMS_PANE);
        this.characterPawnsList.forEach(pawn -> {
            pawn.getImageView().setCursor(Cursor.DEFAULT);
            pawn.getImageView().setOnMouseClicked(null);
        });
        this.characterColorsList.forEach(color -> {
            color.getImageView().setCursor(Cursor.DEFAULT);
            color.getImageView().setOnMouseClicked(null);
        });
        this.characterPawnsList.clear();
        this.characterColorsList.clear();
    }

    void addMoveEntranceStudentsHandlers(MoveStudentsManager manager) {
        this.entrance.forEach(pawnImage -> {
            pawnImage.getImageView().setCursor(Cursor.HAND);
            pawnImage.getImageView().setOnMouseClicked(new ActionPhaseEntranceHandler(pawnImage, manager));
        });
        this.PLAYERS_PANES.get(this.controller.getUsername()).setCursor(Cursor.HAND);
        this.PLAYERS_PANES.get(this.controller.getUsername()).setOnMouseClicked(new DiningRoomHandler(manager));
        this.ISLANDS.forEach((index, island) -> {
            island.getImageView().setCursor(Cursor.HAND);
            island.getImageView().setOnMouseClicked(new ActionPhaseIslandHandler(island, manager));
        });
    }

    void addMoveMNHandlers(MoveMNManager manager) {
        manager.setCurrentMNPosition(this.currentMNPosition);
        this.ISLANDS.forEach((index, island) -> {
            if (manager.getValidIslands().contains(index)) {
                island.getImageView().setCursor(Cursor.HAND);
                island.getImageView().setOnMouseClicked(new MoveMNIslandHandler(island, manager));
            }
        });
    }

    void addCharactersHandlers() {
        this.characterCards.forEach((character, image) -> {
            image.getImageView().setCursor(Cursor.HAND);
            image.getImageView().setOnMouseClicked((event) -> {
                UseCharacterCard useCharacterCard = new UseCharacterCard(character);
                this.controller.sendObjectMessage(useCharacterCard);
            });
        });
    }

    void addChooseCloudHandler(ChooseCloudManager manager) {
        this.CLOUDS.forEach(cloud -> {
            cloud.getImageView().setCursor(Cursor.HAND);
            cloud.getImageView().setOnMouseClicked(new ChooseCloudHandler(cloud, manager));
        });
    }

    void removeCharactersHandlers() {
        this.characterCards.forEach((character, image) -> {
            image.getImageView().setCursor(Cursor.DEFAULT);
            image.getImageView().setOnMouseClicked(null);
        });
    }

    void removeHandlers() {
        this.entrance.forEach(pawnImage -> {
            pawnImage.getImageView().setCursor(Cursor.DEFAULT);
            pawnImage.getImageView().setOnMouseClicked(null);
        });
        this.PLAYERS_PANES.get(this.controller.getUsername()).setOnMouseClicked(null);
        this.PLAYERS_PANES.get(this.controller.getUsername()).setCursor(Cursor.DEFAULT);
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
