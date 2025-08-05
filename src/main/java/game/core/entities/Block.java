package game.core.entities;

import javafx.scene.canvas.GraphicsContext;

public interface Block {
    void hit();
    boolean isDestroyed();
    void render(GraphicsContext gc);
}
