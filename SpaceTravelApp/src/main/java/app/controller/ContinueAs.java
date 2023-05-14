package app.controller;

import app.model.SystemAdministration;
import app.model.users.UserType;
import javafx.event.Event;
import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Controller for initial screen. Based on what button user clicks, the access strategy is set
 */
public class ContinueAs {
    private static final SystemAdministration systemAdministration;

    static {
        systemAdministration = SystemAdministration.initialize();
    }

    @FXML
    protected void onTravelerButtonClick(Event event) throws IOException {
        systemAdministration.setAccessContext(UserType.TRAVELER);
        ControllerUtility.switchSceneOnEvent(event, "traveller-login.fxml");
    }

    @FXML
    protected void onGuideButtonClick(Event event) throws IOException {
        systemAdministration.setAccessContext(UserType.GUIDE);
        ControllerUtility.switchSceneOnEvent(event, "staff-login.fxml");
    }

    @FXML
    protected void onOwnerButtonClick(Event event) throws IOException {
        systemAdministration.setAccessContext(UserType.OWNER);
        ControllerUtility.switchSceneOnEvent(event, "staff-login.fxml");
    }
}