package game.ui;

import game.GameConfig;
import game.core.entities.ActiveEffect;
import game.core.entities.EffectController;
import game.core.entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Map;

public class RenderUIController {

    private final EffectController effectController;
    private final Player p1;
    private String playerName = "PLAYER01";
    private double gridOffset = 0;
    private final Font biggerArcadeFont;
    private final Font smallerArcadeFont;

    // cache for score and lives
    private int lastScore = -1;
    private String scoreTextCache = "";
    private int lastLives = -1;
    private String livesTextCache = "";

    public RenderUIController(EffectController effectController, Player player) {
        this.effectController = effectController;
        this.p1 = player;
        this.biggerArcadeFont = Font.loadFont(getClass().getResourceAsStream("/fonts/press_start_2p.ttf"), 18);
        this.smallerArcadeFont = Font.loadFont(getClass().getResourceAsStream("/fonts/press_start_2p.ttf"), 12);
    }

    private void drawCenteredText(GraphicsContext gc, String text, double y, boolean big) {
        if (big) {
            gc.setFont(biggerArcadeFont);
        } else {
            gc.setFont(smallerArcadeFont);
        }

        javafx.scene.text.Text helper = new javafx.scene.text.Text(text);
        helper.setFont(gc.getFont());
        double textWidth = helper.getLayoutBounds().getWidth();
        double x = (GameConfig.FRAME_WIDTH - textWidth) / 2;
        gc.fillText(text, x, y);
    }

    public void drawPauseScreen(GraphicsContext gc) {
        drawCenteredText(gc, "PAUSED\nPRESS ESC TO CONTINUE", GameConfig.FRAME_HEIGHT / 2.0, true);
    }

    public void updatePlayerName(String newPlayerName) {
        this.playerName = newPlayerName;
    }

    public void renderStartScreen(GraphicsContext gc, String playerNameInput, List<Map.Entry<String, Integer>> topFive) {
        gc.setFill(Color.WHITE);
        drawCenteredText(gc, "PLAYER: " + playerNameInput + "_", GameConfig.FRAME_HEIGHT / 2.0 - 150, true);
        drawCenteredText(gc, "Press SPACE to start the Game", GameConfig.FRAME_HEIGHT / 2.0 - 100, false);

        gc.setFill(Color.GOLD);
        drawCenteredText(gc, "--- HALL OF FAME ---", GameConfig.FRAME_HEIGHT / 2.0, false);

        gc.setFill(Color.LIGHTGRAY);
        double startY = GameConfig.FRAME_HEIGHT / 2.0 + 40;
        double lineHeight = 25;

        if (topFive == null || topFive.isEmpty()) {
            drawCenteredText(gc, "No highscores yet. Be the first!", startY, false);
        } else {
            for (int i = 0; i < topFive.size(); i++) {
                Map.Entry<String, Integer> entry = topFive.get(i);

                String rank = (i + 1) + ". ";
                String name = entry.getKey();
                String score = String.valueOf(entry.getValue());

                String leaderBoardLine = String.format("%-3s %-12s %10s", rank, name, score);

                drawCenteredText(gc, leaderBoardLine, startY + (i * lineHeight), false);
            }
        }

    }

    public void renderGameOver(GraphicsContext gc, int highscore, boolean newHighscore) {
        gc.setFill(Color.DARKRED);
        drawCenteredText(gc, "GAME OVER", GameConfig.FRAME_HEIGHT / 2.0 - 120, true);
        gc.setFill(Color.WHITE);
        String highScoreText = "HIGHSCORE: ";
        if (newHighscore) highScoreText = "NEW " + highScoreText;

        drawCenteredText(gc, "Player: " + playerName, GameConfig.FRAME_HEIGHT / 2.0, false);
        drawCenteredText(gc, "SCORE: " + p1.getScore(), GameConfig.FRAME_HEIGHT / 2.0 + 30, false);
        drawCenteredText(gc, highScoreText + highscore, GameConfig.FRAME_HEIGHT / 2.0 + 60, false);
        drawCenteredText(gc, "Press space to play again", GameConfig.FRAME_HEIGHT / 2.0 + 90, false);
    }

    public void renderEffectText(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.setFont(smallerArcadeFont);
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
        if (score != lastScore) {
            lastScore = score;
            scoreTextCache = "SCORE: " + String.format("%06d", score);
        }
        gc.setFill(Color.WHITE);
        gc.setFont(smallerArcadeFont);
        gc.fillText(scoreTextCache, GameConfig.FRAME_WIDTH - 175, 40);
    }

    public void renderLives(GraphicsContext gc, int lives) {
        double heartX = 20;
        double heartY = 20;
        double heartSize = 20;

        gc.setFill(Color.RED);
        drawHeart(gc, heartX, heartY, heartSize);

        if (lives != lastLives) {
            lastLives = lives;
            livesTextCache = "x " + lives;
        }

        gc.setFill(Color.WHITE);
        gc.setFont(smallerArcadeFont);
        gc.fillText(livesTextCache, heartX + heartSize + 10, heartY + heartSize / 1.3);
    }


    public void renderBackground(GraphicsContext gc, int currentLevel) {
        renderVignette(gc);
        renderRetroGrid(gc, currentLevel);
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

    private void renderRetroGrid(GraphicsContext gc, int currentLevel) {
        gc.setStroke(Color.web("#1a1a1a"));
        gc.setLineWidth(1.0);

        double spacing = 40;
        // moves faster with level progress
        gridOffset = (gridOffset + (currentLevel * 0.75)) % spacing;

        for (double y = gridOffset; y < GameConfig.FRAME_HEIGHT; y += spacing) {
            gc.strokeLine(0, y, GameConfig.FRAME_WIDTH, y);
        }

        for (double x = 0; x < GameConfig.FRAME_WIDTH; x += spacing) {
            gc.strokeLine(x, 0, x, GameConfig.FRAME_HEIGHT);
        }
    }

    private void renderVignette(GraphicsContext gc) {

        RadialGradient gradient = new RadialGradient(
                0, 0,
                GameConfig.FRAME_WIDTH / 2.0, GameConfig.FRAME_HEIGHT / 2.0,
                GameConfig.FRAME_WIDTH,
                false,
                CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#002424")),
                new Stop(1, Color.BLACK)
        );

        gc.setFill(gradient);
        gc.fillRect(0, 0, GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT);
    }
}
