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
import main.model.Booking;
import main.model.BookingHolder;
import main.model.User;
import main.model.UserBookingModel;
import java.net.URL;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserBookingController implements Initializable {
    private UserBookingModel userBookingModel = new UserBookingModel();
    private Main main = new Main();
    private ArrayList<String> seats = new ArrayList<String>();
    private ArrayList<String> allSeatId = new ArrayList<String>();
    private User user = (User) Main.stage.getUserData();
    @FXML
    private DatePicker datePicker;
    @FXML
    private ChoiceBox time;

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


        boolean dateEqualStart = datePicker.getValue().isEqual(userBookingModel.getConditionStartDate());
        boolean dateEqualEnd = datePicker.getValue().isEqual(userBookingModel.getConditionEndDate());
        boolean dateBetween = datePicker.getValue().isAfter(userBookingModel.getConditionStartDate()) && datePicker.getValue().isBefore(userBookingModel.getConditionEndDate());

        if(datePicker.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Pick your date!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
                main.change("ui/UserBooking.fxml");
            }
        }
        else if(time.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR, "Pick your time!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
        else {
            if(userBookingModel.validateDate(datePicker.getValue())) {
                if(!user.getUsername().equals(userBookingModel.validateMultipleBookings(datePicker.getValue(), time.getValue().toString()))) {
                    String username = user.getUsername();
                    LocalDate date = datePicker.getValue();
                    String time2 = time.getValue().toString();
                    String status = "Pending";
                    Booking booking = new Booking(null,username,null,date,status,time2,"N");
                    String seatId = userBookingModel.previousBooking(user.getUsername());
                    ArrayList<String> temps = new ArrayList<String>();
                    temps = userBookingModel.isBooked(date, time2);
                    seats.add(seatId);
                    ArrayList<String> seatIds = userBookingModel.allSeatId();
                    for(String temp : temps) {
                        seats.add(temp);
                    }
                    if(dateEqualStart || dateEqualEnd || dateBetween) {
                        ArrayList<String> id = userBookingModel.getSeatIdAffectedByCondition();
                        main.displaySeatsWithCondition("ui/UserBooking.fxml", booking, seats, seatIds, id);
                    }

                    else {

                        ArrayList<String> id = null;
                        main.displaySeatsWithCondition("ui/UserBooking.fxml", booking, seats, seatIds, id);
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "You can only book one seat per session!", ButtonType.CLOSE);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.CLOSE) {
                        alert.close();
                        main.change("ui/UserBooking.fxml");
                    }
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Date is invalid!", ButtonType.CLOSE);
                alert.showAndWait();
                if (alert.getResult() == ButtonType.CLOSE) {
                    alert.close();
                    main.change("ui/UserBooking.fxml");
                }
            }
        }
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
        else if(rectangle.getFill() == Color.WHITE) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Click on the search button!", ButtonType.CLOSE);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.CLOSE) {
                alert.close();
            }
        }
        else {
            //String str = String.format("%s\n%s%s\n%s%s\n%s%s", "Confirm Booking?", "Seat id: ", booking.getSeatId(), "Booking date: ", booking.getBookingDate(), "Booking Time: ", booking.getTime());
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
            }
            else {
                alert.close();
            }
        }
    }

    public String generateId(int len)
    {

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
