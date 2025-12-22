package game.core.entities.blocks;

import game.core.entities.MyVector;
import game.core.entities.Particle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.Optional;

public abstract class Block {
    private MyVector pos;
    protected int hp;
    private long lastHitTime = 0;

    public Block(int hp) {
        this.hp = hp;
    }

    public abstract Optional<Ball> hit(Ball ball, Player player, List<Particle> particlesToAdd);
    public boolean isDestroyed() {
        return hp <= 0;
    }
    public abstract void render(GraphicsContext gc);
    public MyVector getPosition() {
        return pos;
    }
    public void setPosition(MyVector pos) {
        this.pos = pos;
    }
    public void hitHandler(Ball ball, Player player, List<Particle> particlesToAdd) {
        long now = System.currentTimeMillis();
        // prevents hit register in less than 500 milliseconds
        if (now - lastHitTime < 250) return;
        lastHitTime = now;

        hp = Math.max(0, hp - ball.getCurrentDamage());
        player.increaseScore(ball.getCurrentDamage());

        MyVector pos = this.getPosition();
        if (this.hp <= 0) {
            Particle.animateMultipleParticles(particlesToAdd, pos.x, pos.y);
        }
    }
}
