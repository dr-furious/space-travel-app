package app.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SortEvent;
import javafx.stage.Stage;

import java.io.IOException;
public class ContinueAs {
    @FXML
    private Button traveler;
    private Button guide;
    private Button owner;

    @FXML
    protected void onTravelerButtonClick(Event event) throws IOException {
        ControllerUtility.switchSceneOnEvent(event, "hello-traveller.fxml");
    }

    @FXML
    protected void onGuideButtonClick() {
        System.out.println("Hello Guide!");
    }

    @FXML
    protected void onOwnerButtonClick() {
        System.out.println("Hello Owner!");
    }
}