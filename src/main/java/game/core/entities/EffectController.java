package game.core.entities;

import game.core.controller.GameController;
import game.core.entities.ball.Ball;
import game.core.entities.paddle.Paddle;

import java.util.Random;

public class EffectController {

    public static Effects getRandomEffect() {
        Random RANDOM = new Random();
        Effects[] effects = Effects.values();
        return effects[RANDOM.nextInt(effects.length)];
    }

    public static void handleEffects(Ball effectBall, Paddle paddle, Ball mainBall) {
        if (!effectBall.isEffect()) {
            return;
        }

        Effects ballEffect = effectBall.getEffect();

        switch(ballEffect) {
            case SLOW_PADDLE -> {
                paddle.updateMovingSpeed(0.5);
                GameController.triggerEffect("SLOW PADDLE");
            }
            case FAST_PADDLE -> {
                paddle.updateMovingSpeed(1.5);
                GameController.triggerEffect("FAST PADDLE");
            }
            case FAST_BALL -> {
                mainBall.updateSpeed(1.5);
                GameController.triggerEffect("FAST BALL");
            }
            case SLOW_BALL -> {
                mainBall.updateSpeed(0.5);
                GameController.triggerEffect("SLOW BALL");
            }
        }
    }
}
