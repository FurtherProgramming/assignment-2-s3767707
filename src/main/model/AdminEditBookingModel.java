package main.model;

import main.SQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * Class:		AdminEditBookingModel
 * Description:	The class contains function for admin to add/update/delete bookings
 * Author:		Anson Go Guang Ping
 */
public class AdminEditBookingModel {

    Connection connection;
    private ArrayList<Booking> bookings = new ArrayList<Booking>();

    public AdminEditBookingModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    /*
     * get bookings by status and booking date order by booking date
     */
    public ArrayList<Booking> getUserBookings(String status, LocalDate date) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        String query = "select * from booking where status = ? and booking_date > ? ORDER BY booking_date";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setDate(2, Date.valueOf(date));
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

        } catch (Exception e) {

            e.printStackTrace();
        } finally {
            preparedStatement.close();
            resultSet.close();
        }

        return bookings;
    }

    /*
     * update booking status by booking id
     */
    public Boolean editBookingStatus(String bookId, String status) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean bool = false;
        String query = "UPDATE booking SET status = ? WHERE id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, bookId);
            preparedStatement.executeUpdate();
            bool = true;
        } catch (Exception e) {
            bool = false;
        } finally {
            preparedStatement.close();
        }
        return bool;
    }

    /*
     * Update booking status by booking date
     */
    public Boolean autoRejectBooking(LocalDate date, String status) throws SQLException {

        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "UPDATE booking SET status = ? WHERE booking_date <= ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setDate(2, Date.valueOf(date));
            preparedStatement.executeUpdate();
            bool = true;
        } catch (Exception e) {
            bool = false;
        } finally {
            preparedStatement.close();
        }
        return bool;
    }

    /*
     * Delete rejected bookings from database after one week of expire date
     */
    public Boolean removeBookingFromDatabaseAfter7Days() throws SQLException {

        PreparedStatement preparedStatement = null;
        boolean bool = false;
        LocalDate date = LocalDate.now().minusDays(7);
        String query = "DELETE from booking where booking_date < ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.executeUpdate();
            bool = true;
        } catch (Exception e) {
            bool = false;
        } finally {
            preparedStatement.close();
        }
        return bool;
    }
}
