package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import app.model.users.Owner;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class OwnerMainScreen {
    private Owner owner;
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
        owner = (Owner) systemAdministration.getCurrentUser();
        nameLabel.setText(owner.getUsername());
        bYearLabel.setText(owner.getTravelCard().getBirthYear() + "");
        passwordLabel.setText(Utility.mask(owner.getPassword() + ""));
        accessTokenLabel.setText(Utility.mask(Owner.getAccessToken() + ""));
        balanceLabel.setText(owner.getTravelCard().getBalance() + "$");
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
        boolean success = owner.withdraw(1000);
        if (!success) {
            ControllerUtility.informOnEvent(event, "Error occurred while processing your payment. Please check your balance.", 400, 200);
        }
        balanceLabel.setText(owner.getTravelCard().getBalance() + "$");
    }

    @FXML
    protected void onDepositButtonClicked() {
        owner.deposit(1000);
        balanceLabel.setText(owner.getTravelCard().getBalance() + "$");
    }
}
