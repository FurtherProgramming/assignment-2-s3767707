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
import main.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Class:		AdminUpdateAccountController
 * Description:	A class that handles user update account details page
 * Author:		Anson Go Guang Ping
 */
public class AdminUpdateAccountController implements Initializable {
    public AccountManagementModel accountManagementModel = new AccountManagementModel();
    private RegisterModel registerModel = new RegisterModel();
    private LoginModel loginModel = new LoginModel();
    public Main main = new Main();
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
    public void initialize(URL location, ResourceBundle resources){

        addItemToChoiceBox();
    }

    public void addItemToChoiceBox() {

        ObservableList role = FXCollections.observableArrayList();
        ObservableList secretQuestion = FXCollections.observableArrayList();
        ArrayList<String> roles = new ArrayList<String>();
        ArrayList<String> secretQuestions = new ArrayList<String>();
        roles.add("admin");
        roles.add("user");
        secretQuestions.add("What is your favourite colour?");
        secretQuestions.add("What is your favourite food?");
        role.addAll(roles);
        secretQuestion.addAll(secretQuestions);
        txtRole.getItems().addAll(role);
        txtSecretQuestion.getItems().addAll(secretQuestion);
    }

    /*
     * Enter account details to update
     */
    public void UpdateAccount(ActionEvent event) throws Exception{

        try {
            AccountHolder holder = AccountHolder.getInstance();
            User user = holder.getAccount();
            // if first name is empty, show error message
            if (txtFirstname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "First name is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if last name is empty, show error message
            else if (txtLastname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Last name is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if role is empty, show error message
            else if (txtRole.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Role is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if username is empty, show error message
            else if (txtUsername.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if password is empty, show error message
            else if (txtPassword.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if secret question is empty, show error message
            else if (txtSecretQuestion.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pick a secret question!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            // if answer is empty, show error message
            else if (txtAnswer.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Answer for secret question is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if(accountManagementModel.empIdExist(txtEmployerId.getText(), user.getEmployerId())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employer id exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if(accountManagementModel.usernameExist(txtUsername.getText(), user.getUsername())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else{
                // remove account first and add account again in database so same username can be reused
                // (employer id and username is unique)
                // Empoyer id and username cannot be changed
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to update this account?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.YES) {
                    alert.close();
                    accountManagementModel.updateAccount(txtEmployerId.getText(), txtFirstname.getText(), txtLastname.getText(), txtRole.getValue(), txtUsername.getText(), txtPassword.getText(), txtSecretQuestion.getValue(), txtAnswer.getText(), user);
                    main.change("ui/AdminAccountManagement.fxml");
                }
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
