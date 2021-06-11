package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Main;
import main.model.Booking;
import main.model.BookingHolder;
import main.model.UserEditBookingModel;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/*
 * Class:		UserUpdateBookingController
 * Description:	A class that handles user update booking page
 * Author:		Anson Go Guang Ping
 */
public class UserUpdateBookingController implements Initializable {

    private UserEditBookingModel userEditBookingModel = new UserEditBookingModel();
    private Main main = new Main();


    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }



    public void Back(ActionEvent event) throws Exception {

        main.change("ui/UserViewBooking.fxml");
    }

    /*
     * User can click on the seat to pick
     */
    public void Update(MouseEvent event) throws Exception {

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
            LocalDate date = booking.getBookingDate();
            if (alert.getResult() == ButtonType.YES) {
                userEditBookingModel.updateBooking(bookingId, seatId, time, date);
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "Booking updated successfully!", ButtonType.CLOSE);
                alert2.showAndWait();
                if (alert2.getResult() == ButtonType.CLOSE) {
                    alert2.close();
                }
                main.change("ui/UserViewBooking.fxml");
            } else {
                alert.close();
            }
        }
    }
}
