package game.core.logic;

import game.GameConfig;
import game.core.entities.Block;
import game.core.entities.MyVector;
import game.core.entities.paddle.Paddle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;

public class CollisionHandler {

    public static void checkForPaddleCollision(Ball ball, Paddle paddle) {
        MyVector pos = ball.getPosition();
        MyVector vel = ball.getVelocity();

        boolean withinX = pos.x >= paddle.getX() - paddle.getPaddleWidth() / 2.0 &&
                pos.x <= paddle.getX() + paddle.getPaddleWidth() / 2.0;
        boolean withinY = pos.y + GameConfig.BALL_RADIUS >= paddle.getY() - GameConfig.PADDLE_HEIGHT / 2.0 &&
                pos.y - GameConfig.BALL_RADIUS <= paddle.getY() + GameConfig.PADDLE_HEIGHT;
        if (withinX && withinY) {
            paddle.collisionWithBall(ball);
            // vel = vel.flipY();
        }
        // ball.setVelocity(vel);
    }

    public static void checkEdgeCollision(Ball ball, Player player) {
        MyVector pos = ball.getPosition();
        MyVector vel = ball.getVelocity();
        if (pos.x <= 0 || pos.x >= GameConfig.FRAME_WIDTH - GameConfig.BALL_RADIUS * 2) {
            vel = vel.flipX();
        }
        if (pos.y <= 0) {
            vel = vel.flipY();
        }
        if (pos.y >= GameConfig.FRAME_HEIGHT) {
            player.loseLife();
            ball.reset(new MyVector(600, 450), new MyVector(4, 4));
        }
        ball.setVelocity(vel);
    }

    private static boolean ballIsCollidingWithBlock(Ball ball, Block block) {
        MyVector ballPos = ball.getPosition();
        MyVector blockPos = block.getPosition();

        boolean ballWithinBlockX = ballPos.x >= blockPos.x - GameConfig.BALL_RADIUS &&
                ballPos.x <= blockPos.x + GameConfig.BLOCK_WIDTH + GameConfig.BALL_RADIUS;
        boolean ballWithinBlockY = ballPos.y >= blockPos.y - GameConfig.BALL_RADIUS &&
                ballPos.y <= blockPos.y + GameConfig.BLOCK_HEIGHT + GameConfig.BALL_RADIUS;

        return ballWithinBlockX && ballWithinBlockY;
    }

    public static void checkBlockCollision(Ball ball, Block[][] blocks, Player player) {
        for (Block[] row : blocks) {
            for (Block block : row) {
                if (!block.isDestroyed() && ballIsCollidingWithBlock(ball, block)) {
                    block.hit(ball, player);

                    MyVector ballPos = ball.getPosition();
                    MyVector blockPos = block.getPosition();
                    int ballRadius = GameConfig.BALL_RADIUS;

                    double blockRightEdge = blockPos.x + GameConfig.BLOCK_WIDTH;
                    double blockBottomEdge = blockPos.y + GameConfig.BLOCK_HEIGHT;

                    double overlapLeft = (ballPos.x + ballRadius) - blockPos.x;
                    double overlapRight = blockRightEdge - (ballPos.x - ballRadius);
                    double overlapTop = (ballPos.y + ballRadius) - blockPos.y;
                    double overlapBottom = blockBottomEdge - (ballPos.y - ballRadius);

                    double minX = Math.min(overlapLeft, overlapRight);
                    double minY = Math.min(overlapTop,  overlapBottom);

                    if (minX < minY) {
                        ball.setVelocity(ball.getVelocity().flipX());
                    } else {
                        ball.setVelocity(ball.getVelocity().flipY());
                    }
                }
            }
        }
    }

}
