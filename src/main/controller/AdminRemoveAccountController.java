package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminRemoveAccountController implements Initializable {

    private Main main = new Main();
    private AccountManagementModel accountManagementModel = new AccountManagementModel();
    @FXML
    private TableColumn<User, String> username;
    @FXML
    private TableColumn<User, String> password;
    @FXML
    private TableColumn<User, String> firstname;
    @FXML
    private TableColumn<User, String> lastname;
    @FXML
    private TableColumn<User, String> empId;
    @FXML
    private TableColumn<User, String> role;
    @FXML
    private TableColumn<User, String> question;
    @FXML
    private TableColumn<User, String> answer;
    @FXML
    private TableView<User> table;
    @FXML
    private String selectedRowId;
    @FXML
    private TextField txtUsername;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

        setTableColumns();
        User user = (User) Main.stage.getUserData();
        try {
            ObservableList<User> populateTableList = FXCollections.observableArrayList(accountManagementModel.getAllUser());
            table.getItems().addAll(populateTableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Home(ActionEvent event) throws Exception {

        main.change("ui/Home.fxml");
    }

    public void Profile(ActionEvent event) throws Exception {

        main.change("ui/UserProfile.fxml");
    }

    public void Logout(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }

    public void Back(ActionEvent event) throws Exception {

        main.change("ui/AccountManagement.fxml");
    }

    public void Search(ActionEvent event) throws Exception {

        User user = accountManagementModel.getUserByUsername(txtUsername.getText());
        if(user != null) {
            table.getItems().clear();
            ArrayList<User> users = new ArrayList<User>();
            users.add(user);
            ObservableList<User> populateTableList = FXCollections.observableArrayList(users);
            table.getItems().addAll(populateTableList);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username is invalid!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
                main.change("ui/AdminRemoveAccount.fxml");
            }
        }

    }

    public void Reset(ActionEvent event) throws Exception {

        main.change("ui/AdminRemoveAccount.fxml");
    }

    private void setTableColumns() {

        // specifying how to populate the columns of the table
        empId.setCellValueFactory(new PropertyValueFactory<User, String>("employerId"));
        username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        firstname.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastname.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        role.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        question.setCellValueFactory(new PropertyValueFactory<User, String>("question"));
        answer.setCellValueFactory(new PropertyValueFactory<User, String>("answer"));
    }



    public boolean isSelectedRowValid(String selectedRowId) {
        return selectedRowId != null ? true : false;
    }


    public void getRowId(MouseEvent e) {

        // storing the id number of the selected row in a String
        if (table.getSelectionModel().getSelectedItem() != null)
            this.selectedRowId = table.getSelectionModel().getSelectedItem().getEmployerId();
    }

    public void deleteBooking(ActionEvent event) throws Exception {

        if(table.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to remove this account?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            // if the user clicks OK

            if (alert.getResult() == ButtonType.YES) {
                if (isSelectedRowValid(selectedRowId)) {
                    accountManagementModel.removeAccount(selectedRowId);
                    main.change("ui/AdminRemoveAccount.fxml");
                    alert.close();
                }
            }
            else if (alert.getResult() == ButtonType.NO) {
                alert.close();
            }
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick an account before removing!", ButtonType.CLOSE);
            alert2.showAndWait();
            if (alert2.getResult() == ButtonType.CLOSE)
                alert2.close();
        }
    }

}

