package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.model.*;
import java.util.ArrayList;


public class Main extends Application {

    public static Stage stage = new Stage();
    private UserBookingModel userBookingModel = new UserBookingModel();

    @Override
    public void start(Stage primaryStage) throws Exception{

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

    public void passAccount(String path, User user) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        //stage.hide();
        stage.show();
        AccountHolder holder = AccountHolder.getInstance();
        holder.setAccount(user);
        if(user != null) {
            TextField t = (TextField) scene.lookup("#txtEmployerId");
            if(t != null) {
                t.setText(user.getEmployerId());
            }
            t = (TextField) scene.lookup("#txtFirstname");
            if(t != null) {
                t.setText(user.getFirstName());
            }
            t = (TextField) scene.lookup("#txtLastname");
            if(t != null) {
                t.setText(user.getLastName());
            }
            ChoiceBox t2 = (ChoiceBox) scene.lookup("#txtRole");
            if(t2 != null) {
                t2.setValue(user.getRole());
            }
            t = (TextField) scene.lookup("#txtUsername");
            if(t != null) {
                t.setText(user.getUsername());
            }
            t = (TextField) scene.lookup("#txtPassword");
            if(t != null) {
                t.setText(user.getPassword());
            }
            t2 = (ChoiceBox) scene.lookup("#txtSecretQuestion");
            if(t2 != null) {
                t2.setValue(user.getQuestion());
            }
            t = (TextField) scene.lookup("#txtAnswer");
            if(t != null) {
                t.setText(user.getAnswer());
            }
        }
    }

    public void setSeatColor(String path, ArrayList<String> allSeats, ArrayList<String> seats) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        for(String seat : allSeats) {
            if(seat != null) {
                String seatId = "#" + seat;
                Rectangle rectangle = (Rectangle) scene.lookup(seatId);
                rectangle.setFill(Color.LIGHTGREEN);
            }
        }
        for(String seat : seats) {
            if(seat != null) {
                String seatId = "#" + seat;
                Rectangle rectangle = (Rectangle) scene.lookup(seatId);
                rectangle.setFill(Color.ORANGE);
            }
        }
    }


    public void displaySeatsWithCondition(String path, Booking booking, ArrayList<String> seats, ArrayList<String> seatIds, ArrayList<String> seatCd) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource(path));
        BookingHolder holder = BookingHolder.getInstance();
        holder.setBooking(booking);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Label l = (Label) scene.lookup("#date");
        if(l != null) {
            l.setText(String.valueOf(booking.getBookingDate()));
        }
        ChoiceBox c = (ChoiceBox) scene.lookup("#time");
        if(c != null) {
            c.setValue(String.valueOf(booking.getBookingTime()));
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
            for (String seat : seatCd) {
                if (seat != null) {
                    String seatId = "#" + seat;
                    Rectangle rectangle = (Rectangle) scene.lookup(seatId);
                    rectangle.setFill(Color.ORANGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
