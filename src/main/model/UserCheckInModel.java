package main.model;

import main.Main;
import main.SQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserCheckInModel {

    Connection connection;

    public UserCheckInModel(){

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

    public Boolean isTime(String bookingId, int time) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isDate = false;
        String query = "select * from booking where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookingId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String bookTime = resultSet.getString("booking_time");
                if(bookTime.equals("0800")) {
                    if(time < 14 && time >= 8) {
                        isDate = true;
                    }
                }
                if(bookTime.equals("1400")){
                    if(time >= 14) {
                        isDate = true;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            isDate = false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return isDate;
    }

    public Boolean isCheckedIn(String bookingId) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean isCheck = false;
        String query = "select * from booking where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookingId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String check = resultSet.getString("check_in");
                if(check.equals("Y")) {
                    isCheck = true;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return isCheck;
    }



    public Boolean checkIn(String bookingId) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "UPDATE booking SET check_in = ? WHERE id = ?;";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Y");
            preparedStatement.setString(2, bookingId);
            preparedStatement.executeUpdate();
            bool = true;
        }
        catch (Exception e)
        {
            bool = false;
        }finally {
            preparedStatement.close();

        }
        return bool;
    }

    public ArrayList<Booking> getUserBooking(String username, String status, LocalDate date) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        String query = "select * from booking where username = ? and status = ? and booking_date = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, status);
            preparedStatement.setDate(3, Date.valueOf(date));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String bookId = resultSet.getString("id");
                String un = resultSet.getString("username");
                String seatId = resultSet.getString("seat_id");
                Date bookDate = resultSet.getDate("booking_date");
                String st = resultSet.getString("status");
                String time = resultSet.getString("booking_time");
                String check = resultSet.getString("check_in");
                Booking booking = new Booking(bookId, un, seatId, bookDate.toLocalDate(), st, time, check);
                bookings.add(booking);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }

        return bookings;
    }


}