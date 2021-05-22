package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.model.LoginModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Main main = new Main();

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }

    public void LoginPage(ActionEvent event) throws Exception {

        main.change("ui/login.fxml");
    }

    public void RegisterPage(ActionEvent event) throws Exception {

        main.change("ui/register.fxml");
    }

    public void HomePage(ActionEvent event) throws Exception {

        main.change("ui/home.fxml");
    }






}
