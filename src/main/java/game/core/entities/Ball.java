package game.core.entities;

public class Ball {
    Position position;
    VelocityVector velocity;

    public Ball(Position position, VelocityVector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void move() {
        position =  new Position(position.x() + velocity.x(), position.y() + velocity.y());
    }

    public Position getPosition() {
        return position;
    }
}
