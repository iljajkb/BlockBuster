package game.core;

import javafx.scene.paint.Color;

import java.util.Random;

public class ColorPicker {
    private static final Random RANDOM = new Random();

    public static Color pickColor() {
        MyColors[] colors = MyColors.values();
        return colors[RANDOM.nextInt(colors.length)].getColor();
    }

}
