package game.core.entities.paddle;

import game.core.entities.ball.Ball;

public enum PaddleEffects {
    SLOW_PADDLE {
        @Override
        public void apply(Paddle paddle) {
            paddle.updateMovingSpeed(0.75);
        }
    },
    FAST_PADDLE {
        @Override
        public void apply(Paddle paddle) {
            paddle.updateMovingSpeed(1.5);
        }
    },
    SHORT_PADDLE {
        @Override
        public void apply(Paddle paddle) {
            paddle.updatePaddleWidth(50);
        }
    },
    LONG_PADDLE {
        @Override
        public void apply(Paddle paddle) {
            paddle.updatePaddleWidth(200);
        }
    };

    public abstract void apply(Paddle paddle);
}
