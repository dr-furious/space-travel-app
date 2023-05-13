package app.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class TravelerMainScreen {
    int buttonsGenerated = 0;

    @FXML
    private VBox mainPane;

    @FXML
    private Button generator;

    @FXML
    protected void generateButton() {

        buttonsGenerated++;
        Button newButton = new Button("Button " + buttonsGenerated);
        newButton.setId(""+buttonsGenerated);
        newButton.getStyleClass().add("menu-button");

        EventHandler<ActionEvent> event = e-> {
            int colorPicker = Integer.parseInt(newButton.getId()) %3;
            switch (colorPicker) {
                case 0 -> {
                    newButton.getStyleClass().add("button-red");
                }
                case 1 -> {
                    newButton.getStyleClass().add("button-blue");
                }
                case 2 -> {
                    newButton.getStyleClass().add("button-green");
                }
            }
        };

        newButton.setOnAction(event);

        mainPane.getChildren().add(newButton);
    }
}
