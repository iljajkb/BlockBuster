package game.core.entities.ball;

public enum Effects {
    HIGH_DAMAGE {
        @Override
        public void apply(Ball ball) {
            ball.setDamage(ball.getCurrentDamage() * 2);
        }
    },
    SLOW_BALL {
        @Override
        public void apply(Ball ball) {
            ball.setVelocity(ball.getVelocity().scale(0.5));
        }
    },
    NONE {
        @Override
        public void apply(Ball ball) {
            // no effect
        }
    };

    public abstract void apply(Ball ball);
}
