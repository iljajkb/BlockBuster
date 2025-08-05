package game.entities;

import game.core.entities.Ball;
import game.core.entities.VelocityVector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import game.core.entities.Position;

import static org.assertj.core.api.Assertions.assertThat;


public class BallTest {

    // TDD

    @Test
    @DisplayName("Tests if ball moves correctly in one direction")
    void ballMoveCorrectly() {
        Position position = new Position(100, 100);
        VelocityVector velocityVector = new VelocityVector(5, 0);
        Ball ball = new Ball(position, velocityVector); // x=100, y=100, xVel=5, yVel=0
        ball.move();
        assertThat(ball.getPosition()).isEqualTo(new Position(105, 100));
    }
}
