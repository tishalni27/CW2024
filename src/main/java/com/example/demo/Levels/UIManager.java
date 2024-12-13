package com.example.demo.Levels;

import javafx.scene.Group;
import javafx.scene.text.Text;
import com.example.demo.LevelChallenge.Scoreboard;

/**
 * UIManager handles the management and updating of the game's user interface elements
 * such as the score, kill count, and scoreboard.
 */
public class UIManager {
    private Text scoreText;
    private Text killCountText;
    private Scoreboard scoreboard;
    private int score;

    /**
     * Constructor that initializes the UI elements for the score, kill count, and scoreboard.
     *
     * @param root The root group where the UI elements will be added.
     */
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

    /**
     * Updates the score display.
     *
     * @param score The current score to display.
     */
    public void updateScore(int score) {
        this.score = score;
        scoreText.setText("Score: " + score);
    }

    /**
     * Updates the kill count display.
     *
     * @param killCount The current kill count to display.
     * @param targetKillCount The target kill count to show in the display.
     */
    public void updateKillCount(int killCount, int targetKillCount) {
        killCountText.setText("Kill Count : " + killCount + " / " + targetKillCount);
    }

    /**
     * Updates the scoreboard with the current score.
     *
     * @param score The score to add to the scoreboard.
     */
    public void updateScoreboard(int score) {
        scoreboard.addScore(score);
        scoreboard.displayScoreboard(null);  // Updates the display on the root group
    }

    /**
     * Gets the current score.
     *
     * @return The current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the score text display.
     *
     * @return The score text element.
     */
    public Text getScoreText() {
        return scoreText;
    }

    /**
     * Gets the kill count text display.
     *
     * @return The kill count text element.
     */
    public Text getKillCountText() {
        return killCountText;
    }
}
