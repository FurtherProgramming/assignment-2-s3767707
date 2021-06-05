package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import main.Main;
import main.model.ResetPasswordModel;
import main.model.User;

import java.net.URL;
import java.util.ResourceBundle;

/*
 * Class:		ResetPasswordController
 * Description:	A class that handles reset password page
 * Author:		Anson Go Guang Ping
 */
public class ResetPasswordController implements Initializable {
    private ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    private Main main = new Main();
    @FXML
    private TextField txtUsername;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void ValidateUsername(ActionEvent event) {

        try {
            // user need to enter username before accessing their secret question
            User user = resetPasswordModel.validateUsername(txtUsername.getText());
            if (user != null) {
                Main.stage.setUserData(user);
                main.change("ui/SecretQuestion.fxml");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username is invalid!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE) {
                    alert.close();
                    main.change("ui/ResetPassword.fxml");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Cancel(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }


}
