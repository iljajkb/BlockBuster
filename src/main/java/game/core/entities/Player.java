package game.core.entities;

public class Player {
    private int lives = 3;
    private int score;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public void loseLife() {
        lives--;
    }

    public int getLives() {
        return lives;
    }
}
