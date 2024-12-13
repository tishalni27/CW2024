package com.example.demo.LevelChallenge;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.*;
import java.util.*;

/**
 * The Scoreboard class manages the display and storage of the top 3 scores for the game.
 * It loads scores from a file, saves updated scores, and handles the visual display of the scoreboard.
 */
public class Scoreboard {

    /** List to store the top scores of the game. */
    private List<Integer> topScores;

    /** Text element for displaying the scoreboard on the screen. */
    private final Text scoreboardText;

    /** ImageView element for displaying the background image of the scoreboard. */
    private final ImageView backgroundImage;

    /** Rectangle for the black background behind the scoreboard. */
    private final Rectangle blackBackground;

    /** Path to the file where scores are saved. */
    private final String filename = "src/main/resources/scores.txt";

    /**
     * Constructor to initialize the Scoreboard with its position and load scores from file.
     *
     * @param xPosition The X position for placing the scoreboard (not currently used but could be for further customization).
     * @param yPosition The Y position for placing the scoreboard.
     */
    public Scoreboard(double xPosition, double yPosition) {
        // Load scores from the file
        topScores = loadScores();

        // Create and set up the scoreboard text
        scoreboardText = new Text(725, 320, getScoreboardText());
        scoreboardText.setFill(Color.WHITE); // Set the text color to white
        scoreboardText.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        scoreboardText.toFront(); // Ensure text is in front

        // Create and set up the black background
        blackBackground = new Rectangle(1100, 900); // Dimensions of the background
        blackBackground.setFill(Color.BLACK); // Set color to black
        blackBackground.setLayoutX(200);
        blackBackground.setLayoutY(0);
        blackBackground.setVisible(true);

        // Create and set up the background image
        backgroundImage = new ImageView(new Image(getClass().getResource("/com/example/demo/images/scoreBoardScreen.png").toExternalForm()));
        backgroundImage.setFitWidth(900);
        backgroundImage.setFitHeight(500);
        backgroundImage.setLayoutX(300);
        backgroundImage.setLayoutY(yPosition);
        backgroundImage.setVisible(true);
    }

    /**
     * Loads the scores from the file specified by {@code filename}.
     *
     * @return A list of integers representing the top scores.
     */
    private List<Integer> loadScores() {
        List<Integer> scores = new ArrayList<>();
        try {
            File file = new File(filename);
            System.out.println("Looking for file at: " + file.getAbsolutePath()); // Debugging line
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    try {
                        scores.add(Integer.parseInt(line.trim()));
                    } catch (NumberFormatException e) {
                        // Skip invalid entries
                    }
                }
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores.isEmpty() ? new ArrayList<>(Arrays.asList(0, 0, 0)) : scores;
    }

    /**
     * Saves the current top scores to the file specified by {@code filename}.
     */
    private void saveScores() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (Integer score : topScores) {
                writer.write(score.toString());
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new score, updates the top 3 scores, and saves them to the file.
     *
     * @param newScore The new score to be added to the scoreboard.
     */
    public void addScore(int newScore) {
        topScores.add(newScore);
        Collections.sort(topScores, Collections.reverseOrder()); // Sort in descending order
        if (topScores.size() > 3) {
            topScores = topScores.subList(0, 3); // Keep only the top 3 scores
        }
        saveScores(); // Save updated scores to the file
        updateScoreboardText(); // Update the displayed scoreboard
    }

    /**
     * Updates the scoreboard text to display the current top 3 scores.
     */
    private void updateScoreboardText() {
        scoreboardText.setText(getScoreboardText());
    }

    /**
     * Formats the top 3 scores as a string for display on the scoreboard.
     *
     * @return A string representing the formatted top 3 scores.
     */
    private String getScoreboardText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < topScores.size(); i++) {
            sb.append(i + 1).append(". ").append(topScores.get(i)).append("\n\n");
        }
        return sb.toString();
    }

    /**
     * Displays the scoreboard on the specified root group.
     *
     * @param root The root group to which the scoreboard elements are added.
     */
    public void displayScoreboard(Group root) {
        root.getChildren().add(blackBackground); // Add the background rectangle
        root.getChildren().add(backgroundImage); // Add the background image
        root.getChildren().add(scoreboardText);  // Add the scoreboard text
        bringToFront(); // Ensure the text is on top
    }

    /**
     * Shows the scoreboard by making the black background and background image visible.
     */
    public void showScoreboard() {
        blackBackground.setVisible(true);
        backgroundImage.setVisible(true);
    }

    /**
     * Brings the scoreboard text to the front of the display.
     */
    public void bringToFront() {
        scoreboardText.toFront();
    }
}
