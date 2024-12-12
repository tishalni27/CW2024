module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.Level1 to javafx.fxml;
    opens com.example.demo.Level2 to javafx.fxml;
    opens com.example.demo.Levels to javafx.fxml;
    opens com.example.demo.Screen to javafx.fxml;
    opens com.example.demo.User to javafx.fxml;
    opens com.example.demo.LevelCommonElements to javafx.fxml;
    opens com.example.demo.Level1AndChallenge to javafx.fxml;
    opens com.example.demo.Level3 to javafx.fxml;
    opens com.example.demo.LevelChallenge to javafx.fxml;
    opens com.example.demo.LevelCommonElements.Actor to javafx.fxml;
    opens com.example.demo.LevelCommonElements.visuals to javafx.fxml;
    opens com.example.demo.Screen.Pause to javafx.fxml;
    opens com.example.demo.Level2.EnemyLevelTwo to javafx.fxml;
}