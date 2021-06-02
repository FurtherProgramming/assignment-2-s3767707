package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import main.Main;
import main.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class AccountManagementController implements Initializable {

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

    public void AddAccount(ActionEvent event) throws Exception {

        main.change("ui/AddAccount.fxml");
    }

    public void AdminSearchAccount(ActionEvent event) throws Exception {

        main.change("ui/AdminSearchAccountToUpdate.fxml");
    }

    public void AdminDeleteAccount(ActionEvent event) throws Exception {

        main.change("ui/AdminRemoveAccount.fxml");
    }


}
