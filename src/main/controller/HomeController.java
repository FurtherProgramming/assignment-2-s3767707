package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import main.Main;
import main.model.AdminEditBookingModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Main main = new Main();
    private AdminEditBookingModel adminEditBookingModel = new AdminEditBookingModel();
    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

        try {
            adminEditBookingModel.autoRejectBooking(LocalDate.now(), "Rejected");
            adminEditBookingModel.removeBookingFromDatabaseAfter7Days();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void LoginPage(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }

    public void RegisterPage(ActionEvent event) throws Exception {

        main.change("ui/Register.fxml");
    }

    public void HomePage(ActionEvent event) throws Exception {

        main.change("ui/Home.fxml");
    }






}
