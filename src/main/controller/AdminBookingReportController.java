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
import main.model.AdminReportModel;
import main.model.Booking;
import main.model.User;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Class:		BookingReportController
 * Description:	A class that handles admin generate booking report page
 * Author:		Anson Go Guang Ping
 */
public class AdminBookingReportController implements Initializable {

    @FXML
    public ObservableList<Booking> populateTableList;
    private Main main = new Main();
    private AdminReportModel adminReportModel = new AdminReportModel();
    @FXML
    private TableColumn<Booking, String> bookingId;
    @FXML
    private TableColumn<Booking, String> username;
    @FXML
    private TableColumn<Booking, String> seatId;
    @FXML
    private TableColumn<Booking, LocalDate> bookingDate;
    @FXML
    private TableColumn<Booking, String> status;
    @FXML
    private TableColumn<Booking, String> bookingTime;
    @FXML
    private TableView<Booking> table;
    @FXML
    private String selectedRowId;
    @FXML
    private DatePicker date;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setTableColumns();
        User user = (User) Main.stage.getUserData();


        try {
            //show all accepted bookings in table
            populateTableList = FXCollections.observableArrayList(adminReportModel.getAllBookings());
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

        main.change("ui/AdminReport.fxml");
    }

    private void setTableColumns() {

        // specifying how to populate the columns of the table
        bookingId.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingId"));
        username.setCellValueFactory(new PropertyValueFactory<Booking, String>("username"));
        seatId.setCellValueFactory(new PropertyValueFactory<Booking, String>("seatId"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<Booking, LocalDate>("bookingDate"));
        bookingTime.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingTime"));
        status.setCellValueFactory(new PropertyValueFactory<Booking, String>("status"));
    }

    public void getRowId(MouseEvent e) {

        // storing the id number of the selected row in a String
        if (table.getSelectionModel().getSelectedItem() != null)
            this.selectedRowId = table.getSelectionModel().getSelectedItem().getBookingId();
    }

    public void Export(ActionEvent event) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you confirm to export Booking table to csv?!", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
            if (date.getValue() != null) { //if admin enter date
                adminReportModel.exportBookingTableWithDate(date.getValue()); // export all bookings on that day into csv file
                alert.close();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Booking report exported successfully!", ButtonType.CLOSE);
                alert2.showAndWait();
                if (alert2.getResult() == ButtonType.CLOSE)
                    alert2.close();
                main.change("ui/AdminBookingReport.fxml");
            } else {
                adminReportModel.exportBookingTable(); // export all bookings into csv file
                alert.close();
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Booking report exported successfully!", ButtonType.CLOSE);
                alert2.showAndWait();
                if (alert2.getResult() == ButtonType.CLOSE)
                    alert2.close();
                main.change("ui/AdminBookingReport.fxml");
            }
        } else {
            alert.close();
        }
    }

    public void Search(ActionEvent event) throws Exception {

        if (date.getValue() != null) {
            table.getItems().clear(); // clear table to avoid stacking
            // show table of accepted bookings with booking date searched
            ArrayList<Booking> bookings = adminReportModel.getAllBookingsWithDate(date.getValue());
            ObservableList<Booking> populateTableList = FXCollections.observableArrayList(bookings);
            table.getItems().addAll(populateTableList);
        } else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Pick a booking date before searching!", ButtonType.CLOSE);
            alert2.showAndWait();
            if (alert2.getResult() == ButtonType.CLOSE)
                alert2.close();
            table.getItems().clear();
            // show table of all accepted bookings
            ArrayList<Booking> bookings = adminReportModel.getAllBookings();
            ObservableList<Booking> populateTableList = FXCollections.observableArrayList(bookings);
            table.getItems().addAll(populateTableList);
        }
    }

    public void Reset(ActionEvent event) throws Exception {

        main.change("ui/AdminBookingReport.fxml");
    }
}
