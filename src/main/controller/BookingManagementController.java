package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.Main;
import main.model.SeatManagementModel;
import main.model.User;
import java.net.URL;
import java.util.ResourceBundle;

/*
 * Class:		BookingManagementController
 * Description:	A class that handles admin booking management page
 * Author:		Anson Go Guang Ping
 */
public class BookingManagementController implements Initializable {

    private Main main = new Main();
    @FXML
    private Label username;
    private SeatManagementModel seatManagementModel = new SeatManagementModel();

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

    public void EditBooking(ActionEvent event) throws Exception {

        main.change("ui/AdminEditBooking.fxml");
    }

    public void SeatManagement(ActionEvent event) throws Exception {

        main.setSeatColorInSeatManagement("ui/SeatManagement.fxml", seatManagementModel.getAllSeats());
    }



}
