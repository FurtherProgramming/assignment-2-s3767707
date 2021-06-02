package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.Main;
import main.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class UserProfileController implements Initializable {

    private Main main = new Main();
    @FXML
    private Label username;

    @Override
    public void initialize(URL location, ResourceBundle resources){
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

        main.change("ui/UserBooking.fxml");
    }


    public void CancelBooking(ActionEvent actionEvent) throws Exception {

        main.change("ui/UserRemoveBooking.fxml");
    }

    public void CheckIn(ActionEvent event) throws Exception {

        main.change("ui/UserCheckIn.fxml");
    }
}
