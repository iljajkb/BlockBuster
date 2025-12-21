package game.ui;

import game.GameConfig;
import game.core.controller.GameController;
import game.core.controller.InputController;
import game.core.entities.MyVector;
import game.core.entities.paddle.Paddle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import game.core.logic.CollisionHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;

public class UserInterfaceLoader extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        int frameHeight = GameConfig.FRAME_HEIGHT;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double maxWindowHeight = screenBounds.getHeight();

        System.out.println("max Window Height: " + maxWindowHeight);

        // e.g.: Macbook Pro 14" has maxHeight of 861, so standard height of 900 won't work here
        // -> scaleY < 0 this means that window is to small and has to be scaled
        double scaleY = maxWindowHeight / GameConfig.FRAME_HEIGHT;

        if (scaleY < 1.0) {
            frameHeight = (int) (GameConfig.FRAME_HEIGHT * scaleY);
        }
        Scene scene = new Scene(root, GameConfig.FRAME_WIDTH, frameHeight, GameConfig.BACKGROUND_COLOR);

        Canvas canvas = new Canvas(GameConfig.FRAME_WIDTH, frameHeight);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        URL url = getClass().getResource("/icon.png");
        if (url != null) {
            Image icon = new Image(url.toString());
            primaryStage.getIcons().add(icon);
        }

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlockBuster");
        primaryStage.show();

        InputController inputController = new InputController();
        GameController gameController = new GameController(gc, canvas, frameHeight, inputController);

        scene.setOnKeyPressed(inputController::handleKeyPress);
        scene.setOnKeyReleased(inputController::handleKeyRelease);

        gameController.startGameLoop();
    }
}