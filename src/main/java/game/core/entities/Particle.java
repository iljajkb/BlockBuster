package game.core.entities;

import game.GameConfig;
import game.core.ColorPicker;
import game.core.MyColors;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

import static game.GameConfig.BLOCK_HEIGHT;
import static game.GameConfig.BLOCK_WIDTH;

public class Particle {
    private double x, y;
    private double randomVx, randomVy;
    private double linearVx, linearVy;// velocity
    private double life = 1.0; // 1.0 new, 0.0 dead
    private final Color color;

    public Particle(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
        this.randomVx = (Math.random() - 0.5) * 200;
        this.randomVy = (Math.random() - 0.5) * 200;
        this.linearVx = 10;
        this.linearVy = 10;
    }

    public void updateSplashParticles(double dt) {
        x += randomVx * dt;
        y += randomVy * dt;
        life -= dt * 1.5;
    }

    public void updateTailParticles(double dt) {
        x += linearVx * dt;
        y += linearVy * dt;
        life -= dt * 1.5;
    }

    public void render(GraphicsContext gc) {
        gc.setGlobalAlpha(life);
        gc.setFill(color);
        gc.fillRect(x, y, 4, 4);
        gc.setGlobalAlpha(1.0);
    }

    public boolean isDead() { return life <= 0; }

    public static void animateParticleTail(List<Particle> particlesToAdd, double x, double y) {
        Color color = ColorPicker.pickColor();
        particlesToAdd.add(new Particle(x + GameConfig.BALL_RADIUS / 2.0, y, color));
    }

    public static void animateMultipleParticles(List<Particle> particlesToAdd, double x, double y) {
        Color color = ColorPicker.pickColor();
        for (int i = 0; i < 25; i++) {
            particlesToAdd.add(new Particle(x + BLOCK_WIDTH / 2.0, y + BLOCK_HEIGHT / 2.0, color));
        }
    }
}