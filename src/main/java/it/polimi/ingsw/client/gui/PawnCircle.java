package it.polimi.ingsw.client.gui;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PawnCircle extends Circle {
    private final int index;

    public PawnCircle(int index, double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
