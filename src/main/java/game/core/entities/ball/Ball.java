package game.core.entities.ball;

import game.core.entities.MyVector;

public class Ball {
    private MyVector position;
    private MyVector velocity;

    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 900;

    private int damage = 50;

    public Ball(MyVector position, MyVector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void move() {
        position =  position.add(velocity);
    }

    public MyVector getPosition() {
        return position;
    }

    public MyVector getVelocity() {
        return velocity;
    }

    public int getCurrentDamage() {
        return damage;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    protected MyVector getSpeed() {
        return velocity;
    }

    protected void setSpeed(MyVector vel) {
        this.velocity = vel;
    }

    public void checkEdgeCollision() {
        if (position.x <= 0 || position.x >= FRAME_WIDTH) {
            velocity = velocity.flipX();
        }
        if (position.y <= 0 || position.y >= FRAME_HEIGHT) {
            velocity = velocity.flipY();
        }
    }

}
