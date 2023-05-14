package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import app.model.journeys.Journey;
import app.model.users.Owner;
import app.model.users.Traveler;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class OwnerMainScreen implements Initializable {
    private Owner owner;
    private Timer timer = new Timer();
    private static SystemAdministration systemAdministration;

    static {
        systemAdministration = SystemAdministration.initialize();
    }

    @FXML
    private TabPane tabContainer;

    @FXML
    private VBox newJourneys, allJourneys;

    @FXML
    private Label nameLabel, bYearLabel, passwordLabel, sessionDurationLabel, balanceLabel, accessTokenLabel;

    @FXML
    private Button signOut, save, withdraw, deposit;

    @FXML
    protected void onSignOutButtonClick(Event event) throws IOException {
        systemAdministration.logout();
        ControllerUtility.switchSceneOnEvent(event, "continue-as.fxml");
    }

    @FXML
    protected void onSaveButtonClicked() {

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        owner = (Owner) systemAdministration.getCurrentUser();
        nameLabel.setText(owner.getUsername());
        bYearLabel.setText(owner.getTravelCard().getBirthYear() + "");
        passwordLabel.setText(Utility.mask(owner.getPassword() + ""));
        accessTokenLabel.setText(Utility.mask(Owner.getAccessToken() + ""));
        balanceLabel.setText(owner.getTravelCard().getBalance() + "$");

        fillJourneyTabs(owner.getNewJourneys(), newJourneys, true, true);
        fillJourneyTabs(owner.getAllJourneys(), allJourneys, false, false);

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
    protected void onTabClicked() {
        if (this.owner == null) {
            return;
        }
        fillJourneyTabs(owner.getNewJourneys(), newJourneys, true, true);
        fillJourneyTabs(owner.getAllJourneys(), allJourneys, false, false);
    }

    @FXML
    private void fillJourneyTabs(ArrayList<Journey> journeys, VBox journeyList, boolean approve, boolean cancel) {
        if (journeys == null) {
            System.out.println("returned");
            return;
        }
        journeyList.getChildren().clear();
        for (Journey journey : journeys) {
            ControllerUtility.generateJourneyViewForOwner(journey, journeyList, !approve, !cancel,owner, tabContainer);
        }
    }
}
