package game.core.logic;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.Paddle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;

public class CollisionHandler {

    public void checkForPaddleCollision(Ball ball, Paddle paddle) {
        MyVector pos = ball.getPosition();
        MyVector vel = ball.getVelocity();
        if (pos.x >= paddle.getX() - ((double) GameConfig.PADDLE_WIDTH / 2) &&
                pos.x <= paddle.getX() + ((double) GameConfig.PADDLE_WIDTH / 2)) {
            vel = vel.flipY();
        }
    }

    public void checkEdgeCollision(Ball ball, Player player) {
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
            ball.reset(new Vec2D(600, 450), new Vec2D(2, -2));
        }
        ball.setVelocity(vel);
    }
}
