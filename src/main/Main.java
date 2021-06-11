package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.model.*;

import java.util.ArrayList;

/*
 * Class:		Main
 * Description:	A class that handles passing of parameter between scenes, preset scene visuals and run main application
 * Author:		Anson Go Guang Ping
 */
public class Main extends Application {

    public static Stage stage = new Stage();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("ui/Home.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void change(String path) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.hide();
        stage.show();
    }

    /*
     * pass account instance between scenes and set scene visuals
     */
    public void passAccount(String path, User user) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        AccountHolder holder = AccountHolder.getInstance();
        holder.setAccount(user);
        // preview of account details when rendering AdminUpdateAccount page
        if (user != null) {
            TextField t = (TextField) scene.lookup("#txtEmployerId");
            if (t != null) {
                t.setText(user.getEmployeeId());
            }
            t = (TextField) scene.lookup("#txtFirstname");
            if (t != null) {
                t.setText(user.getFirstName());
            }
            t = (TextField) scene.lookup("#txtLastname");
            if (t != null) {
                t.setText(user.getLastName());
            }
            ChoiceBox t2 = (ChoiceBox) scene.lookup("#txtRole");
            if (t2 != null) {
                t2.setValue(user.getRole());
            }
            t = (TextField) scene.lookup("#txtUsername");
            if (t != null) {
                t.setText(user.getUsername());
            }
            t = (TextField) scene.lookup("#txtPassword");
            if (t != null) {
                t.setText(user.getPassword());
            }
            t2 = (ChoiceBox) scene.lookup("#txtSecretQuestion");
            if (t2 != null) {
                t2.setValue(user.getQuestion());
            }
            t = (TextField) scene.lookup("#txtAnswer");
            if (t != null) {
                t.setText(user.getAnswer());
            }
        }
    }

    /*
     * set seat colour after setting COVID condition
     */
    public void setSeatColorInSeatManagement(String path, ArrayList<Seat> allSeats) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        ChoiceBox condition = (ChoiceBox) scene.lookup("#condition");
        DatePicker startDate = (DatePicker) scene.lookup("#startDate");
        DatePicker endDate = (DatePicker) scene.lookup("#endDate");

        for (Seat seat : allSeats) {
            if (seat.getCondition().equals("Normal")) {
                condition.setValue(seat.getCondition());
                String seatId = "#" + seat.getSeatId();
                Rectangle rectangle = (Rectangle) scene.lookup(seatId);
                rectangle.setFill(Color.LIGHTGREEN);
            } else {
                if (seat.getSeatId().equals("2")) {
                    condition.setValue(seat.getCondition());
                    startDate.setValue(seat.getStartDate());
                    endDate.setValue(seat.getEndDate());
                }
                String seatId = "#" + seat.getSeatId();
                Rectangle rectangle = (Rectangle) scene.lookup(seatId);
                rectangle.setFill(Color.ORANGE);
            }
        }
    }

    /*
     * set seats colour of booking scenes after applying COVID conditions
     */
    public void displaySeatsWithCondition(String path, Booking booking, ArrayList<String> seats, ArrayList<String> seatIds, ArrayList<Seat> seatCd) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        BookingHolder holder = BookingHolder.getInstance();
        holder.setBooking(booking);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        ChoiceBox c = (ChoiceBox) scene.lookup("#time");
        if (c != null) {
            c.setValue(String.valueOf(booking.getBookingTime()));
        }
        DatePicker d = (DatePicker) scene.lookup("#datePicker");
        if (d != null) {
            d.setValue(booking.getBookingDate());
        }
        Label bd = (Label) scene.lookup("#book_date");
        if (bd != null) {
            bd.setText(String.valueOf(booking.getBookingDate()));
        }
        Label bt = (Label) scene.lookup("#book_time");
        if (bt != null) {
            bt.setText(booking.getBookingTime());
        }
        for (String id : seatIds) {
            if (id != null) {
                String seatId = "#" + id;
                Rectangle rectangle = (Rectangle) scene.lookup(seatId);
                rectangle.setFill(Color.LIGHTGREEN);
            }
        }
        for (String seat : seats) {
            if (seat != null) {
                String seatId = "#" + seat;
                Rectangle rectangle = (Rectangle) scene.lookup(seatId);
                rectangle.setFill(Color.RED);
            }
        }
        if (seatCd != null) {
            for (Seat seat : seatCd) {
                if (!booking.getBookingDate().isBefore(seat.getStartDate()) && !booking.getBookingDate().isAfter(seat.getEndDate())) {
                    if (seat.getCondition().equals("Restriction") || seat.getCondition().equals("Lockdown")) {
                        String seatId = "#" + seat.getSeatId();
                        Rectangle rectangle = (Rectangle) scene.lookup(seatId);
                        rectangle.setFill(Color.ORANGE);
                    }
                }
            }
        }
    }
}
