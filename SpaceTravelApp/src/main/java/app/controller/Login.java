package app.controller;

import app.model.SystemAdministration;
import app.model.Utility;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Login {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField accessToken;
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

        /*String informativeText = "";
        if (username.length() <= 2) {
            informativeText += "Username Error: Must have at least 3 characters\n";
        }
        if (realPassword.length() <= 7) {
            informativeText += "Password Error: Must have at least 8 characters (numbers)\n";
        }

        if (informativeText.length() > 0) {
            setErrorMessage(informativeText);
            return;
        }*/

        int responseCode = systemAdministration.login(username, intPassword, intAT);

        switch (responseCode) {
            case 0 -> {
                // switch scene
                ControllerUtility.switchSceneOnEvent(event, "hello-view.fxml");
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

    }

    @FXML
    protected void onPasswordFieldKeyTyped() {
        String text = password.getText();

        if (text.length() > realPassword.length()) {
            realPassword += Character.toString(text.charAt(text.length()-1));
            System.out.println(realPassword);
        } else if (text.length() < realPassword.length()) {
            realPassword = Utility.removeLastChar(realPassword);
        }

        if (Utility.containsNonNumericChars(realPassword)) {
            realPassword = Utility.removeNonNumericChars(realPassword);
            password.setText(Utility.mask(realPassword));
            informative.setStyle("-fx-text-fill: #ff9900");
            informative.setText("Password can contain only numbers!");
        } else {
            password.setText(Utility.mask(realPassword));
            informative.setText("");
        }

        password.positionCaret(realPassword.length());
    }
    @FXML
    protected void onAccessTokenFieldKeyTyped() {
        String text = accessToken.getText();

        if (text.length() > realAccessToken.length()) {
            realAccessToken += Character.toString(text.charAt(text.length()-1));
            System.out.println(realAccessToken);
        } else if (text.length() < realAccessToken.length()) {
            realAccessToken = Utility.removeLastChar(realAccessToken);
        }

        if (Utility.containsNonNumericChars(realAccessToken)) {
            realAccessToken = Utility.removeNonNumericChars(realAccessToken);
            accessToken.setText(Utility.mask(realAccessToken));
            informative.setStyle("-fx-text-fill: #ff9900");
            informative.setText("Access Token can contain only numbers!");
        } else {
            accessToken.setText(Utility.mask(realAccessToken));
            informative.setText("");
        }

        accessToken.positionCaret(realAccessToken.length());
    }

    private void setErrorMessage(String message) {
        informative.setStyle("-fx-text-fill: red");
        informative.setText(message);
    }
}
