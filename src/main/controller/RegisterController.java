package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import main.Main;
import main.model.LoginModel;
import main.model.RegisterModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Class:		RegisterController
 * Description:	A class that handles register page
 * Author:		Anson Go Guang Ping
 */
public class RegisterController implements Initializable {
    public Main main = new Main();
    private RegisterModel registerModel = new RegisterModel();
    private LoginModel loginModel = new LoginModel();
    @FXML
    private TextField txtEmployeeId;
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
    @FXML
    private ChoiceBox<String> txtRole;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtPassword;
    @FXML
    private ChoiceBox<String> txtSecretQuestion;
    @FXML
    private TextField txtAnswer;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addItemToChoiceBox();
    }

    public void addItemToChoiceBox() {

        ObservableList role = FXCollections.observableArrayList();
        ObservableList secretQuestion = FXCollections.observableArrayList();
        ArrayList<String> secretQuestions = new ArrayList<String>();
        ArrayList<String> roles = new ArrayList<String>();
        roles.add("admin");
        roles.add("user");
        secretQuestions.add("What is your favourite colour?");
        secretQuestions.add("What is your favourite food?");
        role.addAll(roles);
        secretQuestion.addAll(secretQuestions);
        txtRole.getItems().addAll(role);
        txtSecretQuestion.getItems().addAll(secretQuestion);
    }

    public void Register(ActionEvent event) throws Exception {

        try {
            if (txtEmployeeId.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employee id is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else if (txtFirstname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "First name is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else if (txtLastname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Last name is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }else if (txtRole.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pick your role!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else if (txtUsername.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else if (txtPassword.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else if (txtSecretQuestion.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pick your secret question!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else if (txtAnswer.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Answer for secret question is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else if (registerModel.employeeIdExist(txtEmployeeId.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employee id exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else if (loginModel.usernameExist(txtUsername.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else {
                registerModel.register(txtEmployeeId.getText(), txtFirstname.getText(), txtLastname.getText(), txtRole.getValue(), txtUsername.getText(), txtPassword.getText(), txtSecretQuestion.getValue(), txtAnswer.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Register successfully!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
                main.change("ui/Login.fxml");
            }
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
