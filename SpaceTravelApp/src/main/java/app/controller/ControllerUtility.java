package app.controller;

import app.Main;
import app.model.journeys.Journey;
import app.model.journeys.JourneyState;
import app.model.users.Owner;
import app.model.users.Traveler;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Contains all utility functions for generating the content into the View, switching scenes and displaying messages (pop-ups)
 */
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

    @FXML
    public static void generateJourneyViewForGuide(Journey journey, VBox journeyList) {
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(639);
        gridPane.setPrefHeight(75);
        gridPane.setPrefWidth(639);
        gridPane.getStyleClass().add("journey-item");

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.SOMETIMES);
        column1.setMaxWidth(241);
        column1.setMinWidth(10);
        column1.setPrefWidth(236);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.SOMETIMES);
        column2.setMaxWidth(305);
        column2.setMinWidth(10);
        column2.setPrefWidth(160);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.SOMETIMES);
        column3.setMaxWidth(185);
        column3.setMinWidth(10);
        column3.setPrefWidth(80);

        ColumnConstraints column4 = new ColumnConstraints();
        column4.setHgrow(Priority.SOMETIMES);
        column4.setMaxWidth(116);
        column4.setMinWidth(10);
        column4.setPrefWidth(80);

        ColumnConstraints column5 = new ColumnConstraints();
        column5.setHgrow(Priority.SOMETIMES);
        column5.setMaxWidth(107);
        column5.setMinWidth(10);
        column5.setPrefWidth(80);

        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(67);
        row1.setPrefHeight(67);
        row1.setVgrow(Priority.SOMETIMES);

        gridPane.getRowConstraints().add(row1);

        Label label1 = new Label("Name: " + journey.getName());
        label1.setPrefHeight(86);
        label1.setPrefWidth(236);

        Label label2 = new Label("Price: " + journey.getPrice()+"$");
        label2.setPrefHeight(83);
        label2.setPrefWidth(160);
        GridPane.setConstraints(label2, 1, 0);

        Button button = new Button(journey.getJourneyState().name().toLowerCase());
        button.setDisable(true);
        button.setPrefHeight(91);
        button.setPrefWidth(80);
        if (journey.getJourneyState() == JourneyState.PENDING) {
            button.setStyle("-fx-background-color: #95adc4");
        } else if (journey.getJourneyState() == JourneyState.CANCELLED) {
            button.setStyle("-fx-background-color: #c49595");
        } else {
            button.setStyle("-fx-background-color: #9edc8e");
        }
        button.getStyleClass().add("state-btn");
        GridPane.setConstraints(button, 4, 0);

        gridPane.getChildren().addAll(label1, label2, button);
        journeyList.getChildren().add(gridPane);
    }
    @FXML
    public static void generateJourneyViewForOwner(Journey journey, VBox journeyList, boolean add, boolean remove, Owner owner, TabPane tabPane) {
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(639);
        gridPane.setPrefHeight(75);
        gridPane.setPrefWidth(639);
        gridPane.getStyleClass().add("journey-item");

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.SOMETIMES);
        column1.setMaxWidth(241);
        column1.setMinWidth(10);
        column1.setPrefWidth(236);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.SOMETIMES);
        column2.setMaxWidth(305);
        column2.setMinWidth(10);
        column2.setPrefWidth(160);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.SOMETIMES);
        column3.setMaxWidth(185);
        column3.setMinWidth(10);
        column3.setPrefWidth(80);

        ColumnConstraints column4 = new ColumnConstraints();
        column4.setHgrow(Priority.SOMETIMES);
        column4.setMaxWidth(116);
        column4.setMinWidth(10);
        column4.setPrefWidth(80);

        ColumnConstraints column5 = new ColumnConstraints();
        column5.setHgrow(Priority.SOMETIMES);
        column5.setMaxWidth(107);
        column5.setMinWidth(10);
        column5.setPrefWidth(80);

        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(67);
        row1.setPrefHeight(67);
        row1.setVgrow(Priority.SOMETIMES);

        gridPane.getRowConstraints().add(row1);

        Label label1 = new Label("Name: " + journey.getName());
        label1.setPrefHeight(86);
        label1.setPrefWidth(236);

        Label label2 = new Label("Price: " + journey.getPrice()+"$");
        label2.setPrefHeight(83);
        label2.setPrefWidth(160);
        GridPane.setConstraints(label2, 1, 0);

        Button buttonAdd = new Button("Add");
        buttonAdd.setDisable(add);
        buttonAdd.setPrefHeight(91);
        buttonAdd.setPrefWidth(80);
        buttonAdd.getStyleClass().add("add-btn");
        GridPane.setConstraints(buttonAdd, 2, 0);

        Button buttonRemove = new Button("Remove");
        buttonRemove.setDisable(remove);
        buttonRemove.setPrefHeight(91);
        buttonRemove.setPrefWidth(80);
        buttonRemove.getStyleClass().add("remove-btn");
        GridPane.setConstraints(buttonRemove, 3, 0);

        Button buttonInfo = new Button("Info");
        buttonInfo.setPrefHeight(91);
        buttonInfo.setPrefWidth(80);
        buttonInfo.getStyleClass().add("info-btn");
        GridPane.setConstraints(buttonInfo, 4, 0);

        Tab selected = tabPane.getSelectionModel().getSelectedItem();

        buttonAdd.setOnAction(e-> {
            owner.approveJourney(journey);
            selected.getOnSelectionChanged().handle(null);
        });

        buttonRemove.setOnAction(e -> {
            owner.declineJourney(journey);
            selected.getOnSelectionChanged().handle(null);
        });

        buttonInfo.setOnAction(actionEvent -> {
            informOnEvent(actionEvent, journey.info(), 600,400);
        });

        gridPane.getChildren().addAll(label1, label2, buttonAdd, buttonRemove, buttonInfo);
        journeyList.getChildren().add(gridPane);
    }

    public static void generateJourneyViewForTraveler(Journey journey, VBox journeyList, boolean add, boolean remove, Traveler traveler, TabPane tabPane) {
        GridPane gridPane = new GridPane();
        gridPane.setMaxWidth(639);
        gridPane.setPrefHeight(75);
        gridPane.setPrefWidth(639);
        gridPane.getStyleClass().add("journey-item");

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setHgrow(Priority.SOMETIMES);
        column1.setMaxWidth(241);
        column1.setMinWidth(10);
        column1.setPrefWidth(236);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setHgrow(Priority.SOMETIMES);
        column2.setMaxWidth(305);
        column2.setMinWidth(10);
        column2.setPrefWidth(160);

        ColumnConstraints column3 = new ColumnConstraints();
        column3.setHgrow(Priority.SOMETIMES);
        column3.setMaxWidth(185);
        column3.setMinWidth(10);
        column3.setPrefWidth(80);

        ColumnConstraints column4 = new ColumnConstraints();
        column4.setHgrow(Priority.SOMETIMES);
        column4.setMaxWidth(116);
        column4.setMinWidth(10);
        column4.setPrefWidth(80);

        ColumnConstraints column5 = new ColumnConstraints();
        column5.setHgrow(Priority.SOMETIMES);
        column5.setMaxWidth(107);
        column5.setMinWidth(10);
        column5.setPrefWidth(80);

        gridPane.getColumnConstraints().addAll(column1, column2, column3, column4, column5);

        RowConstraints row1 = new RowConstraints();
        row1.setMinHeight(67);
        row1.setPrefHeight(67);
        row1.setVgrow(Priority.SOMETIMES);

        gridPane.getRowConstraints().add(row1);

        Label label1 = new Label("Name: " + journey.getName());
        label1.setPrefHeight(86);
        label1.setPrefWidth(236);

        Label label2 = new Label("Price: " + journey.getPrice()+"$");
        label2.setPrefHeight(83);
        label2.setPrefWidth(160);
        GridPane.setConstraints(label2, 1, 0);

        Button buttonAdd = new Button("Add");
        buttonAdd.setDisable(add);
        buttonAdd.setPrefHeight(91);
        buttonAdd.setPrefWidth(80);
        buttonAdd.getStyleClass().add("add-btn");
        GridPane.setConstraints(buttonAdd, 2, 0);

        Button buttonRemove = new Button("Remove");
        buttonRemove.setDisable(remove);
        buttonRemove.setPrefHeight(91);
        buttonRemove.setPrefWidth(80);
        buttonRemove.getStyleClass().add("remove-btn");
        GridPane.setConstraints(buttonRemove, 3, 0);

        Button buttonInfo = new Button("Info");
        buttonInfo.setPrefHeight(91);
        buttonInfo.setPrefWidth(80);
        buttonInfo.getStyleClass().add("info-btn");
        GridPane.setConstraints(buttonInfo, 4, 0);

        Tab selected = tabPane.getSelectionModel().getSelectedItem();

        buttonAdd.setOnAction( actionEvent -> {
            if (!traveler.addMyJourney(journey)) {
                informOnEvent(actionEvent, "Cannot add this journey. Check your balance and age restrictions.", 400, 200);
                return;
            }
            selected.getOnSelectionChanged().handle(null);
        });

        buttonRemove.setOnAction(actionEvent -> {
            traveler.removeMyJourney(journey);
            selected.getOnSelectionChanged().handle(null);
        });

        buttonInfo.setOnAction(actionEvent -> {
            informOnEvent(actionEvent, journey.info(), 600,400);
        });

        gridPane.getChildren().addAll(label1, label2, buttonAdd, buttonRemove, buttonInfo);
        journeyList.getChildren().add(gridPane);
    }
}
