package game.core.entities.blocks;

public enum BlockType {
    STRONG(0.08),
    EXTRA_BALL(0.05),
    EFFECT(0.20),
    STANDARD(0.67);

    private final double probability;

    BlockType(double probability) {
        this.probability = probability;
    }

    public double getProbability() { return probability; }
}
