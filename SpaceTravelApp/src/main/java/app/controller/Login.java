package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import app.model.access.AccessContext;
import app.model.access.GuideAccess;
import app.model.access.TravelerAccess;
import app.model.users.Guide;
import app.model.users.Traveler;
import app.model.users.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login {

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField accessToken;
    @FXML
    private Label informative;

    private String realPassword = "";
    private String realAccessToken = "";
    private static final SystemAdministration systemAdministration;

    static {
        systemAdministration = SystemAdministration.initialize();
    }

    @FXML
    protected void onPreviousButtonClick(Event event) throws IOException {
        clear();
        ControllerUtility.switchSceneOnEvent(event, "continue-as.fxml");
    }

    @FXML
    protected void onNextButtonClick(Event event) throws IOException {
        String username = this.username.getText();

        int intPassword;
        int intAT;

        try {
            intPassword = Integer.parseInt(realPassword);
        } catch (NumberFormatException numberFormatException) {
            intPassword = 0;
        }
        try {
            intAT = Integer.parseInt(realAccessToken);
        } catch (NumberFormatException numberFormatException) {
            intAT = 0;
        }

        int responseCode = systemAdministration.login(username, intPassword, intAT);

        switch (responseCode) {
            case 0 -> {
                // switch scene based on user type
                User currentUser = systemAdministration.getCurrentUser();
                if (currentUser.getClass() == Traveler.class) {
                    ControllerUtility.switchSceneOnEvent(event, "traveller/traveller-main.fxml");
                } else if (currentUser.getClass() == Guide.class) {
                    ControllerUtility.switchSceneOnEvent(event, "guide/guide-main.fxml");
                } else {
                    ControllerUtility.switchSceneOnEvent(event, "owner/owner-main.fxml");
                }
                clear();
            }
            case 1 -> {
                setErrorMessage("User with name \"" + username + "\" not found.");
            }
            case 2 -> {
                setErrorMessage("Incorrect password for user with name \"" + username + "\".");
            }
            case 3 -> {
                setErrorMessage("Incorrect access token for user with name \"" + username + "\".");
            }
        }
    }

    @FXML
    protected void onRegisterLinkClicked(Event event) throws IOException {
        AccessContext accessContext = systemAdministration.getAccessContext();
        clear();
        if (accessContext.getAccessStrategy().getClass() == TravelerAccess.class) {
            ControllerUtility.switchSceneOnEvent(event, "traveller-signup.fxml");
        } else {
            ControllerUtility.switchSceneOnEvent(event, "staff-signup.fxml");
        }
    }

    @FXML
    protected void onPasswordFieldKeyTyped() {
        String text = password.getText();

        if (text.length() > realPassword.length()) {
            realPassword += Character.toString(text.charAt(text.length()-1));
        } else if (text.length() < realPassword.length()) {
            realPassword = Utility.removeLastChar(realPassword);
        }

        if (Utility.containsNonNumericChars(realPassword)) {
            realPassword = Utility.removeNonNumericChars(realPassword);
            password.setText(realPassword);
            informative.setStyle("-fx-text-fill: #ff9900");
            informative.setText("Password can contain only numbers!");
        } else {
            password.setText(realPassword);
            informative.setText("");
        }

        password.positionCaret(realPassword.length());
    }
    @FXML
    protected void onAccessTokenFieldKeyTyped() {
        String text = accessToken.getText();

        if (text.length() > realAccessToken.length()) {
            realAccessToken += Character.toString(text.charAt(text.length()-1));
        } else if (text.length() < realAccessToken.length()) {
            realAccessToken = Utility.removeLastChar(realAccessToken);
        }

        if (Utility.containsNonNumericChars(realAccessToken)) {
            realAccessToken = Utility.removeNonNumericChars(realAccessToken);
            accessToken.setText(realAccessToken);
            informative.setStyle("-fx-text-fill: #ff9900");
            informative.setText("Access Token can contain only numbers!");
        } else {
            accessToken.setText(realAccessToken);
            informative.setText("");
        }

        accessToken.positionCaret(realAccessToken.length());
    }

    private void setErrorMessage(String message) {
        informative.setStyle("-fx-text-fill: red");
        informative.setText(message);
    }

    private void clear() {
        this.realPassword = "";
        this.realAccessToken = "";
    }
}
