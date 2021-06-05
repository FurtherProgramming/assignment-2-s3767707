package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Main;
import main.model.ResetPasswordModel;
import main.model.User;

import java.net.URL;
import java.util.ResourceBundle;

/*
 * Class:		SecretQuestionController
 * Description:	A class that shows secret question when user reset their password
 * Author:		Anson Go Guang Ping
 */
public class SecretQuestionController implements Initializable {
    private ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    private Main main = new Main();
    @FXML
    private TextArea txtSecretQuestion;
    @FXML
    private TextField txtAnswer;


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        User user = (User) Main.stage.getUserData();
        txtSecretQuestion.setText(user.getQuestion());
    }

    public void Submit(ActionEvent event) throws Exception {

        User user = (User) Main.stage.getUserData();
        if (resetPasswordModel.validateAnswer(user.getAnswer(), txtAnswer.getText())) { //validate answer
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you confirm to reset your password?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                alert.close();
                String newPassword = resetPasswordModel.generateRandomPassword(4);
                resetPasswordModel.updatePassword(user.getUsername(), newPassword);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Update successfully! Your new password is " + newPassword + ".", ButtonType.CLOSE);
                alert2.showAndWait();
                if (alert2.getResult() == ButtonType.CLOSE) {
                    alert2.close();
                    main.change("ui/Login.fxml");
                }
            } else {
                alert.close();
                main.change("ui/Login.fxml");
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Answer is incorrect!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
                main.change("ui/SecretQuestion.fxml");
            }
        }
    }

    public void Cancel(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }


}
