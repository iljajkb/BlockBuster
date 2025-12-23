package game.core.entities.blocks;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import static game.GameConfig.BLOCK_HEIGHT;
import static game.GameConfig.BLOCK_WIDTH;

// for better performance
public class CreateBlockImage {
    private final Color color;

    public CreateBlockImage(Color color) {
        this.color = color;
    }

    public Image createGlowCache() {
        System.out.println("created glow cache");
        Canvas tempCanvas = new Canvas(BLOCK_WIDTH + 40, BLOCK_HEIGHT + 40);
        GraphicsContext tempGc = tempCanvas.getGraphicsContext2D();

        tempGc.save();
        tempGc.setEffect(new DropShadow(15, color));
        tempGc.setFill(color);
        tempGc.fillRoundRect(20, 20, BLOCK_WIDTH, BLOCK_HEIGHT, 10, 10);
        tempGc.restore();

        // create snapchot
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        return tempCanvas.snapshot(params, null);
    }
}
