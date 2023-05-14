package app;

import app.controller.ControllerUtility;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ControllerUtility.switchSceneTo(stage, "continue-as.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}