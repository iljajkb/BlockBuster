package game.core.entities.blocks;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

import static game.GameConfig.BLOCK_HEIGHT;
import static game.GameConfig.BLOCK_WIDTH;

public class Particle {
    private double x, y;
    private double vx, vy; // velocity
    private double life = 1.0; // 1.0 new, 0.0 dead
    private final Color color;

    public Particle(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.vx = (Math.random() - 0.5) * 200;
        this.vy = (Math.random() - 0.5) * 200;
    }

    public void update(double dt) {
        x += vx * dt;
        y += vy * dt;
        life -= dt * 1.5;
    }

    public void render(GraphicsContext gc) {
        gc.setGlobalAlpha(life);
        gc.setFill(color);
        gc.fillRect(x, y, 4, 4);
        gc.setGlobalAlpha(1.0);
    }

    public boolean isDead() { return life <= 0; }

    public static void animateMultipleParticles(List<Particle> particlesToAdd, double x, double y, Color color) {
        for (int i = 0; i < 15; i++) {
            particlesToAdd.add(new Particle(x + BLOCK_WIDTH / 2.0, y + BLOCK_HEIGHT / 2.0, color));
        }
    }
}