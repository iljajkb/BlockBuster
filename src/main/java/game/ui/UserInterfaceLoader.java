package game.ui;

import game.GameConfig;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;

public class UserInterfaceLoader extends Application {
    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Scene scene = new Scene(root, GameConfig.FRAME_WIDTH, GameConfig.FRAME_HEIGHT, Color.DARKBLUE);

        // Icon
        URL url = getClass().getResource("/icon.png");
        System.out.println(url);
        assert url != null;
        Image icon = new Image(url.toString());
        primaryStage.getIcons().add(icon);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("BlockBuster");
        primaryStage.show();
    }
}
