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
 * Class:		AdminAddAccountController
 * Description:	A class that handles user edit bookings function
 * Author:		Anson Go Guang Ping
 */
public class AdminAddAccountController implements Initializable {
    public RegisterModel registerModel = new RegisterModel();
    public Main main = new Main();
    private LoginModel loginModel = new LoginModel();
    @FXML
    private TextField txtEmployerId;
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
        ArrayList<String> roles = new ArrayList<String>();
        ArrayList<String> secretQuestions = new ArrayList<String>();
        // Add selections to role section
        roles.add("admin");
        roles.add("user");
        // Add selections to secret question section
        secretQuestions.add("What is your favourite colour?");
        secretQuestions.add("What is your favourite food?");
        role.addAll(roles);
        secretQuestion.addAll(secretQuestions);
        txtRole.getItems().addAll(role);
        txtSecretQuestion.getItems().addAll(secretQuestion);
    }

    /*
     * handles register button
     */
    public void Register(ActionEvent event) throws Exception {

        try {
            // if employer id not entered, show error message
            if (txtEmployerId.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employer id is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if first name not entered, show error message
            else if (txtFirstname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "First name is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if last name not entered, show error message
            else if (txtLastname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Last name is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if role not choosed, show error message
            else if (txtRole.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Role is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if username not entered, show error message
            else if (txtUsername.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if password not entered, show error message
            else if (txtPassword.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if secret question not choosed, show error message
            else if (txtSecretQuestion.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pick a secret question!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if answer not entered, show error message
            else if (txtAnswer.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Answer for secret question is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if employer id exists, show error message
            else if (registerModel.employerIdExist(txtEmployerId.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employer id exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if username exists, show error message
            else if (loginModel.usernameExist(txtUsername.getText())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            } else {
                registerModel.register(txtEmployerId.getText(), txtFirstname.getText(), txtLastname.getText(), txtRole.getValue(), txtUsername.getText(), txtPassword.getText(), txtSecretQuestion.getValue(), txtAnswer.getText()); // register user
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Register successfully!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
                main.change("ui/AdminAccountManagement.fxml");
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

    public void Back(ActionEvent event) throws Exception {

        main.change("ui/AccountManagement.fxml");
    }
}
