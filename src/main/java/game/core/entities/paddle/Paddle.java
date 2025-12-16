package game.core.entities.paddle;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    private final int yPos;
    private int xPos;
    private int paddleWidth = 120;

    private int movingSpeed = 5;

    public Paddle(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
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

    public void updateMovingSpeed(double percentage) {
        movingSpeed = (int) (movingSpeed * percentage);
    }

    public void updatePaddleWidth(int newWidth) {
        paddleWidth = newWidth;
    }

    public void render(GraphicsContext gc) {
        gc.setFill(GameConfig.COLOR_1);
        gc.fillRect(xPos - (double) paddleWidth / 2,
                yPos,
                paddleWidth,
                GameConfig.PADDLE_HEIGHT);
    }

    // NOTE: may move to different class for SRP
    public void collisionWithBall(Ball ball) {
        double x0 = xPos; // middle of paddle as default collision angle

        double maxAngle = Math.PI / 3.0;

        double term = (ball.getPosition().x - x0) / (paddleWidth / 2.0);

        double u = Math.clamp(term, -1, 1);

        double angle = maxAngle * u; // -> ist max 60 deg, weil u wert zwischen 1 und -1 liefert

        double dx = Math.sin(angle);
        double dy = -Math.cos(angle); // negative because negative y is moving up

        MyVector direction = new MyVector(dx, dy);
        MyVector newVelocity = direction.scale(ball.getSpeed());

        ball.setVelocity(newVelocity);
    }
}
