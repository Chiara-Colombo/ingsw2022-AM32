package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Wizards;
import javafx.scene.image.Image;

import java.util.EnumMap;
import java.util.Map;

public class Utils {
    public static final String CARDS_RESOURCE_PATH = "src\\main\\resources\\assistantCards.json";
    public static final int DEFAULT_SERVER_PORT = 30300, CLI_WIDTH = 80, CLI_HEIGHT = 80, GUI_WIDTH = 1280, GUI_HEIGHT = 700;
    public static final String DEFAULT_SERVER_ADDRESS = "localhost";
    public static final EnumMap<Wizards, Image> WIZARDS_IMAGE_MAP = new EnumMap<>(Map.ofEntries(
            Map.entry(Wizards.FIRST, new Image("\\assets\\wizard_1.png")),
            Map.entry(Wizards.SECOND, new Image("\\assets\\wizard_2.png")),
            Map.entry(Wizards.THIRD, new Image("\\assets\\wizard_3.png")),
            Map.entry(Wizards.FOURTH, new Image("\\assets\\wizard_4.png"))
    ));
}