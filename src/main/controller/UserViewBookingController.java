package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import main.Main;
import main.model.*;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Class:		UserCancelBookingController
 * Description:	A class that handles user cancel booking page
 * Author:		Anson Go Guang Ping
 */
public class UserViewBookingController implements Initializable {

    private Main main = new Main();
    private UserEditBookingModel userEditBookingModel = new UserEditBookingModel();
    private AdminEditBookingModel adminEditBookingModel = new AdminEditBookingModel();
    private UserBookingModel userBookingModel = new UserBookingModel();
    private ArrayList<String> seats = new ArrayList<String>();
    private SeatManagementModel seatManagementModel = new SeatManagementModel();
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
    public void initialize(URL location, ResourceBundle resources) {

        setTableColumns();
        User user = (User) Main.stage.getUserData();


        try {
            // sort the table from booking status "Accepted"->"Pending"->"Rejected" after each cancellation
            ObservableList<Booking> populateTableListAccepted = FXCollections.observableArrayList(userEditBookingModel.getUserBookings(user.getUsername(), "Accepted"));
            ObservableList<Booking> populateTableListPending = FXCollections.observableArrayList(userEditBookingModel.getUserBookings(user.getUsername(), "Pending"));
            ObservableList<Booking> populateTableListRejected = FXCollections.observableArrayList(userEditBookingModel.getUserBookings(user.getUsername(), "Rejected"));
            table.getItems().addAll(populateTableListAccepted);
            table.getItems().addAll(populateTableListPending);
            table.getItems().addAll(populateTableListRejected);
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


    public boolean validateBookingCancellation() {

        // getting current date for reference
        LocalDate currentDate = LocalDate.now();
        // retrieving date from the table and saving it as String
        LocalDate selectedBookingDate = table.getSelectionModel().getSelectedItem().getBookingDate();
        // comparing current date with booking date
        int dateComparison = selectedBookingDate.compareTo(currentDate);
        return dateComparison >= 0 ? true : false;
    }

    public void CancelBooking(ActionEvent event) throws Exception {

        User u = (User) Main.stage.getUserData();
        // oonly activated account can cancel booking
        if (u.getStatus().equals("activated")) {
            main.change("ui/UserCheckIn.fxml");
            if (table.getSelectionModel().getSelectedItem() != null) { // if no item selected
                boolean validStatus = table.getSelectionModel().getSelectedItem().getStatus().equals("Accepted") || table.getSelectionModel().getSelectedItem().getStatus().equals("Pending");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to cancel this booking?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                // if the user clicks OK

                if (alert.getResult() == ButtonType.YES) {
                    if (isSelectedRowValid(selectedRowId)) {
                        if (validStatus) { // user can only cancel "Pending" or "Accepted" bookings
                            if (validateBookingCancellation()) { // comparing booking's date with the current date
                                if (userEditBookingModel.checkHourBeforeEdit(table.getSelectionModel().getSelectedItem().getBookingDate(), table.getSelectionModel().getSelectedItem().getBookingTime(), LocalDate.now(), LocalDateTime.now())) { // check if changes is not within 48 hours of booking date
                                    adminEditBookingModel.editBookingStatus(selectedRowId, "Rejected");
                                    main.change("ui/UserViewBooking.fxml");
                                    alert.close();
                                } else {
                                    Alert alert2 = new Alert(Alert.AlertType.ERROR, "You cannot cancel a booking within 48 hours!", ButtonType.CLOSE);
                                    alert2.showAndWait();
                                    if (alert2.getResult() == ButtonType.CLOSE)
                                        alert2.close();
                                }
                            } else {
                                Alert alert2 = new Alert(Alert.AlertType.ERROR, "You cannot cancel an old booking!", ButtonType.CLOSE);
                                alert2.showAndWait();
                                if (alert2.getResult() == ButtonType.CLOSE)
                                    alert2.close();
                            }
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Booking already rejected!", ButtonType.CLOSE);
                            alert2.showAndWait();
                            if (alert2.getResult() == ButtonType.CLOSE)
                                alert2.close();
                        }
                    }
                } else if (alert.getResult() == ButtonType.NO) {
                    alert.close();
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick a booking!", ButtonType.CLOSE);
                alert2.showAndWait();
                if (alert2.getResult() == ButtonType.CLOSE)
                    alert2.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Account deactivated. Please contact admin to reactivate your account!!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
    }

    public void updateBooking(ActionEvent event) throws Exception {

        User u = (User) Main.stage.getUserData();
        // only activated account can update booking
        if (u.getStatus().equals("activated")) {
            if (table.getSelectionModel().getSelectedItem() != null) {
                boolean dateEqualStart = table.getSelectionModel().getSelectedItem().getBookingDate().isEqual(userBookingModel.getConditionStartDate());
                boolean dateEqualEnd = table.getSelectionModel().getSelectedItem().getBookingDate().isEqual(userBookingModel.getConditionEndDate());
                boolean dateBetween = table.getSelectionModel().getSelectedItem().getBookingDate().isAfter(userBookingModel.getConditionStartDate()) && table.getSelectionModel().getSelectedItem().getBookingDate().isBefore(userBookingModel.getConditionEndDate());
                boolean validStatus = table.getSelectionModel().getSelectedItem().getStatus().equals("Pending");
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you wish to update this booking?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();
                // if the user clicks OK
                if (alert.getResult() == ButtonType.YES) {
                    if (isSelectedRowValid(selectedRowId)) {
                        if (validStatus) { // user can only update pending bookings
                            if (validateBookingCancellation()) {
                                if (userEditBookingModel.checkHourBeforeEdit(table.getSelectionModel().getSelectedItem().getBookingDate(), table.getSelectionModel().getSelectedItem().getBookingTime(), LocalDate.now(), LocalDateTime.now())) { // check changes is not within 48 hours
                                    User user = (User) Main.stage.getUserData();
                                    String username = user.getUsername();
                                    String bookingId = table.getSelectionModel().getSelectedItem().getBookingId();
                                    String seatId = table.getSelectionModel().getSelectedItem().getSeatId();
                                    LocalDate date = table.getSelectionModel().getSelectedItem().getBookingDate();
                                    String status = table.getSelectionModel().getSelectedItem().getStatus();
                                    String time = table.getSelectionModel().getSelectedItem().getBookingTime();
                                    String check = table.getSelectionModel().getSelectedItem().getCheckIn();
                                    Booking booking = new Booking(bookingId, username, seatId, date, status, time, check);
                                    String seat = userBookingModel.previousBooking(user.getUsername());
                                    ArrayList<String> temps = new ArrayList<String>();
                                    temps = userBookingModel.isBooked(date, time);
                                    seats.add(seat);
                                    ArrayList<String> seatIds = userBookingModel.allSeatId();
                                    for (String temp : temps) {
                                        seats.add(temp);
                                    }
                                    // if current date affected by COVID restrictions, set seat color
                                    if (dateEqualStart || dateEqualEnd || dateBetween) {
                                        ArrayList<Seat> lockedSeats = seatManagementModel.getAllSeats();
                                        main.displaySeatsWithCondition("ui/UserUpdateBooking.fxml", booking, seats, seatIds, lockedSeats);
                                    } else {

                                        ArrayList<Seat> lockedSeats = null;
                                        main.displaySeatsWithCondition("ui/UserUpdateBooking.fxml", booking, seats, seatIds, lockedSeats);
                                    }
                                } else {
                                    Alert alert2 = new Alert(Alert.AlertType.ERROR, "You cannot change a booking within 48 hours!", ButtonType.CLOSE);
                                    alert2.showAndWait();
                                    if (alert2.getResult() == ButtonType.CLOSE)
                                        alert2.close();
                                }
                            } else {
                                Alert alert2 = new Alert(Alert.AlertType.ERROR, "You cannot cancel an old booking!", ButtonType.CLOSE);
                                alert2.showAndWait();
                                if (alert2.getResult() == ButtonType.CLOSE)
                                    alert2.close();
                            }
                        } else {
                            Alert alert2 = new Alert(Alert.AlertType.ERROR, "Booking cannot be changed! Only pick pending bookings!!", ButtonType.CLOSE);
                            alert2.showAndWait();
                            if (alert2.getResult() == ButtonType.CLOSE)
                                alert2.close();
                        }
                    }
                } else if (alert.getResult() == ButtonType.NO) {
                    alert.close();
                }
            } else {
                Alert alert2 = new Alert(Alert.AlertType.ERROR, "Please pick an booking!", ButtonType.CLOSE);
                alert2.showAndWait();
                if (alert2.getResult() == ButtonType.CLOSE)
                    alert2.close();
            }
            main.change("ui/UserCheckIn.fxml");
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Account deactivated. Please contact admin to reactivate your account!!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
    }
}

