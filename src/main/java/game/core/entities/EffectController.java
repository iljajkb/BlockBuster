package game.core.entities;

import game.core.controller.GameController;
import game.core.entities.ball.Ball;
import game.core.entities.paddle.Paddle;

import java.util.ArrayList;
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

    public void handleCollision(Ball effectBall) {
        if (effectBall.isEffect()) {
            triggerEffect(effectBall.getEffect());
        }
    }

    public void update() {
        for (ActiveEffect active : activeEffects) {
            if (active.isExpired()) {
                applyEffect(active.getActiveEffect(), false);
                activeEffects.remove(active);
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


    // --- deprecated ---

    @Deprecated
    public void handleEffects(Ball effectBall, Paddle paddle, Ball mainBall) {
        if (!effectBall.isEffect()) {
            return;
        }

        Effects ballEffect = effectBall.getEffect();

        switch(ballEffect) {
            case SLOW_PADDLE -> {
                paddle.updateMovingSpeed(0.5);
                triggerEffect(Effects.SLOW_PADDLE);
            }
            case FAST_PADDLE -> {
                paddle.updateMovingSpeed(1.5);
                triggerEffect(Effects.FAST_PADDLE);
            }
            case FAST_BALL -> {
                mainBall.updateSpeed(1.5);
                triggerEffect(Effects.FAST_BALL);
            }
            case SLOW_BALL -> {
                mainBall.updateSpeed(0.5);
                triggerEffect(Effects.SLOW_BALL);
            }
        }
    }
}
