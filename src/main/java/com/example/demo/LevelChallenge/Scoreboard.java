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
public class Scoreboard {
    private List<Integer> topScores;
    private final Text scoreboardText;
    private final ImageView backgroundImage;
    private final Rectangle blackBackground;
    private final String filename = "src/main/resources/scores.txt"; // File where scores are saved
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
        blackBackground.setLayoutX(200);//200
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

    // Method to add a new score and update the top 3 scores
    public void addScore(int newScore) {
        topScores.add(newScore);
        Collections.sort(topScores, Collections.reverseOrder()); // Sort in descending order
        if (topScores.size() > 3) {
            topScores = topScores.subList(0, 3); // Keep only the top 3 scores
        }
        saveScores(); // Save updated scores to the file
        updateScoreboardText(); // Update the displayed scoreboard
    }

    // Method to generate the display text for the scoreboard
    private void updateScoreboardText() {
        scoreboardText.setText(getScoreboardText());
    }

    // Method to format the top 3 scores as a string for display
    private String getScoreboardText() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < topScores.size(); i++) {
            sb.append(i + 1).append(". ").append(topScores.get(i)).append("\n\n");
        }
        return sb.toString();
    }
    public void displayScoreboard(Group root) {
        root.getChildren().add(blackBackground); // Add the background image
        root.getChildren().add(backgroundImage); // Add the background image
        root.getChildren().add(scoreboardText);  // Add the scoreboard text
        bringToFront(); // Ensure the text is on top
    }

    public void showScoreboard() {
        blackBackground.setVisible(true);
        backgroundImage.setVisible(true);
    }
    public void bringToFront() {
        scoreboardText.toFront();
    }

}
