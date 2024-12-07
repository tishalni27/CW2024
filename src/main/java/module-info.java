module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo.controller;
    opens com.example.demo.Level1 to javafx.fxml;
    opens com.example.demo.Level3 to javafx.fxml;
    opens com.example.demo.Levels to javafx.fxml;
    opens com.example.demo.Screen to javafx.fxml;
    opens com.example.demo.User to javafx.fxml;
    opens com.example.demo.CommonElements to javafx.fxml;
}