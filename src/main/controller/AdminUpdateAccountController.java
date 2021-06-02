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
import main.model.AccountHolder;
import main.model.AccountManagementModel;
import main.model.RegisterModel;
import main.model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class AdminUpdateAccountController implements Initializable {
    public AccountManagementModel accountManagementModel = new AccountManagementModel();
    private RegisterModel registerModel = new RegisterModel();
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

    public void UpdateAccount(ActionEvent event) throws Exception{

        try {
            if (txtEmployerId.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employer id is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (txtFirstname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "First name is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (txtLastname.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Last name is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (txtRole.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Role is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (txtUsername.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (txtPassword.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Password is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (txtSecretQuestion.getValue() == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pick a secret question!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (txtAnswer.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Answer for secret question is required!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (accountManagementModel.empIdValid(txtEmployerId.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employer id exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (accountManagementModel.usernameValid(txtUsername.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else{
                AccountHolder holder = AccountHolder.getInstance();
                User user = holder.getAccount();
                accountManagementModel.removeAccount(user.getEmployerId());
                registerModel.register(txtEmployerId.getText(), txtFirstname.getText(), txtLastname.getText(), txtRole.getValue(), txtUsername.getText(), txtPassword.getText(), txtSecretQuestion.getValue(), txtAnswer.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Update account successfully!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
                main.change("ui/AccountManagement.fxml");
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
