package game.core.controller;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class InputController {
    private boolean moveLeftPressed = false;
    private boolean moveRightPressed = false;

    private boolean gameStarted = false;
    private final StringBuilder nameInput = new StringBuilder();

    private boolean spaceRequested = false;
    private boolean pauseToggled = false;

    public void setGameState(boolean started) {
        this.gameStarted = started;
    }

    public void handleKeyPress(KeyEvent e) {
        if (!gameStarted) {
            if (e.getCode().isLetterKey() || e.getCode().isDigitKey()) {
                if (nameInput.length() < 12) nameInput.append(e.getText().toUpperCase());
            } else if (e.getCode() == KeyCode.BACK_SPACE && !nameInput.isEmpty()) {
                nameInput.deleteCharAt(nameInput.length() - 1);
            }
        }
        if (nameInput.isEmpty()) nameInput.append("PLAYER1");
        System.out.println("CURRENT PLAYER: " + nameInput);
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
    public String getNameInput() { return nameInput.toString(); }

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

