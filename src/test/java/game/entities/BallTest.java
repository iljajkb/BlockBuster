package game.entities;

import game.GameConfig;
import game.core.entities.paddle.Paddle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import game.core.entities.MyVector;
import game.core.logic.CollisionHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class BallTest {

    // TDD

    @Test
    @DisplayName("Checks if ball moves correctly in one direction")
    void ballMoveCorrectly() {
        MyVector position = new MyVector(100, 100);
        MyVector velocityVector = new MyVector(5, 0);
        Ball ball = new Ball(position, velocityVector); // x=100, y=100, xVel=5, yVel=0
        ball.move();
        assertThat(ball.getPosition()).isEqualTo(new MyVector(105, 100));
    }

    @Test
    @DisplayName("Checks if ball changes directory when touching edge")
    void ballChangesDirection() {
        MyVector position = new MyVector(1200, 0);
        MyVector velocityVector = new MyVector(5, 5);
        Ball ball = new Ball(position, velocityVector);
        Player player = new Player("name");
        CollisionHandler.checkEdgeCollision(ball, player);
        assertThat(ball.getVelocity()).isEqualTo(new MyVector(-5, -5));
    }

    @Test
    @DisplayName("Check for Collision ball with paddle with exact xPos")
    void checkPaddleCollision() {
        // Arrange
        Paddle paddle = new Paddle(50);
        MyVector position = new MyVector(50, paddle.getY());
        MyVector velocityVector = new MyVector(1, -5);
        Ball ball = new Ball(position, velocityVector);
        // Act
        CollisionHandler.checkForPaddleCollision(ball, paddle);
        // Assert
        assertThat(ball.getVelocity()).isEqualTo(new MyVector(1, 5));
    }

    @Test
    @DisplayName("Check for PLayer losing one life when Ball hitting bottom and reset")
    void checkForLifeLoss1() {
        // Arrange
        Player p1 = new Player("p1");
        MyVector position = new MyVector(0, GameConfig.FRAME_HEIGHT);
        MyVector velocityVector = new MyVector(0, 0);
        Ball ball = new Ball(position, velocityVector);
        // Act
        CollisionHandler.checkEdgeCollision(ball, p1);
        // Assert
        assertThat(p1.getLives()).isEqualTo(2);
        assertThat(ball.getPosition()).isEqualTo(new MyVector(600, 450)); // ball reset
    }
}
