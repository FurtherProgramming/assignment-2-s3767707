package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Main;
import main.model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Class:		UserUpdateBookingController
 * Description:	A class that handles user update booking page
 * Author:		Anson Go Guang Ping
 */
public class UserUpdateBookingController implements Initializable {

    private UserBookingModel userBookingModel = new UserBookingModel();
    private UserEditBookingModel userEditBookingModel = new UserEditBookingModel();
    private SeatManagementModel seatManagementModel = new SeatManagementModel();
    private Main main = new Main();
    private ArrayList<String> seats = new ArrayList<String>();
    @FXML
    private ChoiceBox time;
    @FXML
    private Label date;

    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addItemToChoiceBox();
    }

    public void addItemToChoiceBox() {

        ObservableList timeList = FXCollections.observableArrayList();
        ArrayList<String> times = new ArrayList<String>();
        times.add("0800");
        times.add("1400");
        timeList.addAll(times);
        time.getItems().addAll(timeList);
    }

    public void Back(ActionEvent event) throws Exception {

        main.change("ui/UserViewBooking.fxml");
    }

    /*
     * User can click on the seat to pick
     */
    public void Book(MouseEvent event) throws Exception {

        Rectangle rectangle = (Rectangle) event.getSource();
        // if rectangle is red, it is booked by others
        if (rectangle.getFill() == Color.RED) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seat unavailable. Please pick again!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
        // if rectangle is red, it is locked due to COVID restrictions
        else if (rectangle.getFill() == Color.ORANGE) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seat locked. Please pick again!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        } else {

            BookingHolder holder = BookingHolder.getInstance();
            Booking booking = holder.getBooking();
            String seatId = rectangle.getId();
            booking.setSeatId(seatId);
            String bookingId = booking.getBookingId();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to update this booking?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            String time = booking.getBookingTime();
            if (alert.getResult() == ButtonType.YES) {
                userEditBookingModel.updateBooking(bookingId, seatId, time);
                main.change("ui/UserViewBooking.fxml");
            } else {
                alert.close();
            }
        }
    }

    /*
     *  Handles search button to show available seats
     */
    public void Search(ActionEvent event) throws Exception {

        BookingHolder holder = BookingHolder.getInstance();
        Booking booking = holder.getBooking();
        boolean dateEqualStart = booking.getBookingDate().isEqual(userBookingModel.getConditionStartDate());
        boolean dateEqualEnd = booking.getBookingDate().isEqual(userBookingModel.getConditionEndDate());
        boolean dateBetween = booking.getBookingDate().isAfter(userBookingModel.getConditionStartDate()) && booking.getBookingDate().isBefore(userBookingModel.getConditionEndDate());
        if (time.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Pick your time!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        } else {

            booking.setBookingTime(time.getValue().toString());
            User user = (User) Main.stage.getUserData();
            String username = user.getUsername();
            String time2 = time.getValue().toString();
            String status = "Pending";
            // user cannot book the same seats from previous accepted bookings
            String seatId = userBookingModel.previousBooking(user.getUsername());
            ArrayList<String> temps = new ArrayList<String>();
            // temps array stored all booked seats at that day and user previous booked seats and seat ids in it are set red later
            temps = userBookingModel.isBooked(booking.getBookingDate(), time2);
            seats.add(seatId);
            ArrayList<String> seatIds = userBookingModel.allSeatId(); // get all seat ids and set it green later
            for (String temp : temps) {
                seats.add(temp);
            }
            // if search date is between COVID restriction duration
            if (dateEqualStart || dateEqualEnd || dateBetween) {
                ArrayList<Seat> lockedSeats = seatManagementModel.getAllSeats(); // get all locked seat ids and set them orange later
                main.displaySeatsWithCondition("ui/UserUpdateBooking.fxml", booking, seats, seatIds, lockedSeats); // set all seats based on the arrays
            } else {
                ArrayList<Seat> lockedSeats = null;
                main.displaySeatsWithCondition("ui/UserUpdateBooking.fxml", booking, seats, seatIds, lockedSeats);
            }
        }
    }


}
