package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import main.Main;
import main.model.LoginModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    private LoginModel loginModel = new LoginModel();
    private Main main = new Main();
    @FXML
    private Label isConnected;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){
        if (loginModel.isDbConnected()){
            isConnected.setText("Connected");
        }else{
            isConnected.setText("Not Connected");
        }

    }
    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event){

        try {
            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){
                if(loginModel.isAdmin(txtUsername.getText(), txtPassword.getText())) {

                    main.change("ui/adminProfile.fxml");
                }
                else {
                    main.change("ui/userProfile.fxml");
                }

            }else{
                isConnected.setText("username and password is incorrect");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ResetPassword(ActionEvent event) throws Exception {

        main.change("ui/resetPassword.fxml");
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
