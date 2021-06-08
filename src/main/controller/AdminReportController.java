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
 * Class:		AdminReportController
 * Description:	A class that handles admin report page
 * Author:		Anson Go Guang Ping
 */
public class AdminReportController implements Initializable {

    private Main main = new Main();
    @FXML
    private Label username;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

    public void Back(ActionEvent event) throws Exception {

        main.change("ui/AdminProfile.fxml");
    }

    public void EmployeeReport(ActionEvent event) throws Exception {

        main.change("ui/AdminEmployeeReport.fxml");
    }

    public void BookingReport(ActionEvent event) throws Exception {

        main.change("ui/AdminBookingReport.fxml");
    }
}
