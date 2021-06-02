package main.model;

import main.Main;
import main.SQLConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;


public class UserBookingModel {

    Connection connection;

    public UserBookingModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public Boolean isDbConnected(){
        try {
            return !connection.isClosed();
        }
        catch(Exception e){
            return false;
        }
    }

    public Boolean validateDate(LocalDate date) throws SQLException {
        boolean available = true;
        LocalDate tmr = LocalDate.now().plusDays(1);
        if(date.isBefore(tmr)) {
            available = false;
        }
        return available;
    }

    public String validateMultipleBookings(LocalDate date, String time) throws SQLException {
        String username = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from booking where booking_date = ? and booking_time = ? and (status = ? or status = ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, "Pending");
            preparedStatement.setString(4, "Accepted");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                username = resultSet.getString("username");
                System.out.println(username);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return username;
    }


    public Boolean isDateMoreThan48hours(LocalDate date) {
        boolean valid = true;


        return valid;
    }


    public Boolean book(String bookingId, String seatId, LocalDate date, String username, String time, String check) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;

        String query = "INSERT INTO Booking (id, username, seat_id, booking_date, status, booking_time, check_in) VALUES(?, ?, ?, ?, ?, ?, ?)";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookingId);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, seatId);
            preparedStatement.setDate(4, Date.valueOf(date));
            preparedStatement.setString(5, "Pending");
            preparedStatement.setString(6, time);
            preparedStatement.setString(7, check);
            preparedStatement.executeUpdate();
            bool = true;
        }
        catch (Exception e)
        {
            bool = false;
        }finally {
            if(preparedStatement != null) {
                preparedStatement.close();

            }
        }
        return bool;
    }

    public String previousBooking(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from booking where username = ?  ORDER BY ROWID DESC LIMIT 1;";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);


            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {

                seatId = resultSet.getString("seat_id");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }finally {
            preparedStatement.close();
            resultSet.close();
        }

        return seatId;
    }

    public ArrayList<String> isBooked(LocalDate date, String time) throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> seats = new ArrayList<String>();
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from booking where booking_date = ? and booking_time = ? and status = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, "Accepted");

            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {

                seatId = resultSet.getString("seat_id");
                seats.add(seatId);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }finally {
            preparedStatement.close();
            resultSet.close();
        }

        return seats;
    }

    public ArrayList<String> allSeatId() throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> seats = new ArrayList<String>();
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from seat ";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                seatId = resultSet.getString("id");
                seats.add(seatId);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }finally {
            preparedStatement.close();
            resultSet.close();
        }

        return seats;
    }

    public ArrayList<String> getSeatIdAffectedByCondition() throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> seats = new ArrayList<String>();
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from seat where start_date is not null ";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                seatId = resultSet.getString("id");
                seats.add(seatId);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }finally {
            preparedStatement.close();
            resultSet.close();
        }

        return seats;
    }

    public LocalDate getConditionStartDate() throws SQLException {
        PreparedStatement preparedStatement = null;
        LocalDate date = null;
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from seat where status = 0 Limit 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                date = resultSet.getDate("start_date").toLocalDate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }finally {
            preparedStatement.close();
            resultSet.close();
        }

        return date;
    }

    public LocalDate getConditionEndDate() throws SQLException {
        PreparedStatement preparedStatement = null;
        LocalDate date = null;
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from seat where status = 0 Limit 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                date = resultSet.getDate("end_date").toLocalDate();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }finally {
            preparedStatement.close();
            resultSet.close();
        }

        return date;
    }

}
