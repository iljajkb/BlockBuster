package game.core.controller;

import javafx.scene.input.KeyEvent;

public class InputController {
    private boolean moveLeftPressed = false;
    private boolean moveRightPressed = false;

    private boolean spaceRequested = false;
    private boolean pauseToggled = false;

    public void handleKeyPress(KeyEvent e) {
        switch (e.getCode()) {
            case LEFT -> moveLeftPressed = true;
            case RIGHT -> moveRightPressed = true;
            case SPACE, ENTER -> spaceRequested = true;
            case ESCAPE -> pauseToggled = true;
        }
    }

    public void handleKeyRelease(KeyEvent e) {
        switch (e.getCode()) {
            case LEFT -> moveLeftPressed = false;
            case RIGHT -> moveRightPressed = false;
        }
    }

    public boolean isMoveLeft() { return moveLeftPressed; }
    public boolean isMoveRight() { return moveRightPressed; }

    public boolean consumeSpaceRequest() {
        if (spaceRequested) {
            spaceRequested = false;
            return true;
        }
        return false;
    }

    public boolean consumePauseToggle() {
        if (pauseToggled) {
            pauseToggled = false;
            return true;
        }
        return false;
    }
}

