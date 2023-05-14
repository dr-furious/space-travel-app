package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import app.model.journeys.Journey;
import app.model.users.Guide;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The Controller for the main screen of the Guide user
 */
public class GuideMainScreen implements Initializable {
    private Guide guide;
    private Timer timer = new Timer();
    private static SystemAdministration systemAdministration;

    static {
        systemAdministration = SystemAdministration.initialize();
    }

    @FXML
    private VBox myJourneys, acceptedJourneys, declinedJourneys;

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

    @FXML
    protected void onCreateJourneyButtonClick(Event event) {
        Journey newJourney = guide.createJourney(systemAdministration.getJourneys());
        systemAdministration.addJourney(newJourney);

        ControllerUtility.generateJourneyViewForGuide(newJourney, myJourneys);
        ControllerUtility.informOnEvent(event, "New Journey created!", 400, 200);
    }

    /**
     * @param url not used
     * @param resourceBundle not used
     * This method loads all necessary user data onto the screen immediately after the scene with this screen is displayed
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        guide = (Guide) systemAdministration.getCurrentUser();
        nameLabel.setText(guide.getUsername());
        bYearLabel.setText(guide.getTravelCard().getBirthYear() + "");
        passwordLabel.setText(Utility.mask(guide.getPassword() + ""));
        accessTokenLabel.setText(Utility.mask(Guide.getAccessToken() + ""));
        balanceLabel.setText(guide.getTravelCard().getBalance() + "$");

        fillJourneyTabs(guide.getMyJourneys(), myJourneys);
        fillJourneyTabs(guide.getApprovedJourneys(), acceptedJourneys);
        fillJourneyTabs(guide.getCanceledJourneys(), declinedJourneys);

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
        if (this.guide == null) {
            return;
        }
        fillJourneyTabs(guide.getMyJourneys(), myJourneys);
        fillJourneyTabs(guide.getApprovedJourneys(), acceptedJourneys);
        fillJourneyTabs(guide.getCanceledJourneys(), declinedJourneys);
    }

    @FXML
    private void fillJourneyTabs(ArrayList<Journey> journeys, VBox journeyList) {
        if (journeys == null) {
            System.out.println("returned");
            return;
        }
        journeyList.getChildren().clear();
        for (Journey journey : journeys) {
            ControllerUtility.generateJourneyViewForGuide(journey, journeyList);
        }
    }
}