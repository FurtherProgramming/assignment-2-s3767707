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
import main.model.AccountManagementModel;
import main.model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Class:		AdminRemoveAccountController
 * Description:	A class that handles admin remove account page
 * Author:		Anson Go Guang Ping
 */
public class AdminAccountManagementController implements Initializable {

    private final Main main = new Main();
    private final AccountManagementModel accountManagementModel = new AccountManagementModel();
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
    private TableColumn<User, String> status;
    @FXML
    private TableView<User> table;
    @FXML
    private String selectedRowId;
    @FXML
    private TextField txtUsername;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setTableColumns();
        User user = (User) Main.stage.getUserData();
        try {
            // show all accounts in table
            ObservableList<User> populateTableList = FXCollections.observableArrayList(accountManagementModel.getAllUser(user.getUsername()));
            table.getItems().addAll(populateTableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void Home(ActionEvent event) throws Exception {

        main.change("ui/Home.fxml");
    }

    public void Profile(ActionEvent event) throws Exception {

        main.change("ui/AdminProfile.fxml");
    }

    public void Logout(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }

    public void Back(ActionEvent event) throws Exception {

        main.change("ui/AdminProfile.fxml");
    }

    /*
     * Search account by entering username
     */
    public void Search(ActionEvent event) throws Exception {

        User user = accountManagementModel.getUserByUsername(txtUsername.getText());
        if (user != null) {
            table.getItems().clear();
            ArrayList<User> users = new ArrayList<User>();
            users.add(user);
            ObservableList<User> populateTableList = FXCollections.observableArrayList(users);
            table.getItems().addAll(populateTableList);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username is invalid!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
                main.change("ui/AdminAccountManagement.fxml");
            }
        }

    }

    public void Reset(ActionEvent event) throws Exception {

        main.change("ui/AdminAccountManagement.fxml");
    }

    private void setTableColumns() {

        // specifying how to populate the columns of the table
        empId.setCellValueFactory(new PropertyValueFactory<User, String>("employeeId"));
        username.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        password.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
        firstname.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        lastname.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        role.setCellValueFactory(new PropertyValueFactory<User, String>("role"));
        question.setCellValueFactory(new PropertyValueFactory<User, String>("question"));
        answer.setCellValueFactory(new PropertyValueFactory<User, String>("answer"));
        status.setCellValueFactory(new PropertyValueFactory<User, String>("status"));
    }


    public boolean isSelectedRowValid(String selectedRowId) {
        return selectedRowId != null;
    }


    public void getRowId(MouseEvent e) {

        // storing the id number of the selected row in a String
        if (table.getSelectionModel().getSelectedItem() != null)
            this.selectedRowId = table.getSelectionModel().getSelectedItem().getEmployeeId();
    }

    /*
     * handle remove account button
     */
    public void Delete(ActionEvent event) throws Exception {

        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to remove this account?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (isSelectedRowValid(selectedRowId)) {
                    //remove user and all its bookings from database
                    accountManagementModel.removeAccount(selectedRowId); // remove account
                    accountManagementModel.removeBookings(table.getSelectionModel().getSelectedItem().getUsername()); // remove all bookings associated with this account
                    main.change("ui/AdminAccountManagement.fxml");
                    alert.close();
                }
            } else if (alert.getResult() == ButtonType.NO) {
                alert.close();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick an account before removing!", ButtonType.CLOSE);
            alert2.showAndWait();
            if (alert2.getResult() == ButtonType.CLOSE)
                alert2.close();
        }
    }

    /*
     * directs admin to add account page if a table row is selected
     */
    public void Add(ActionEvent event) throws Exception {

        main.change("ui/AdminAddAccount.fxml");
    }

    /*
     * directs admin to update account page if a table row is selected
     */
    public void Update(ActionEvent event) throws Exception {

        if (table.getSelectionModel().getSelectedItem() != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to update this account?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                if (isSelectedRowValid(selectedRowId)) {
                    User user = accountManagementModel.getUserById(selectedRowId);
                    main.passAccount("ui/AdminUpdateAccount.fxml", user);
                    alert.close();
                }
            } else if (alert.getResult() == ButtonType.NO) {
                alert.close();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick an account before updating!", ButtonType.CLOSE);
            alert2.showAndWait();
            if (alert2.getResult() == ButtonType.CLOSE)
                alert2.close();
        }
    }

    /*
     * activate or deactivate account
     */
    public void ActivateOrDeactivate(ActionEvent event) throws Exception {

        if (table.getSelectionModel().getSelectedItem() != null) {
            if(!table.getSelectionModel().getSelectedItem().getRole().equals("admin")) {
                if (table.getSelectionModel().getSelectedItem().getStatus().equals("activated")) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to deactivate this account?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        accountManagementModel.activateOrDeactivate(selectedRowId, "deactivated"); // deactivate account
                        main.change("ui/AdminAccountManagement.fxml");
                        alert.close();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Account deactivated!", ButtonType.CLOSE);
                        alert2.showAndWait();
                        if (alert2.getResult() == ButtonType.CLOSE)
                            alert2.close();
                    } else {
                        alert.close();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to activate this account?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        accountManagementModel.activateOrDeactivate(selectedRowId, "activated"); // activate account
                        main.change("ui/AdminAccountManagement.fxml");
                        alert.close();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Account activated!", ButtonType.CLOSE);
                        alert2.showAndWait();
                        if (alert2.getResult() == ButtonType.CLOSE)
                            alert2.close();
                    } else {
                        alert.close();
                    }
                }
            }
            else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "You cannot deactivate an admin account!", ButtonType.CLOSE);
                alert2.showAndWait();
                if (alert2.getResult() == ButtonType.CLOSE)
                    alert2.close();
            }
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick an account before activating or deactivating!", ButtonType.CLOSE);
            alert2.showAndWait();
            if (alert2.getResult() == ButtonType.CLOSE)
                alert2.close();
        }
    }
}

