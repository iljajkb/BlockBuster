package game.core.entities;

import game.GameConfig;
import game.core.entities.ball.Ball;
import game.core.entities.ball.BallEffects;
import javafx.scene.canvas.GraphicsContext;

public interface Block {


    void hit(Ball ball, Player player);
    boolean isDestroyed();
    void render(GraphicsContext gc);
    MyVector getPosition();
    void setPosition(MyVector pos);


}
