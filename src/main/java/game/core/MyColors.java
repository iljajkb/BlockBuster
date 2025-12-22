package game.core;

import javafx.scene.paint.Color;

public enum MyColors {
    GREEN(Color.GREEN),
    RED(Color.RED),
    BLUE(Color.BLUE),
    ORANGE(Color.ORANGE),
    ORANGERED(Color.ORANGERED),
    CYAN(Color.CYAN),
    WHITE(Color.AZURE),
    ORCHID(Color.ORCHID);

    private final Color color;

    MyColors(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}


