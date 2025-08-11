package game.core.entities;

import game.GameConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    private final static int Y_POS = GameConfig.FRAME_HEIGHT - 10;
    private int xPos;

    private int movingSpeed = 17;

    public Paddle(int xPos) {
        this.xPos = xPos;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return Y_POS;
    }

    public void moveLeft() {
        xPos = xPos - movingSpeed;
    }

    public void moveRight() {
        xPos = xPos + movingSpeed;
    }

    public void updateMovingSpeed(double percentage) {
        movingSpeed = (int) (movingSpeed * percentage);
    }

    public void render(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillRect(xPos - (double) GameConfig.PADDLE_WIDTH / 2,
                Y_POS,
                GameConfig.PADDLE_WIDTH,
                GameConfig.PADDLE_HEIGHT);
    }
}
