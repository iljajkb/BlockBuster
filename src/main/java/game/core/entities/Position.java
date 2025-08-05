package game.core.entities;

public record Position(int x, int y) {
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

