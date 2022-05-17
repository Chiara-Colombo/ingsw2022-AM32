package it.polimi.ingsw.client;

import it.polimi.ingsw.model.PawnsColors;
import it.polimi.ingsw.model.TowersColors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;

public class PlayerUpdate implements Serializable {
    private final String nickname;
    private final ArrayList<PawnsColors> entranceStudents, professors;
    private final EnumMap<PawnsColors, Integer> diningRoom;
    private final TowersColors towersColor;
    private final int towers;

    public PlayerUpdate(String nickname, ArrayList<PawnsColors> entranceStudents, ArrayList<PawnsColors> professors, EnumMap<PawnsColors, Integer> diningRoom, TowersColors towersColor, int towers) {
        this.nickname = nickname;
        this.entranceStudents = entranceStudents;
        this.professors = professors;
        this.diningRoom = diningRoom;
        this.towersColor = towersColor;
        this.towers = towers;
    }

    public String getNickname() {
        return this.nickname;
    }

    public Iterator<PawnsColors> getEntranceStudents() {
        return this.entranceStudents.iterator();
    }

    public EnumMap<PawnsColors, Integer> getDiningRoom() {
        return this.diningRoom;
    }

    public Iterator<PawnsColors> getProfessors() {
        return this.professors.iterator();
    }

    public int getTowers() {
        return this.towers;
    }

    public TowersColors getTowersColor() {
        return towersColor;
    }
}
