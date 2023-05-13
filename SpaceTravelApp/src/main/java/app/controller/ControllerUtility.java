package app.controller;

import app.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

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
        System.out.println("H_Size: " + H_SIZE + ", V_Size: " + V_SIZE);
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

    public static void disableAll(ArrayList<ButtonBase> controls) {
        for (ButtonBase control: controls) {
            control.setDisable(true);
        }
    }

    public static void enableAll(ArrayList<ButtonBase> controls) {
        for (ButtonBase control: controls) {
            control.setDisable(false);
        }
    }

    @FXML
    public static void informOnEvent(Event event, String info, int sizeX, int sizeY) {

        Node node = (Node) event.getSource();
        Stage primaryStage = (Stage) node.getScene().getWindow();
        Node root = primaryStage.getScene().getRoot();

        root.setDisable(true);

        // Create a new popup and add a message and button to it
        Popup popup = new Popup();
        VBox popupContent = new VBox();
        VBox messageContainer = new VBox();

        popupContent.setStyle("" +
                "-fx-alignment: bottom-center;" +
                "-fx-pref-height:" + sizeY +"px;" +
                "-fx-pref-width:" + sizeX +"px;" +
                "-fx-background-color: white;" +
                "-fx-background-radius: 8px;" +
                "-fx-wrap-text: true;" +
                "-fx-padding: 8px;" +
                "-fx-border-radius: 8px;" +
                "-fx-border-width: 2px;" +
                "-fx-border-color: #484848");

        messageContainer.setStyle("-fx-alignment: top-center;" +
                "-fx-pref-height:"+ sizeY + "px");

        Label message = new Label(info);
        message.setStyle("" +
                "-fx-wrap-text: true;" +
                "-fx-font-size: 18px");

        Button close =  new Button("Close");
        close.setStyle("" +
                "-fx-alignment: bottom-center;" +
                "-fx-pref-height: 50px;" +
                "-fx-pref-width: 150px;" +
                "-fx-font-size: 18px;" +
                "-fx-background-color: #d3d3d3;" +
                "-fx-background-radius: 4px;" +
                "-fx-border-radius: 4px;" +
                "-fx-border-color: #3883f3;" +
                "-fx-border-width: 2px;" +
                "-fx-cursor: hand");

        messageContainer.getChildren().add(message);
        popupContent.getChildren().addAll(messageContainer, close);
        popup.getContent().add(popupContent);

        // Set the location of the popup to be centered relative to the stage
        popup.setX((H_SIZE - sizeX) / 2.0);
        popup.setY((V_SIZE - sizeY) / 2.);


        // Show the popup
        popup.show(primaryStage);

        // Set the action for the button to hide the popup when clicked
        Button okButton = (Button) popupContent.getChildren().get(1);
        popup.setOnHidden(e -> root.setDisable(false));
        okButton.setOnAction(e -> popup.hide());
    }
}
