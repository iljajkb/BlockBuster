package game.core.entities.ball;

import game.core.entities.MyVector;

public class Ball {
    private MyVector position;
    private MyVector velocity;

    private int damage = 50;

    public Ball(MyVector position, MyVector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void move() {
        position =  position.add(velocity);
    }

    public void reset(MyVector position, MyVector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public MyVector getPosition() {
        return position;
    }

    public MyVector getVelocity() {
        return velocity;
    }

    public void setVelocity(MyVector vel) {
        this.velocity = vel;
    }

    public int getCurrentDamage() {
        return damage;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }




}
