package game.core.entities;

import game.core.controller.GameController;
import javafx.util.Duration;

public interface GameEffect {
    void apply(Player player, GameController controller);
    void remove(Player player, GameController controller); // optional
    Duration getDuration();
    String getName(); // z.B. für UI-Anzeige
}