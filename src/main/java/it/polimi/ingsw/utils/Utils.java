package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Characters;
import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.model.TowersColors;
import it.polimi.ingsw.model.Wizards;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

import java.util.*;

public class Utils {
    public static final String CARDS_RESOURCE_PATH = "src\\main\\resources\\assistantCards.json";
    public static final int DEFAULT_SERVER_PORT = 30300,
            CLI_WIDTH = 80,
            CLI_HEIGHT = 80,
            GUI_WIDTH = 1280,
            GUI_HEIGHT = 700,
            SCHOOL_BOARD_WIDTH = 200,
            SCHOOL_BOARD_HEIGHT = GUI_HEIGHT - SCHOOL_BOARD_WIDTH,
            BOARD_PANE_WIDTH = GUI_WIDTH - 2 * SCHOOL_BOARD_WIDTH,
            BOARD_PANE_HEIGHT = GUI_HEIGHT - SCHOOL_BOARD_WIDTH,
            ISLANDS_ELLIPSE_X_AXIS = 340,
            ISLANDS_ELLIPSE_Y_AXIS = 190,
            ISLANDS_RADIUS = 500,
            PAWNS_RADIUS = 10,
            ISLAND_DIMENSION = 110,
            CLOUD_DIMENSION = 80,
            CARD_WIDTH = 100,
            CARD_HEIGHT = 175,
            CARDS_MARGIN_X = (GUI_WIDTH - 2 * SCHOOL_BOARD_WIDTH - 5 * CARD_WIDTH) / 6,
            CARDS_MARGIN_Y = (GUI_HEIGHT - SCHOOL_BOARD_WIDTH - 2 * CARD_HEIGHT) / 3;
    public static final double ENTRANCE_X_MARGIN = 33.0 - PAWNS_RADIUS / 2.0,
            ENTRANCE_X_SPACE = 15.5,
            ENTRANCE_Y_MARGIN = 100 / 3.0,
            ENTRANCE_Y_SPACE = 32.0,
            DINING_ROOM_Y_MARGIN = 99.0 - PAWNS_RADIUS / 2.0,
            DINING_ROOM_Y_SPACE = 3.0,
            TOWERS_X_MARGIN = 40.0 + 2 * PAWNS_RADIUS,
            TOWERS_X_SPACE = 15.0;
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final EnumMap<PawnsColors, Integer> PAWNS_COLORS_INTEGER_ENUM_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(PawnsColors.BLUE, 0),
            Map.entry(PawnsColors.PINK, 1),
            Map.entry(PawnsColors.YELLOW, 2),
            Map.entry(PawnsColors.RED, 3),
            Map.entry(PawnsColors.GREEN, 4)
    ));
    public static final ArrayList<Map<Character, Double>>[] GROUP_OF_ISLANDS_OFFSETS = new ArrayList[]{
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', 0.0)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * .35),
                            Map.entry('y', ISLAND_DIMENSION * -.15)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -.35),
                            Map.entry('y', ISLAND_DIMENSION * .15)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -.45),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * .30),
                            Map.entry('y', ISLAND_DIMENSION * -.45)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * .30),
                            Map.entry('y', ISLAND_DIMENSION * .45)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -.8),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * -.45)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * .415)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * .8),
                            Map.entry('y', 0.0)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * -1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * 1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * -1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * 1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * -1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * 1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -2.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * -1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * 1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -2.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * -1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 2.0),
                            Map.entry('y', 0.0)
                    ))
            )),
            new ArrayList<>(List.of(
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * 1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -2.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * -2.0),
                            Map.entry('y', ISLAND_DIMENSION * -1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', 0.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', 0.0),
                            Map.entry('y', ISLAND_DIMENSION * -1.0)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * -.5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 1.0),
                            Map.entry('y', ISLAND_DIMENSION * .5)
                    )),
                    new HashMap<>(Map.ofEntries(
                            Map.entry('x', ISLAND_DIMENSION * 2.0),
                            Map.entry('y', 0.0)
                    ))
            ))
    };
    public static final EnumMap<Characters, String> CHARACTERS_NAME_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(Characters.MONK, "Monaco"),
            Map.entry(Characters.CENTAUR, "Centauro"),
            Map.entry(Characters.FARMER, "Contadino"),
            Map.entry(Characters.GRANDMA_HERBS, "Nonna Erbe"),
            Map.entry(Characters.KNIGHT, "Cavaliere"),
            Map.entry(Characters.MAGIC_MAILMAN, "Postino Magico"),
            Map.entry(Characters.MUSHROOMS_MAN, "Fungaro"),
            Map.entry(Characters.SPOILED_PRINCESS, "Principessa Viziata"),
            Map.entry(Characters.MINSTREL, "Menestrello"),
            Map.entry(Characters.THIEF, "Ladro"),
            Map.entry(Characters.HERALD, "Araldo"),
            Map.entry(Characters.JESTER, "Giullare")
    ));
}