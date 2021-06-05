package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Main;
import main.model.*;

import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*
 * Class:		UserBookingController
 * Description:	A class that handles user book seats function
 * Author:		Anson Go Guang Ping
 */
public class UserBookingController implements Initializable {
    private UserBookingModel userBookingModel = new UserBookingModel();
    private SeatManagementModel seatManagementModel = new SeatManagementModel();
    private Main main = new Main();
    private ArrayList<String> seats = new ArrayList<String>();
    private ArrayList<String> allSeatId = new ArrayList<String>();
    private User user = (User) Main.stage.getUserData();
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox book_time;

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
        book_time.getItems().addAll(timeList);
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


    public void Search(ActionEvent event) throws Exception {

        if (datePicker.getValue() != null) { // if date choosed
            if (book_time.getValue() != null) { //if book time choosed
                if (datePicker.getValue().isAfter(LocalDate.now())) {//if date choosed is after current date
                    if (!userBookingModel.UsernameExistInList(user.getUsername(), datePicker.getValue(), book_time.getValue().toString())) {// user can only have one bookings per session, applies to pending  bookings too to avoid spamming
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Click on seats to book!", ButtonType.CLOSE);
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.CLOSE) {
                            alert.close();
                        }
                        Booking booking = new Booking(null, user.getUsername(), null, datePicker.getValue(), "Pending", book_time.getValue().toString(), "N");
                        ArrayList<String> bookedSeats = userBookingModel.isBooked(datePicker.getValue(), book_time.getValue().toString());
                        bookedSeats.add(userBookingModel.previousBooking(user.getUsername()));// user cannot book the same seat as previous bookings
                        // get seats beside same Employees that have been sitten previously
                        Booking prevBooking = userBookingModel.previousSit(user.getUsername());
                        if (prevBooking != null) {
                            ArrayList<String> usernames = userBookingModel.getAdjacentUserOfPreviousSit(prevBooking.getBookingDate(), prevBooking.getBookingTime(), prevBooking.getSeatId());
                            if (usernames != null) {
                                ArrayList<String> SeatsBookedByPrevUsers = userBookingModel.getSeatsOfPreviousAdjacentUser(datePicker.getValue(), book_time.getValue().toString(), usernames);
                                ArrayList<String> SeatsBesidePrevUsers = userBookingModel.SeatsBesidePrevUser(SeatsBookedByPrevUsers);
                                for (String s : SeatsBesidePrevUsers) {
                                    bookedSeats.add(s);
                                }
                            }
                        }
                        ArrayList<String> allSeats = userBookingModel.allSeatId();
                        // get locked seats due to COVID
                        ArrayList<Seat> lockedSeat = seatManagementModel.getAllSeats();
                        // All Booked seat, user previous booked seat and seats affected by COVID are stored in arrays and change seat color based on arrays' elements
                        main.displaySeatsWithCondition("ui/UserBooking.fxml", booking, bookedSeats, allSeats, lockedSeat);
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Multiple bookings in a session not allowed!", ButtonType.CLOSE);
                        alert.showAndWait();
                        if (alert.getResult() == ButtonType.CLOSE) {
                            alert.close();
                            main.change("ui/UserBooking.fxml");
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Booking date cannot be today or past!", ButtonType.CLOSE);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.CLOSE) {
                        alert.close();
                        main.change("ui/UserBooking.fxml");
                    }
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pick booking time!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE) {
                    alert.close();
                    main.change("ui/UserBooking.fxml");
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Pick booking date!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
                main.change("ui/UserBooking.fxml");
            }
        }
    }

    /*
     * User can only book green seats, clicking orange and red seat will show error message
     */
    public void Book(MouseEvent event) throws Exception {

        Rectangle rectangle = (Rectangle) event.getSource();
        if (rectangle.getFill() == Color.RED) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seat booked. Please pick again!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        } else if (rectangle.getFill() == Color.ORANGE) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seat locked. Please pick again!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        } else if (rectangle.getFill() == Color.WHITE) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Click on the search button!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Confirm booking?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.YES) {
                String username = user.getUsername();
                String seatId = rectangle.getId();
                String bookingId = generateId(4) + seatId;
                BookingHolder holder = BookingHolder.getInstance();
                Booking booking = holder.getBooking();
                booking.setBookingId(bookingId);
                booking.setSeatId(seatId);
                userBookingModel.book(bookingId, seatId, booking.getBookingDate(), username, booking.getBookingTime(), "N");
                alert.close();
                main.change("ui/UserProfile.fxml");
            } else {
                alert.close();
            }
        }
    }

    /*
     * generate random string for booking id
     */
    public String generateId(int len) {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
}
