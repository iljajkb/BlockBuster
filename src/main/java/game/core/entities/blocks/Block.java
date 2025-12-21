package game.core.entities.blocks;

import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;

import java.util.Optional;

public abstract class Block {
    private MyVector pos;
    protected int hp;

    public Block(int hp) {
        this.hp = hp;
    }

    public abstract Optional<Ball> hit(Ball ball, Player player);
    public boolean isDestroyed() {
        return hp <= 0;
    }
    public abstract void render(GraphicsContext gc);
    public MyVector getPosition() {
        return pos;
    }
    public void setPosition(MyVector pos) {
        this.pos = pos;
    }
}
