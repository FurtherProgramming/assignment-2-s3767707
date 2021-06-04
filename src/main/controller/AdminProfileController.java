package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.Main;
import main.model.User;
import java.net.URL;
import java.util.ResourceBundle;

/*
 * Class:		AdminProfileController
 * Description:	A class that handles admin profile page
 * Author:		Anson Go Guang Ping
 */
public class AdminProfileController implements Initializable {

    private Main main = new Main();
    @FXML
    private Label username;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

        User user = (User) Main.stage.getUserData();
        username.setText(user.getUsername());
    }

    public void HomePage(ActionEvent event) throws Exception {

        main.change("ui/Home.fxml");
    }

    public void ProfilePage(ActionEvent event) throws Exception {

        main.change("ui/AdminProfile.fxml");
    }

    public void Logout(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }

    public void BookingManagement(ActionEvent event) throws Exception {

        main.change("ui/BookingManagement.fxml");
    }

    public void AccountManagement(ActionEvent event) throws Exception {

        main.change("ui/AccountManagement.fxml");
    }

    public void Report(ActionEvent event) throws Exception {

        main.change("ui/AdminReport.fxml");
    }

}
