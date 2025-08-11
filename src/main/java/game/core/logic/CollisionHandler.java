package game.core.logic;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.paddle.Paddle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;

public class CollisionHandler {

    public static void checkForPaddleCollision(Ball ball, Paddle paddle) {
        MyVector pos = ball.getPosition();
        MyVector vel = ball.getVelocity();

        boolean withinX = pos.x >= paddle.getX() - (double) paddle.getPaddleWidth() / 2 &&
                pos.x <= paddle.getX() + (double) paddle.getPaddleWidth() / 2;
        boolean withinY = pos.y + GameConfig.BALL_RADIUS >= paddle.getY() &&
                pos.y - GameConfig.BALL_RADIUS <= paddle.getY() + GameConfig.PADDLE_HEIGHT;

        if (withinX && withinY) {
            vel = vel.flipY();
        }
        ball.setVelocity(vel);
    }

    public static void checkEdgeCollision(Ball ball, Player player) {
        MyVector pos = ball.getPosition();
        MyVector vel = ball.getVelocity();
        if (pos.x <= 0 || pos.x >= GameConfig.FRAME_WIDTH) {
            vel = vel.flipX();
        }
        if (pos.y <= 0) {
            vel = vel.flipY();
        }
        if (pos.y >= GameConfig.FRAME_HEIGHT) {
            player.loseLife();
            ball.reset(new MyVector(600, 450), new MyVector(2, -2));
        }
        ball.setVelocity(vel);
    }
}
