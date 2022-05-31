package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.model.TowersColors;
import it.polimi.ingsw.model.Wizards;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

import java.util.*;

public class Utils {
    public static final String CARDS_RESOURCE_PATH = "src\\main\\resources\\assistantCards.json";
    public static final Image SCHOOL_BOARD = new Image("\\assets\\school_board.png");
    public static final int DEFAULT_SERVER_PORT = 30300,
            CLI_WIDTH = 80,
            CLI_HEIGHT = 80,
            GUI_WIDTH = 1280,
            GUI_HEIGHT = 700,
            SCHOOL_BOARD_WIDTH = 200,
            SCHOOL_BOARD_HEIGHT = GUI_HEIGHT - SCHOOL_BOARD_WIDTH,
            BOARD_PANE_WIDTH = GUI_WIDTH - 2 * SCHOOL_BOARD_WIDTH,
            BOARD_PANE_HEIGHT = GUI_HEIGHT - SCHOOL_BOARD_WIDTH,
            ISLANDS_ELLIPSE_X_AXIS = 350,
            ISLANDS_ELLIPSE_Y_AXIS = 190,
            ISLANDS_RADIUS = 500,
            PAWNS_RADIUS = 10,
            ISLAND_DIMENSION = 75,
            CLOUD_DIMENSION = 60,
            CARD_WIDTH = 100,
            CARD_HEIGHT = 175,
            CARDS_MARGIN_X = (GUI_WIDTH - 2 * SCHOOL_BOARD_WIDTH - 5 * CARD_WIDTH) / 6,
            CARDS_MARGIN_Y = (GUI_HEIGHT - SCHOOL_BOARD_WIDTH - 2 * CARD_HEIGHT) / 3;
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final EnumMap<Wizards, Image> WIZARDS_IMAGE_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(Wizards.FIRST, new Image("\\assets\\wizard_1.png")),
            Map.entry(Wizards.SECOND, new Image("\\assets\\wizard_2.png")),
            Map.entry(Wizards.THIRD, new Image("\\assets\\wizard_3.png")),
            Map.entry(Wizards.FOURTH, new Image("\\assets\\wizard_4.png"))
    ));
    public static final EnumMap<PawnsColors, Image> PAWNS_COLORS_IMAGE_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(PawnsColors.PINK, new Image("\\assets\\pawn_pink.png")),
            Map.entry(PawnsColors.BLUE, new Image("\\assets\\pawn_blue.png")),
            Map.entry(PawnsColors.RED, new Image("\\assets\\pawn_red.png")),
            Map.entry(PawnsColors.GREEN, new Image("\\assets\\pawn_green.png")),
            Map.entry(PawnsColors.YELLOW, new Image("\\assets\\pawn_yellow.png"))
    ));
    public static final EnumMap<PawnsColors, Image> PROFESSOR_COLORS_IMAGE_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(PawnsColors.PINK, new Image("\\assets\\professor_pink.png")),
            Map.entry(PawnsColors.BLUE, new Image("\\assets\\professor_blue.png")),
            Map.entry(PawnsColors.RED, new Image("\\assets\\professor_red.png")),
            Map.entry(PawnsColors.GREEN, new Image("\\assets\\professor_green.png")),
            Map.entry(PawnsColors.YELLOW, new Image("\\assets\\professor_yellow.png"))
    ));
    public static final EnumMap<TowersColors, Paint> TOWERS_COLORS_PAINT_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(TowersColors.BLACK, Paint.valueOf("#101010")),
            Map.entry(TowersColors.WHITE, Paint.valueOf("#fafafa")),
            Map.entry(TowersColors.GREY, Paint.valueOf("#adadad"))
    ));
    public static final EnumMap<PawnsColors, Integer> PAWNS_COLORS_INTEGER_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(PawnsColors.BLUE, 0),
            Map.entry(PawnsColors.PINK, 1),
            Map.entry(PawnsColors.YELLOW, 2),
            Map.entry(PawnsColors.RED, 3),
            Map.entry(PawnsColors.GREEN, 4)
    ));
    public static final ArrayList<Image> ASSISTANT_CARDS_IMAGES = new ArrayList<>(List.of(
            new Image("\\assets\\assistente_1.png"),
            new Image("\\assets\\assistente_2.png"),
            new Image("\\assets\\assistente_3.png"),
            new Image("\\assets\\assistente_4.png"),
            new Image("\\assets\\assistente_5.png"),
            new Image("\\assets\\assistente_6.png"),
            new Image("\\assets\\assistente_7.png"),
            new Image("\\assets\\assistente_8.png"),
            new Image("\\assets\\assistente_9.png"),
            new Image("\\assets\\assistente_10.png")
    ));
    public static final Image CLOUD_IMAGE = new Image("\\assets\\cloud.png");
    public static final Image ISLAND_IMAGE = new Image("\\assets\\island.png");
}