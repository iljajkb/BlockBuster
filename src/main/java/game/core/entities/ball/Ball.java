package game.core.entities.ball;

import game.GameConfig;
import game.core.entities.MyVector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

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


    public void render(GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(position.x, position.y, GameConfig.BALL_RADIUS * 2, GameConfig.BALL_RADIUS * 2);
    }
}
