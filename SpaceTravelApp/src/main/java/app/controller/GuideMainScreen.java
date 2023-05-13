package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import app.model.users.Guide;
import app.model.users.Traveler;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GuideMainScreen {
    private Guide guide;
    private Timer timer = new Timer();
    private static SystemAdministration systemAdministration;

    static {
        systemAdministration = SystemAdministration.initialize();
    }


    @FXML
    private Label nameLabel, bYearLabel, passwordLabel, sessionDurationLabel, balanceLabel, accessTokenLabel;

    @FXML
    private Button signOut, loadData, withdraw, deposit;

    @FXML
    protected void onSignOutButtonClick(Event event) throws IOException {
        systemAdministration.logout();
        ControllerUtility.switchSceneOnEvent(event, "continue-as.fxml");
    }

    @FXML
    protected void onLoadDataButtonClicked() {
        guide = (Guide) systemAdministration.getCurrentUser();
        nameLabel.setText(guide.getUsername());
        bYearLabel.setText(guide.getTravelCard().getBirthYear() + "");
        passwordLabel.setText(Utility.mask(guide.getPassword() + ""));
        accessTokenLabel.setText(Utility.mask(Guide.getAccessToken() + ""));
        balanceLabel.setText(guide.getTravelCard().getBalance() + "$");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    sessionDurationLabel.setText(systemAdministration.getSessionDuration() + "");
                });
            }
        }, 0, 1000);
    }

    @FXML
    protected void onWithdrawButtonClicked(Event event) {
        boolean success = guide.withdraw(1000);
        if (!success) {
            ControllerUtility.informOnEvent(event, "Error occurred while processing your payment. Please check your balance.", 400, 200);
        }
        balanceLabel.setText(guide.getTravelCard().getBalance() + "$");
    }

    @FXML
    protected void onDepositButtonClicked() {
        guide.deposit(1000);
        balanceLabel.setText(guide.getTravelCard().getBalance() + "$");
    }
}