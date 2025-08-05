package game.core.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class StandardBlock implements Block {
    private int hp = 50;

    @Override
    public void hit() {

    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(50, 50, 100, 70);

        gc.setStroke(Color.BLACK);
        gc.strokeRect(50, 50, 100, 70);
    }
}
