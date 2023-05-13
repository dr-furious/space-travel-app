package app;

import app.controller.ControllerUtility;
import app.model.SystemAdministration;
import app.model.Utility;
import app.model.users.UserType;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ControllerUtility.switchSceneTo(stage, "continue-as.fxml");
    }

    public static void main(String[] args) {
        launch();
    }
}