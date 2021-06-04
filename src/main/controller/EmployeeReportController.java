package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main.Main;
import main.model.AccountManagementModel;
import main.model.AdminReportModel;
import main.model.User;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/*
 * Class:		EmployeeReportController
 * Description:	A class that handles admin generate employee report page
 * Author:		Anson Go Guang Ping
 */
public class EmployeeReportController implements Initializable {
    private Main main = new Main();
    private AccountManagementModel accountManagementModel = new AccountManagementModel();
    private AdminReportModel adminReportModel = new AdminReportModel();
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


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

        setTableColumns();
        try {
            // show table of all users
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

        main.change("ui/AdminReport.fxml");
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

    public void Export(ActionEvent event) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you confirm to export Employee table to csv?!", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            adminReportModel.exportEmployeeTable();
            alert.close();
            main.change("ui/AdminReport.fxml");
        }
        else {
            alert.close();
        }
    }

}

