package game.core.entities;

public class ActiveEffect {
    private final Effects name;
    private final long expirationTime;

    public ActiveEffect(Effects name, int durationSeconds) {
        this.name = name;
        this.expirationTime = System.currentTimeMillis() + (durationSeconds * 1000L);
    }

    public Effects getActiveEffect() { return name; }

    public boolean isExpired() {
        return System.currentTimeMillis() > expirationTime;
    }
}
