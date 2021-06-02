package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import main.Main;
import main.model.RegisterModel;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class RegisterController implements Initializable {
    public RegisterModel registerModel = new RegisterModel();
    public Main main = new Main();
    @FXML
    private TextField txtEmployerId;
    @FXML
    private TextField txtFirstname;
    @FXML
    private TextField txtLastname;
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

        ObservableList secretQuestion = FXCollections.observableArrayList();
        ArrayList<String> secretQuestions = new ArrayList<String>();
        secretQuestions.add("What is your favourite colour?");
        secretQuestions.add("What is your favourite food?");
        secretQuestion.addAll(secretQuestions);
        txtSecretQuestion.getItems().addAll(secretQuestion);
    }

    public void Register(ActionEvent event) throws Exception{

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
            else if (registerModel.employerIdExist(txtEmployerId.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Employer id exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else if (registerModel.usernameExist(txtUsername.getText())){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Username exists!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE)
                    alert.close();
            }
            else{
                registerModel.register(txtEmployerId.getText(), txtFirstname.getText(), txtLastname.getText(), "user", txtUsername.getText(), txtPassword.getText(), txtSecretQuestion.getValue(), txtAnswer.getText());
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
