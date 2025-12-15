package game.app;

import game.ui.UserInterfaceLoader;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;

import static javafx.application.Application.launch;

public class Main  {
    public static void main(String[] args) {
        Application.launch(UserInterfaceLoader.class, args);
    }
}