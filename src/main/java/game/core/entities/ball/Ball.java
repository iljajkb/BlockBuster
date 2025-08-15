package game.core.entities.ball;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.paddle.Paddle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball {
    public enum BallType { MAIN, EXTRA }

    private final BallType type;

    private boolean attached = false;

    private MyVector position;
    private MyVector velocity;
    private BallEffects currentEffect;

    private int damage = 50;

    public static Ball createMainBall(Paddle paddle) {
        Ball b = new Ball(new MyVector(0,0), new MyVector(0,0), BallType.MAIN);
        b.reset(paddle);     // setzt Position über Paddle + attached=true
        return b;
    }
    public static Ball createExtraBall(MyVector position, MyVector velocity) {
        return new Ball(position, velocity, BallType.EXTRA); // wähle deine Wunschfarbe
    }

    private Ball(MyVector position, MyVector velocity, BallType type) {
        this.position = position;
        this.velocity = velocity;
        this.type = type;
    }

    public double getSpeed() {
        return velocity.magnitude();
    }

    public void setSpeed(double s) {
        double m = velocity.magnitude();
        if (m > 0) velocity = velocity.scale(s / m);
    }

    public BallType getType() { return type; }
    public boolean isMain() { return type == BallType.MAIN; }

    // >>> Change move() to time-based:
    public void move(double dt) { // dt in seconds
        position = position.add(velocity.scale(dt));
    }

    // Keep an overload if you need it, but avoid using it in the loop:
    @Deprecated
    public void move() { position = position.add(velocity); } // legacy; try not to call it


    public void reset(MyVector position, MyVector velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public void followPaddle(Paddle paddle) {
        if (!attached) return;
        this.position = dockedPos(paddle);
    }

    private MyVector dockedPos(Paddle paddle) {
        double xPos = paddle.getX() - GameConfig.BALL_RADIUS; // same xPos as Paddle
        double yPos = paddle.getY() - GameConfig.PADDLE_HEIGHT - 5;
        return new MyVector(xPos, yPos);
    }

    public void reset(Paddle paddle) {
        double xPos = paddle.getX() - GameConfig.BALL_RADIUS; // same xPos as Paddle
        double yPos = paddle.getY() - GameConfig.PADDLE_HEIGHT - 5;
        setVelocity(new MyVector(0, 0));
        this.position = new MyVector(xPos, yPos);
        attached = true;
    }

    public void launch(MyVector vel) {
        if (!attached) return;
        this.velocity = vel;
        this.attached = false;
    }

    public MyVector getPosition() {
        return position;
    }

    public MyVector getVelocity() {
        return velocity;
    }

    public boolean isAttached() { return attached; }

    public void setVelocity(MyVector vel) {
        this.velocity = vel;
    }

    public int getCurrentDamage() {
        return damage;
    }

    public BallEffects getCurrentEffect() {
        return currentEffect;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    public void render(GraphicsContext gc) {
        if (type == BallType.MAIN) {
            gc.setFill(GameConfig.COLOR_1);
        } else if (type == BallType.EXTRA) {
            gc.setFill(Color.DARKBLUE);
        }
        gc.fillOval(position.x, position.y, GameConfig.BALL_RADIUS * 2, GameConfig.BALL_RADIUS * 2);
    }

}
