package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import app.model.journeys.Journey;
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

/**
 * The Controller for the main screen of the Traveler user
 */
public class TravelerMainScreen implements Initializable {
    private Traveler traveler;
    private Timer timer = new Timer();
    private static SystemAdministration systemAdministration;

    static {
        systemAdministration = SystemAdministration.initialize();
    }

    @FXML
    private TabPane tabContainer;

    @FXML
    private VBox myJourneys, availableJourneys, allJourneys;

    @FXML
    private Label nameLabel, bYearLabel, passwordLabel, sessionDurationLabel, balanceLabel;

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
        boolean success = traveler.withdraw(1000);
        if (!success) {
            ControllerUtility.informOnEvent(event, "Error occurred while processing your payment. Please check your balance.", 400, 200);
        }
        balanceLabel.setText(traveler.getTravelCard().getBalance() + "$");
    }

    @FXML
    protected void onDepositButtonClicked() {
        traveler.deposit(1000);
        balanceLabel.setText(traveler.getTravelCard().getBalance() + "$");
    }

    /**
     * @param url not used
     * @param resourceBundle not used
     * This method loads all necessary user data onto the screen immediately after the scene with this screen is displayed
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        traveler = (Traveler) systemAdministration.getCurrentUser();
        nameLabel.setText(traveler.getUsername());
        bYearLabel.setText(traveler.getTravelCard().getBirthYear() + "");
        passwordLabel.setText(Utility.mask(traveler.getPassword() + ""));
        balanceLabel.setText(traveler.getTravelCard().getBalance() + "$");

        fillJourneyTabs(traveler.getMyJourneys(), myJourneys, false, true);
        fillJourneyTabs(traveler.getAvailableJourneys(), availableJourneys, true, false);
        fillJourneyTabs(traveler.getAllJourneys(), allJourneys, false, false);

        System.out.println(traveler.getMyJourneys());
        System.out.println(traveler.getAvailableJourneys());
        System.out.println(traveler.getAllJourneys());

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
        if (this.traveler == null) {
            return;
        }
        fillJourneyTabs(traveler.getMyJourneys(), myJourneys, false, true);
        fillJourneyTabs(traveler.getAvailableJourneys(), availableJourneys, true, false);
        fillJourneyTabs(traveler.getAllJourneys(), allJourneys, false, false);
        balanceLabel.setText(traveler.getTravelCard().getBalance() + "$");
    }


    @FXML
    private void fillJourneyTabs(ArrayList<Journey> journeys, VBox journeyList, boolean add, boolean remove) {
        if (journeys == null) {
            System.out.println("returned");
            return;
        }
        journeyList.getChildren().clear();
        for (Journey journey : journeys) {
            ControllerUtility.generateJourneyViewForTraveler(journey, journeyList, !add, !remove, traveler, tabContainer);
        }
    }
}
