package app.controller;

import app.Main;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerUtility {
    private static int H_SIZE;
    private static int V_SIZE;

    static {
        Screen screen = Screen.getPrimary();
        H_SIZE = (int) screen.getBounds().getWidth();
        V_SIZE = (int) screen.getBounds().getHeight();
    }
    public static Scene switchSceneTo(Stage stage,String PathToFXML) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(PathToFXML));
        Scene scene = new Scene(fxmlLoader.load(), H_SIZE, V_SIZE);
        stage.setTitle("Space Travel");
        stage.setScene(scene);
        stage.show();

        return scene;
    }

    // Switches the scene after an event is triggered e.g. mouse is clicked (on button) or key is pressed
    public static Scene switchSceneOnEvent(Event event, String PathToFXML) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        return switchSceneTo(stage, PathToFXML);
    }

    public static void setHSize(int hSize) {
        H_SIZE = hSize;
    }

    public static void setVSize(int vSize) {
        V_SIZE = vSize;
    }
}
