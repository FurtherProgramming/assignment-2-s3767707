package main.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Main;
import main.model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserUpdateBookingController implements Initializable {

    private UserBookingModel userBookingModel = new UserBookingModel();
    private UserEditBookingModel userEditBookingModel = new UserEditBookingModel();
    private Main main = new Main();
    private ArrayList<String> seats = new ArrayList<String>();
    @FXML
    private ChoiceBox time;
    @FXML
    private Label date;
    // Check database connection
    @Override
    public void initialize(URL location, ResourceBundle resources){

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

        main.change("ui/UserRemoveBooking.fxml");
    }

    public void Book(MouseEvent event) throws Exception {

        Rectangle rectangle = (Rectangle) event.getSource();
        if(rectangle.getFill() == Color.RED || rectangle.getFill() == Color.ORANGE) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Seat unavailable. Please pick again!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
        else {

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
                main.change("ui/UserRemoveBooking.fxml");
            }
            else {
                alert.close();
            }
        }
    }

    public void Search(ActionEvent event) throws Exception {

        BookingHolder holder = BookingHolder.getInstance();
        Booking booking = holder.getBooking();
        boolean dateEqualStart = booking.getBookingDate().isEqual(userBookingModel.getConditionStartDate());
        boolean dateEqualEnd = booking.getBookingDate().isEqual(userBookingModel.getConditionEndDate());
        boolean dateBetween = booking.getBookingDate().isAfter(userBookingModel.getConditionStartDate()) && booking.getBookingDate().isBefore(userBookingModel.getConditionEndDate());
        if(time.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Pick your time!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
        else {

            booking.setBookingTime(time.getValue().toString());
            User user = (User) Main.stage.getUserData();
            String username = user.getUsername();
            String time2 = time.getValue().toString();
            String status = "Pending";
            String seatId = userBookingModel.previousBooking(user.getUsername());
            ArrayList<String> temps = new ArrayList<String>();
            temps = userBookingModel.isBooked(booking.getBookingDate(), time2);
            seats.add(seatId);
            ArrayList<String> seatIds = userBookingModel.allSeatId();
            for(String temp : temps) {
                seats.add(temp);
            }
            if(dateEqualStart || dateEqualEnd || dateBetween) {
                ArrayList<String> id = userBookingModel.getSeatIdAffectedByCondition();
                main.displaySeatsWithCondition("ui/UserUpdateBooking.fxml", booking, seats, seatIds, id);
            }

            else {

                ArrayList<String> id = null;
                main.displaySeatsWithCondition("ui/UserUpdateBooking.fxml", booking, seats, seatIds, id);
            }
        }
    }




}
