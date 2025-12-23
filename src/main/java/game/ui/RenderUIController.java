package game.ui;

import game.GameConfig;
import game.core.entities.ActiveEffect;
import game.core.entities.EffectController;
import game.core.entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Map;

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

    private Font getArcadeFont(int size) {
        return Font.loadFont(getClass().getResourceAsStream("/fonts/press_start_2p.ttf"), size);
    }

    private void drawCenteredText(GraphicsContext gc, String text, double y, double fontSize) {
        gc.setFont(getArcadeFont(16));
        javafx.scene.text.Text helper = new javafx.scene.text.Text(text);
        helper.setFont(gc.getFont());
        double textWidth = helper.getLayoutBounds().getWidth();
        double x = (GameConfig.FRAME_WIDTH - textWidth) / 2;
        gc.fillText(text, x, y);
    }

    public void drawPauseScreen(GraphicsContext gc) {
        drawCenteredText(gc, "PAUSED\nPRESS ESC TO CONTINUE", GameConfig.FRAME_HEIGHT / 2.0, 20);
    }

    public void updatePlayerName(String newPlayerName) {
        this.playerName = newPlayerName;
    }

    public void renderStartScreen(GraphicsContext gc, String playerNameInput, List<Map.Entry<String, Integer>> topFive) {
        gc.setFill(Color.WHITE);
        drawCenteredText(gc, "PLAYER: " + playerNameInput + "_", GameConfig.FRAME_HEIGHT / 2.0 - 150, 30);
        drawCenteredText(gc, "Press SPACE to start the Game", GameConfig.FRAME_HEIGHT / 2.0 - 100, 20);

        gc.setFill(Color.GOLD);
        drawCenteredText(gc, "--- HALL OF FAME ---", GameConfig.FRAME_HEIGHT / 2.0, 25);

        gc.setFill(Color.LIGHTGRAY);
        double startY = GameConfig.FRAME_HEIGHT / 2.0 + 40;
        double lineHeight = 25;

        if (topFive == null || topFive.isEmpty()) {
            drawCenteredText(gc, "No highscores yet. Be the first!", startY, 15);
        } else {
            for (int i = 0; i < topFive.size(); i++) {
                Map.Entry<String, Integer> entry = topFive.get(i);

                String rank = (i + 1) + ". ";
                String name = entry.getKey();
                String score = String.valueOf(entry.getValue());

                String leaderBoardLine = String.format("%-3s %-12s %10s", rank, name, score);

                drawCenteredText(gc, leaderBoardLine, startY + (i * lineHeight), 18);
            }
        }

    }

    public void renderGameOver(GraphicsContext gc, int highscore, boolean newHighscore) {
        gc.setFill(Color.DARKRED);
        drawCenteredText(gc, "GAME OVER", GameConfig.FRAME_HEIGHT / 2.0 - 120, 40);
        gc.setFill(Color.WHITE);
        String highScoreText = "HIGHSCORE: ";
        if (newHighscore) highScoreText = "NEW " + highScoreText;

        drawCenteredText(gc, "Player: " + playerName, GameConfig.FRAME_HEIGHT / 2.0, 20);
        drawCenteredText(gc, "SCORE: " + p1.getScore(), GameConfig.FRAME_HEIGHT / 2.0 + 30, 20);
        drawCenteredText(gc, highScoreText + highscore, GameConfig.FRAME_HEIGHT / 2.0 + 60, 20);
        drawCenteredText(gc, "Press space to play again", GameConfig.FRAME_HEIGHT / 2.0 + 90, 20);
    }

    public void renderEffectText(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(getArcadeFont(12));
        List<ActiveEffect> list = effectController.getActiveEffects();
        if (list.isEmpty()) {
            return;
        }
        StringBuilder effectText = new StringBuilder("EFFECT ACTIVATED:\n\n");
        for (ActiveEffect value : list) {
            effectText.append(value.getActiveEffect().name());
            effectText.append("\n");
        }
        gc.fillText(effectText.toString(), 30 , 120);
    }

    public void renderScore(GraphicsContext gc, int score) {
        gc.setFill(Color.WHITE);
        gc.setFont(getArcadeFont(12));
        gc.fillText("SCORE | " + score, GameConfig.FRAME_WIDTH - 175, 35);
    }

    public void renderLives(GraphicsContext gc, int lives) {
        double heartX = 20;
        double heartY = 20;
        double heartSize = 20;

        gc.setFill(Color.RED);
        drawHeart(gc, heartX, heartY, heartSize);

        gc.setFill(Color.WHITE);
        gc.setFont(getArcadeFont(12));
        gc.fillText("x " + lives, heartX + heartSize + 10, heartY + (heartY * 0.5 + heartY * 0.25));
    }

    private void drawHeart(GraphicsContext gc, double x, double y, double size) {
        gc.beginPath();

        gc.moveTo(x + size / 2, y + size);

        gc.bezierCurveTo(x, y + size / 2,
                x, y,
                x + size / 2, y + size / 4);

        gc.bezierCurveTo(x + size, y,
                x + size, y + size / 2,
                x + size / 2, y + size);

        gc.fill();
        gc.closePath();
    }
}
