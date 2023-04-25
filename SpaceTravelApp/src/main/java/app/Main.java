package app;

import app.model.SystemAdministration;
import app.model.Utility;
import app.model.users.UserType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        /*FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Space Travel");
        stage.setScene(scene);
        stage.show();*/

        int option;
        String username;
        int password;
        Scanner scanner = new Scanner(System.in);
        SystemAdministration systemAdministration = SystemAdministration.initialize();
        do {
            System.out.println("=============================");
            System.out.println("Quit: 0\nLogin: 1\nSignup: 2\nPrint all users: 3");
            System.out.println("=============================");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 0 -> {System.exit(0);}
                case 1 -> {
                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextInt();
                    scanner.nextLine();
                    systemAdministration.login(username, password);
                }
                case 2 -> {
                    int birthYear;
                    int who;
                    System.out.print("Who? T(1)|G(2)|O(3): ");
                    who = Integer.parseInt(scanner.nextLine());
                    switch (who) {
                        case 1 -> {systemAdministration.setAccessContext(UserType.TRAVELER);}
                        case 2 -> {systemAdministration.setAccessContext(UserType.GUIDE);}
                        case 3 -> {systemAdministration.setAccessContext(UserType.OWNER);}
                    }
                    System.out.print("Username: ");
                    username = scanner.nextLine();
                    System.out.print("Password: ");
                    password = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Birth year: ");
                    birthYear = scanner.nextInt();
                    scanner.nextLine();
                    systemAdministration.signup(username, birthYear, password);
                }
                case 3 -> {
                    systemAdministration.printUsers();
                }
                default -> {}
            }
        } while (option != 0);
    }

    public static void main(String[] args) {
        launch();
    }
}