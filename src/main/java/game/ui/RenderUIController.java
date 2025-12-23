package game.ui;

import game.GameConfig;
import game.core.entities.ActiveEffect;
import game.core.entities.EffectController;
import game.core.entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;

public class RenderUIController {

    private EffectController effectController;
    private final GraphicsContext gc;
    private Player p1;
    private String playerName = "PLAYER01";

    public RenderUIController(GraphicsContext gc, EffectController effectController, Player player) {
        this.gc = gc;
        this.effectController = effectController;
        this.p1 = player;
    }

    public void drawCenteredText(GraphicsContext gc, String text, double y, double fontSize) {
        gc.setFont(new Font(fontSize));
        javafx.scene.text.Text helper = new javafx.scene.text.Text(text);
        helper.setFont(gc.getFont());
        double textWidth = helper.getLayoutBounds().getWidth();
        double x = (GameConfig.FRAME_WIDTH - textWidth) / 2;
        gc.fillText(text, x, y);
    }

    public void updatePlayerName(String newPlayerName) {
        this.playerName = newPlayerName;
    }

    public void renderStartScreen(GraphicsContext gc, String playerNameInput) {
        drawCenteredText(gc, "Put in your name: " + playerNameInput, GameConfig.FRAME_HEIGHT / 2.0, 20);
        drawCenteredText(gc, "Press SPACE to start the Game", GameConfig.FRAME_HEIGHT / 2.0 + 50, 20);
    }

    public void renderGameOver(GraphicsContext gc, int highscore) {
        gc.setFill(Color.DARKRED);
        drawCenteredText(gc, "GAME OVER", GameConfig.FRAME_HEIGHT / 2.0 - 120, 40);
        gc.setFill(Color.WHITE);
        drawCenteredText(gc, "Player: " + playerName, GameConfig.FRAME_HEIGHT / 2.0, 20);
        drawCenteredText(gc, "SCORE: " + p1.getScore(), GameConfig.FRAME_HEIGHT / 2.0 + 30, 20);
        drawCenteredText(gc, "HIGHSCORE: " + highscore, GameConfig.FRAME_HEIGHT / 2.0 + 60, 20);
        drawCenteredText(gc, "Press space to play again", GameConfig.FRAME_HEIGHT / 2.0 + 90, 20);
    }

    public void renderEffectText(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(new Font(20));
        List<ActiveEffect> list = effectController.getActiveEffects();
        if (list.isEmpty()) {
            return;
        }
        StringBuilder effectText = new StringBuilder("EFFECT ACTIVATED:\n");
        for (ActiveEffect value : list) {
            effectText.append(value.getActiveEffect().name());
            effectText.append("\n");
        }
        gc.fillText(effectText.toString(), 30 , 120);
    }
}
