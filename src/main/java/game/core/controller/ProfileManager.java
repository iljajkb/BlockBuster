package game.core.controller;

import java.io.*;
import java.util.*;

public class ProfileManager {
    private static final String FILE_PATH = "highscores.dat";
    private Map<String, Integer> scores = new HashMap<>();

    public ProfileManager() {
        loadScores();
    }

    public void saveScore(String name, int score) {
        // only save with new score
        if (score > scores.getOrDefault(name, 0)) {
            scores.put(name, score);
            persist();
        }
    }

    private void persist() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(scores);
        } catch (IOException e) { e.printStackTrace(); }
        System.out.println("WROTE SCORES: " + scores + " to " + FILE_PATH + ".");
    }

    private void loadScores() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                scores = (Map<String, Integer>) ois.readObject();
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    public int findScoreByName(String name) {
        return scores.entrySet().stream()
                .filter(s -> s.getKey().equals(name))
                .mapToInt(Map.Entry::getValue)
                .limit(1)
                .sum();
    }

    public List<Map.Entry<String, Integer>> getTopTen() {
        return scores.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10)
                .toList();
    }
}
