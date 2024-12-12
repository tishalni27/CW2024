package com.example.demo.Levels;

import javafx.scene.Group;
import javafx.scene.text.Text;
import com.example.demo.LevelChallenge.Scoreboard;

public class UIManager {
    private Text scoreText;
    private Text killCountText;
    private Scoreboard scoreboard;
    private int score;

    public UIManager(Group root) {
        // Initialize the texts for score and kill count
        this.scoreText = new Text(20, 70, "Score: 0");
        this.killCountText = new Text(20, 40, "Kill Count : 0 / 10");
        this.scoreboard = new Scoreboard(20, 100);  // Position of the scoreboard (can adjust)
        this.score = 0;

        // Add the UI elements to the root group
        root.getChildren().addAll(scoreText, killCountText);
        scoreboard.displayScoreboard(root);  // Display the scoreboard
    }

    // Method to update the score
    public void updateScore(int score) {
        this.score = score;
        scoreText.setText("Score: " + score);
    }

    // Method to update the kill count
    public void updateKillCount(int killCount, int targetKillCount) {
        killCountText.setText("Kill Count : " + killCount + " / " + targetKillCount);
    }

    // Method to update the scoreboard with the current score
    public void updateScoreboard(int score) {
        scoreboard.addScore(score);
        scoreboard.displayScoreboard(null);  // Updates the display on the root group
    }

    // Accessor methods for score and kill count
    public int getScore() {
        return score;
    }

    public Text getScoreText() {
        return scoreText;
    }

    public Text getKillCountText() {
        return killCountText;
    }
}
