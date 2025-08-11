package game.core.entities.paddle;

import game.GameConfig;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    private final static int Y_POS = GameConfig.FRAME_HEIGHT - 10;
    private int xPos;
    private int paddleWidth = 100;

    private int movingSpeed = 20;

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
        if ((xPos - (paddleWidth / 2)) > 0) {
            xPos = xPos - movingSpeed; // prevents going out of frame
        }
    }
    public void moveRight() {
        if ((xPos + (paddleWidth / 2)) < GameConfig.FRAME_WIDTH) {
            xPos = xPos + movingSpeed;
        }
    }

    public int getPaddleWidth() {
        return paddleWidth;
    }

    protected void updateMovingSpeed(double percentage) {
        movingSpeed = (int) (movingSpeed * percentage);
    }

    protected void updatePaddleWidth(int newWidth) {
        paddleWidth = newWidth;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(GameConfig.COLOR_1);
        gc.fillRect(xPos - (double) paddleWidth / 2,
                Y_POS,
                paddleWidth,
                GameConfig.PADDLE_HEIGHT);
    }
}
