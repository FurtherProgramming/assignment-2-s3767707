package main.model;

import main.Main;
import main.SQLConnection;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

/*
 * Class:		UserEditBookingModel
 * Description:	A class that handles user edit bookings function
 * Author:		Anson Go Guang Ping
 */
public class UserEditBookingModel {

    Connection connection;
    public UserEditBookingModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    /*
     * return booking list of username and particular condition (Accepted/Pending/Rejected) order by booking date
     * Each seat has two session opened for bookings(morning/afternoon)
     * User can have bookings on two session in the same day so list is returned
     */
    public ArrayList<Booking> getUserBookings(String username, String status) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        ArrayList<Booking> bookings = new ArrayList<Booking>();

        String query = "select * from booking where username = ? and status = ? order by booking_date";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, status);

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

    /*
     * check if booking available for removing/updating
     */
    public Boolean checkHourBeforeEdit(LocalDate date, String time, LocalDate now, LocalDateTime currentTime) {

        boolean valid = false;
        int hour = Integer.parseInt(time)/100;
        long daysBetween = ChronoUnit.DAYS.between(now, date);
        if(daysBetween < 2) {
            valid = false;
        }
        else if(daysBetween >= 3) {
            valid = true;
        }
        else {
            int currentHour = currentTime.getHour();
            // if difference of days between current day and booking date is 2 days, calculate the difference in hours
            int diff = (24 - currentHour) + 24 + hour; // today's hours left before midnight + 24hour + midnight to booking time
            System.out.println(diff);
            if(diff <= 48) {
                valid = false;
            }
            else {
                valid = true;
            }
        }
        return valid;
    }

    /*
     * remove booking from database with booking id
     */
    public Boolean deleteBooking(String bookingId) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "DELETE FROM booking WHERE id = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookingId);
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

    /*
     * update booking's seat id and booking time
     */
    public Boolean updateBooking(String bookingId, String seatId, String time) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;

        String query = "UPDATE booking SET seat_id = ?, booking_time = ? WHERE id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, seatId);
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, bookingId);
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
}
