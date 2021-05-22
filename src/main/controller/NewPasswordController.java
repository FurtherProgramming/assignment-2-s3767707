package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Main;
import main.model.NewPasswordModel;
import main.model.ResetPasswordModel;
import main.model.ShowSecretQuestionModel;
import main.model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class NewPasswordController implements Initializable {
    private NewPasswordModel newPasswordModel = new NewPasswordModel();
    private Main main = new Main();
    private String newPassword;
    @FXML
    private TextArea txtNewPassword;



    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        newPassword = newPasswordModel.generateRandomPassword(4);
        txtNewPassword.setText("Your new password is " + newPassword);
        try {
            newPasswordModel.updatePassword(newPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Confirm(ActionEvent event) throws Exception {

        main.change("ui/login.fxml");
    }

    public void Cancel(ActionEvent event) throws Exception {

        main.change("ui/login.fxml");
    }



}
