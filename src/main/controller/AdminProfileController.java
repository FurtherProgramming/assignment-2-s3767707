package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import main.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminProfileController implements Initializable {

    private Main main = new Main();

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    public void HomePage(ActionEvent event) throws Exception {

        main.change("ui/home.fxml");
    }

    public void ProfilePage(ActionEvent event) throws Exception {

        main.change("ui/adminProfile.fxml");
    }

    public void Logout(ActionEvent event) throws Exception {

        main.change("ui/login.fxml");
    }






}
