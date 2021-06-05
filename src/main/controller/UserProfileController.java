package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import main.Main;
import main.model.User;

import java.net.URL;
import java.util.ResourceBundle;

/*
 * Class:		UserProfileController
 * Description:	A class that handles user profile page
 * Author:		Anson Go Guang Ping
 */
public class UserProfileController implements Initializable {

    private Main main = new Main();
    @FXML
    private Label username;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        User user = (User) Main.stage.getUserData();
        username.setText(user.getUsername());
    }

    public void HomePage(ActionEvent event) throws Exception {

        main.change("ui/Home.fxml");
    }

    public void ProfilePage(ActionEvent event) throws Exception {

        main.change("ui/UserProfile.fxml");
    }

    public void Logout(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }

    public void MakeBooking(ActionEvent event) throws Exception {

        User u = (User) Main.stage.getUserData();
        // only activated account can make booking
        if (u.getStatus().equals("activated")) {
            main.change("ui/UserBooking.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Account deactivated. Please contact admin to reactivate your account!!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
    }


    public void ViewBooking(ActionEvent actionEvent) throws Exception {

        main.change("ui/UserViewBooking.fxml");
    }

    public void CheckIn(ActionEvent event) throws Exception {

        User u = (User) Main.stage.getUserData();
        // only activated account and check in
        if (u.getStatus().equals("activated")) {
            main.change("ui/UserCheckIn.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Account deactivated. Please contact admin to reactivate your account!!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
    }
}
