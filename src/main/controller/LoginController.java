package main.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

    }
    /* login Action method
       check if user input is the same as database.
     */
    public void Login(ActionEvent event){

        try {
            if (loginModel.isLogin(txtUsername.getText(),txtPassword.getText())){
                if(loginModel.isAdmin(txtUsername.getText(), txtPassword.getText())) {
                    ButtonType admin = new ButtonType("ADMIN");
                    ButtonType user = new ButtonType("USER");
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Choose your account type", admin, user);
                    alert.showAndWait();
                    if (alert.getResult() == admin) {
                        alert.close();
                        main.change("ui/AdminProfile.fxml");
                    }
                    else {
                        main.change("ui/UserProfile.fxml");
                    }
                }
                else {
                    main.change("ui/UserProfile.fxml");
                }

            }else{
                if(loginModel.usernameValid(txtUsername.getText())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Password is invalid!", ButtonType.CLOSE);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.CLOSE) {
                        alert.close();
                        main.change("ui/Login.fxml");
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Username is invalid!", ButtonType.CLOSE);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.CLOSE) {
                        alert.close();
                        main.change("ui/Login.fxml");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ResetPassword(ActionEvent event) throws Exception {

        main.change("ui/ResetPassword.fxml");
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
