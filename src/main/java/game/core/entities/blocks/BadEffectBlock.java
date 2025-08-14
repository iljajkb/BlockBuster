package game.core.entities.blocks;

import game.GameConfig;
import game.core.entities.EffectDrop;
import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BadEffectBlock extends Block {
    public BadEffectBlock() {
        super(50);
    }

    @Override
    public void hit(Ball ball, Player player) {

    }

    @Override
    public void render(GraphicsContext gc) {
        MyVector pos = this.getPosition();
        if (!isDestroyed()) {
            gc.setFill(Color.DARKORANGE);
            gc.fillRect(pos.x, pos.y, GameConfig.BLOCK_WIDTH, GameConfig.BLOCK_HEIGHT);
        }
//        else {
//            EffectDrop drop = new EffectDrop();
//        }

    }
}
