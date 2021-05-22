package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.model.LoginModel;
import main.model.ResetPasswordModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {
    private ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    private Main main = new Main();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;



    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (resetPasswordModel.isDbConnected()){
            isConnected.setText("Connected to database");
        }else{
            isConnected.setText("Not Connected to database");
        }

    }

    public void ValidteUsername(ActionEvent event){

        try {
            if (resetPasswordModel.validateUsername(txtUsername.getText())){

                main.change("ui/showSecretQuestion.fxml");
            }else{
                isConnected.setText("Username is incorrect");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Cancel(ActionEvent event) throws Exception {

        main.change("ui/login.fxml");
    }


}
