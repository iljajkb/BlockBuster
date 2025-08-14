package game.core.entities.paddle;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle {
    private final static int Y_POS = GameConfig.FRAME_HEIGHT - 10;
    private int xPos;
    private int paddleWidth = 120;

    private int movingSpeed = 20;

    public Paddle(int xPos) {
        this.xPos = xPos;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
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

    public void collisionWithBall(Ball ball) {
        double x0 = xPos; // middle of paddle as default collision angle

        double maxAngle = Math.PI / 3.0;

        double term = (ball.getPosition().x - x0) / (paddleWidth / 2.0);

        double u = Math.clamp(term, -1, 1);

        double angle = maxAngle * u; // -> ist max 60 deg, weil u wert zwischen 1 und -1 liefert

        double dx = Math.sin(angle);
        double dy = -Math.cos(angle);

        MyVector direction = new MyVector(dx, dy);
        MyVector newVelocity = direction.scale(ball.getCurrentSpeed());

        ball.setVelocity(newVelocity);
    }
}
