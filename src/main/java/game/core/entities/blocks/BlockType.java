package game.core.entities.blocks;

public enum BlockType {
    STRONG(0.07),
    EXTRA_BALL(0.03),
    EFFECT(0.10),
    STANDARD(0.80);

    private final double probability;

    BlockType(double probability) {
        this.probability = probability;
    }

    public double getProbability() { return probability; }
}
