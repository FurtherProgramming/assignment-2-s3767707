package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import main.Main;
import main.model.RegisterModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
    public RegisterModel registerModel = new RegisterModel();
    public Main main = new Main();
    @FXML
    private Label isConnected;
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

        if (registerModel.isDbConnected()){
            isConnected.setText("Connected to Database");
        }else{
            isConnected.setText("Not Connected to Database");
        }
        addItemToChoiceBox();
    }

    public void addItemToChoiceBox() {

        ObservableList role = FXCollections.observableArrayList();
        ObservableList secretQuestion = FXCollections.observableArrayList();

        role.addAll("admin", "user");
        secretQuestion.addAll("What is your favourite colour?");
        txtRole.getItems().addAll(role);
        txtSecretQuestion.getItems().addAll(secretQuestion);
    }

    public void Register(ActionEvent event) throws Exception{

        try {
            if (txtEmployerId.getText().isEmpty()) {
                isConnected.setText("Employer id is required");
            }
            else if (txtFirstname.getText().isEmpty()) {
                isConnected.setText("First name is required");
            }
            else if (txtLastname.getText().isEmpty()) {
                isConnected.setText("Last name is required");
            }
            else if (txtRole.getValue() == null) {
                isConnected.setText("Role is required");
            }
            else if (txtUsername.getText().isEmpty()) {
                isConnected.setText("Username is required");
            }
            else if (txtPassword.getText().isEmpty()) {
                isConnected.setText("Password is required");
            }
            else if (txtSecretQuestion.getValue() == null) {
                isConnected.setText("Secret question is required");
            }
            else if (txtAnswer.getText().isEmpty()) {
                isConnected.setText("Answer for secret question is required");
            }
            else if (registerModel.employerIdExist(txtEmployerId.getText())){
                isConnected.setText("Employer id exists.");
            }
            else if (registerModel.usernameExist(txtUsername.getText())){
                isConnected.setText("Username exists.");
            }
            else{

                registerModel.register(txtEmployerId.getText(), txtFirstname.getText(), txtLastname.getText(), txtRole.getValue(), txtUsername.getText(), txtPassword.getText(), txtSecretQuestion.getValue(), txtAnswer.getText());
                isConnected.setText("Register successfully");
                main.change("ui/login.fxml");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
