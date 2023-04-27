package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;

import java.io.IOException;

public class Logout {
    private static final SystemAdministration systemAdministration;

    static {
        systemAdministration = SystemAdministration.initialize();
    }

    @FXML
    protected void onLogOutButtonClick(Event event) throws IOException {
        systemAdministration.logout();
        ControllerUtility.switchSceneOnEvent(event, "continue-as.fxml");
    }
}
