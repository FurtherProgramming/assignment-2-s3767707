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
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/*
 * Class:		UserCheckInController
 * Description:	A class that handles user check in page
 * Author:		Anson Go Guang Ping
 */
public class UserCheckInController implements Initializable {
    private UserCheckInModel userCheckInModel = new UserCheckInModel();
    private Main main = new Main();
    private UserEditBookingModel userEditBookingModel = new UserEditBookingModel();

    @FXML
    private Label label;
    @FXML
    private Label label2;
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
    private TableColumn<Booking, String> checkIn;
    @FXML
    private TableView<Booking> table;
    @FXML
    private String selectedRowId;
    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

        label.setText(LocalDate.now().toString());
        label2.setText(String.valueOf(LocalDateTime.now().getHour())+":"+ String.valueOf(LocalDateTime.now().getMinute()));
        setTableColumns();
        User user = (User) Main.stage.getUserData();
        try {
            // only show today's accepted bookings so user can only pick from it
            ObservableList<Booking> populateTableList = FXCollections.observableArrayList(userCheckInModel.getUserBooking(user.getUsername(), "Accepted", LocalDate.now()));
            table.getItems().addAll(populateTableList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setTableColumns() {

        // specifying how to populate the columns of the table
        bookingId.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingId"));
        username.setCellValueFactory(new PropertyValueFactory<Booking, String>("username"));
        seatId.setCellValueFactory(new PropertyValueFactory<Booking, String>("seatId"));
        bookingDate.setCellValueFactory(new PropertyValueFactory<Booking, LocalDate>("bookingDate"));
        bookingTime.setCellValueFactory(new PropertyValueFactory<Booking, String>("bookingTime"));
        status.setCellValueFactory(new PropertyValueFactory<Booking, String>("status"));
        checkIn.setCellValueFactory(new PropertyValueFactory<Booking, String>("checkIn"));
    }

    public boolean isSelectedRowValid(String selectedRowId) {
        return selectedRowId != null ? true : false;
    }


    public void getRowId(MouseEvent e) {

        // storing the id number of the selected row in a String
        if (table.getSelectionModel().getSelectedItem() != null)
            this.selectedRowId = table.getSelectionModel().getSelectedItem().getBookingId();
    }

    public void Profile(ActionEvent event) throws Exception {

        main.change("ui/UserProfile.fxml");
    }


    public void Home(ActionEvent event) throws Exception {

        main.change("ui/Home.fxml");
    }

    public void Logout(ActionEvent event) throws Exception {

        main.change("ui/Login.fxml");
    }

    public void Back(ActionEvent event) throws Exception {

        main.change("ui/UserProfile.fxml");
    }

    public void CheckIn(ActionEvent event) throws Exception {

        if(table.getSelectionModel().getSelectedItem() != null) {

                if (isSelectedRowValid(selectedRowId)) {
                    // check if the current time is within the booking session
                    if(userCheckInModel.isTime(selectedRowId, LocalDateTime.now().getHour())) {
                        // user can check in on a checked in a booking
                        if(!userCheckInModel.isCheckedIn(selectedRowId)) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to check-in with this booking?", ButtonType.YES, ButtonType.NO);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.YES) {
                                userCheckInModel.checkIn(selectedRowId);
                                main.change("ui/UserCheckIn.fxml");
                            }
                            else {
                                alert.close();
                                main.change("ui/UserCheckIn.fxml");
                            }
                        }
                        else {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Seat already checked-in!", ButtonType.CLOSE);
                            alert.showAndWait();
                            if (alert.getResult() == ButtonType.CLOSE) {
                                alert.close();
                                main.change("ui/UserCheckIn.fxml");
                            }
                        }
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Seat not opened! Check your booking time!", ButtonType.CLOSE);
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.CLOSE) {
                            alert.close();
                            main.change("ui/UserCheckIn.fxml");
                        }
                    }
                }
        }
        else {
            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick a booking!", ButtonType.CLOSE);
            alert2.showAndWait();
            if (alert2.getResult() == ButtonType.CLOSE) {
                alert2.close();
                main.change("ui/UserCheckIn.fxml");
            }
        }
    }



}
