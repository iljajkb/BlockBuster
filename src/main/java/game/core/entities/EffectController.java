package game.core.entities;

import game.core.controller.GameController;
import game.core.entities.ball.Ball;
import game.core.entities.paddle.Paddle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class EffectController {

    List<ActiveEffect> activeEffects = new ArrayList<>();

    private final Paddle paddle;
    private final Ball mainBall;

    public EffectController(Paddle paddle, Ball mainBall) {
        this.paddle = paddle;
        this.mainBall = mainBall;
    }

    public static Effects getRandomEffect() {
        Random RANDOM = new Random();
        Effects[] effects = Effects.values();
        return effects[RANDOM.nextInt(effects.length)];
    }

    public List<ActiveEffect> getActiveEffects() {
        return activeEffects;
    }

    public void triggerEffect(Effects effectName) {
        applyEffect(effectName, true);
        activeEffects.add(new ActiveEffect(effectName, 10));
    }

    public void update() {
        Iterator<ActiveEffect> it = activeEffects.iterator();
        while (it.hasNext()) {
            ActiveEffect active = it.next();
            if (active.isExpired()) {
                applyEffect(active.getActiveEffect(), false);
                it.remove();
            }
        }
    }

    private double getFactorForEffect(Effects effect) {
        return switch (effect) {
            case SLOW_PADDLE, SLOW_BALL -> 0.5;
            case FAST_PADDLE, FAST_BALL -> 1.5;
        };
    }

    private void applyEffect(Effects effect, boolean activate) {
        double factor = 1.0;
        if (activate) {
            factor = getFactorForEffect(effect);
        } else {
            factor = getReverseFactor(effect);
        }

        switch (effect) {
            case SLOW_PADDLE, FAST_PADDLE -> paddle.updateMovingSpeed(factor);
            case SLOW_BALL, FAST_BALL -> mainBall.updateSpeed(factor);
        }
    }

    private double getReverseFactor(Effects effect) {
        return switch (effect) {
            case SLOW_PADDLE, SLOW_BALL -> 2.0;
            case FAST_PADDLE, FAST_BALL -> 1.0 / 1.5;
        };
    }
}
