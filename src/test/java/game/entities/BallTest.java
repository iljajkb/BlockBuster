package game.entities;

import game.core.entities.ball.Ball;
import game.core.entities.MyVector;
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
        ball.checkEdgeCollision();
        assertThat(ball.getVelocity()).isEqualTo(new MyVector(-5, -5));
    }
}
