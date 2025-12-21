package game.core.entities.blocks;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Optional;

import static game.GameConfig.*;

public class StandardBlock extends Block {
    private static Image blockImage;

    public StandardBlock() {
        super(50);
        if (blockImage == null) {
            CreateBlockImage creator = new CreateBlockImage(COLOR_1);
            blockImage = creator.createGlowCache();
        }
    }

    @Override
    public Optional<Ball> hit(Ball ball, Player player) {
        // clamp to 0 so we don't go negative
        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());
        return Optional.empty();
    }

    @Override
    public void render(GraphicsContext gc) {
        MyVector pos = this.getPosition();
        if (!isDestroyed()) {
            gc.drawImage(blockImage, pos.x - 20, pos.y - 20);
        }
    }

}
