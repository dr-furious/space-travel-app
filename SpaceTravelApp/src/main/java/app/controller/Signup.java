package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import app.model.access.AccessContext;
import app.model.access.TravelerAccess;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Signup {
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
        AccessContext accessContext = systemAdministration.getAccessContext();
        if (accessContext.getAccessStrategy().getClass() == TravelerAccess.class) {
            ControllerUtility.switchSceneOnEvent(event, "traveller-login.fxml");
        } else {
            ControllerUtility.switchSceneOnEvent(event, "staff-login.fxml");
        }
    }

    @FXML
    protected void onNextButtonClick(Event event) throws IOException {
        String username = this.username.getText();


        int intPassword;
        int intAT;
        int birthYear = 0;

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

        String informativeText = "";
        if (username.length() <= 2) {
            informativeText += "Username Error: Must have at least 3 characters\n";
        }
        if (realPassword.length() <= 7) {
            informativeText += "Password Error: Must have at least 8 characters (numbers)\n";
        }

        if (informativeText.length() > 0) {
            setErrorMessage(informativeText);
            return;
        }

        int responseCode = systemAdministration.signup(username, intPassword,birthYear, intAT);

        switch (responseCode) {
            case 0 -> {
                // switch scene
                ControllerUtility.switchSceneOnEvent(event, "success.fxml");
                clear();
            }
            case 1 -> {
                setErrorMessage("User with name \"" + username + "\" already exists.");
            }
            case 2 -> {
                setErrorMessage("Should not get this code");
            }
            case 3 -> {
                setErrorMessage("Incorrect access token for user with name \"" + username + "\".");
            }
        }
    }

    @FXML
    protected void onPasswordFieldKeyTyped() {
        String text = password.getText();

        if (text.length() > realPassword.length()) {
            if (text.charAt(0) == '0') {
                password.setText(realPassword);
                informative.setStyle("-fx-text-fill: #ff9900");
                informative.setText("Password cannot begin with \"0\"");
                return;
            }
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
