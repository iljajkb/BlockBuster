package game.core.logic;

import game.GameConfig;
import game.core.controller.GameController;
import game.core.entities.EffectController;
import game.core.entities.blocks.Block;
import game.core.entities.MyVector;
import game.core.entities.Particle;
import game.core.entities.paddle.Paddle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;

import java.util.List;
import java.util.Optional;

public class CollisionHandler {

    public static void checkForPaddleCollision(List<Ball> balls, Paddle paddle, EffectController effectController, List<Ball> ballsToRemove) {
        for (Ball ball : balls) {
            MyVector pos = ball.getPosition();

            if (!ball.isAttached()) { // paddle collision only if ball was released from paddle
                boolean withinX = pos.x >= paddle.getX() - paddle.getPaddleWidth() / 2.0 &&
                        pos.x <= paddle.getX() + paddle.getPaddleWidth() / 2.0;
                boolean withinY = pos.y + GameConfig.BALL_RADIUS >= paddle.getY() - GameConfig.PADDLE_HEIGHT / 2.0 &&
                        pos.y - GameConfig.BALL_RADIUS <= paddle.getY() + GameConfig.PADDLE_HEIGHT;
                if (withinX && withinY) {
                    if (ball.isEffect()) {
                        handleEffectCollision(ball, effectController, ballsToRemove);
                    }
                    paddle.collisionWithBall(ball);
                }
            }
        }
    }

    public static void handleEffectCollision(Ball effectBall, EffectController effectController, List<Ball> ballsToRemove) {
        if (effectBall.isEffect()) {
            effectController.triggerEffect(effectBall.getEffect());
            ballsToRemove.add(effectBall);
        }
    }

    public static void checkEdgeCollision(List<Ball> balls, Player player, Paddle paddle, int frameHeight, GameController controller, List<Ball> ballsToRemove) {
        for (Ball ball : balls) {
            MyVector pos = ball.getPosition();
            MyVector vel = ball.getVelocity();
            if (pos.x <= 0) {
                pos.x = 0;
                vel = vel.flipX();
            }
            if (pos.x >= GameConfig.FRAME_WIDTH - GameConfig.BALL_RADIUS * 2) {
                pos.x = GameConfig.FRAME_WIDTH - GameConfig.BALL_RADIUS * 2;
                vel = vel.flipX();
            }
            if (pos.y <= 0) {
                pos.y = 0;
                vel = vel.flipY();
            }
            if (pos.y >= frameHeight + GameConfig.BALL_RADIUS) {
                if (ball.isMain()) {
                    player.loseLife();
                    ball.reset(paddle);
                    controller.removeAllExtraBalls();
                    controller.removeAllEffects();
                    return;
                } else if (ball.getType() == Ball.BallType.EXTRA) {
                    ballsToRemove.add(ball);
                }
            }
            ball.setVelocity(vel);
        }
    }

    private static boolean ballIsCollidingWithBlock(Ball ball, Block block) {
        if (block.isDestroyed()) {
            return false;
        }
        // effect balls should not collide, just drop down
        if (ball.isEffect()) {
            return false;
        }

        MyVector ballPos = ball.getPosition();
        MyVector blockPos = block.getPosition();

        boolean ballWithinBlockX = ballPos.x >= blockPos.x - GameConfig.BALL_RADIUS &&
                ballPos.x <= blockPos.x + GameConfig.BLOCK_WIDTH + GameConfig.BALL_RADIUS;
        boolean ballWithinBlockY = ballPos.y >= blockPos.y - GameConfig.BALL_RADIUS &&
                ballPos.y <= blockPos.y + GameConfig.BLOCK_HEIGHT + GameConfig.BALL_RADIUS;

        return ballWithinBlockX && ballWithinBlockY;

    }

    public static boolean checkBlockCollision(List<Ball> balls, Block[][] blocks, Player player, List<Ball> ballsToAdd, List<Particle> particlesToAdd) {
        for (Block[] row : blocks) {
            for (Block block : row) {
                for (Ball ball : balls) {
                    if (!block.isDestroyed() && ballIsCollidingWithBlock(ball, block)) {

                        Optional<Ball> ballOptional = block.hit(ball, player, particlesToAdd);
                        ballOptional.ifPresent(ballsToAdd::add); // adds extra or effect balls when hit

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
                        double minY = Math.min(overlapTop, overlapBottom);

                        if (minX < minY) {
                            ball.setVelocity(ball.getVelocity().flipX());
                        } else {
                            ball.setVelocity(ball.getVelocity().flipY());
                        }

                        return true;
                    }
                }
            }
        }
        return false;
    }

}
