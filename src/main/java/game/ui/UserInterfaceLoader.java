package game.ui;

import game.GameConfig;
import game.core.entities.MyVector;
import game.core.entities.paddle.Paddle;
import game.core.entities.Player;
import game.core.entities.ball.Ball;
import game.core.logic.CollisionHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;

public class UserInterfaceLoader extends Application {
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root, GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT, GameConfig.BACKGROUND_COLOR);

        // Canvas + GC
        Canvas canvas = new Canvas(GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);

        // Icon (assert kann in Produktion deaktiviert sein -> lieber if)
        URL url = getClass().getResource("/icon.png");
        if (url != null) {
            Image icon = new Image(url.toString());
            primaryStage.getIcons().add(icon);
        }

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlockBuster");
        primaryStage.show();

        // Entities
        Ball ball = new Ball(new MyVector(100, 100), new MyVector(4, -4));
        // Paddle-Mittelpunkt: x = Mitte, y = z. B. 40 px über dem Boden
        Paddle paddle = new Paddle(GameConfig.FRAME_WIDTH / 2);

        // Steuerung (einfach: schrittweise Bewegung bei KeyPress)
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT -> paddle.moveLeft();
                case RIGHT -> paddle.moveRight();
            }
        });

        // Game Loop starten
        startGameLoop(gc, ball, paddle, canvas);
    }


    private void startGameLoop(GraphicsContext gc, Ball ball, Paddle paddle, Canvas canvas) {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update
                ball.move();
                CollisionHandler.checkForPaddleCollision(ball, paddle);
                // (Optional) Wände abprallen lassen:
                CollisionHandler.checkEdgeCollision(ball, new Player("p1"));

                // Render
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                paddle.render(gc);
                ball.render(gc);
            }
        }.start();
    }
}