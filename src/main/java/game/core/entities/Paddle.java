package game.core.entities;

import game.GameConfig;

public class Paddle {
    private final static int Y_POS = GameConfig.FRAME_HEIGHT - 10;
    private int xPos;

    public Paddle(int xPos) {
        this.xPos = xPos;
    }

    public int getX() {
        return xPos;
    }

    public void updateXPos(int xPos) {
        this.xPos = xPos;
    }

}
