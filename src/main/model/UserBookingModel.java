package main.model;

import main.SQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * Class:		UserBookingModel
 * Description:	A class that handles user booking functions
 * Author:		Anson Go Guang Ping
 */
public class UserBookingModel {

    Connection connection;

    public UserBookingModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    /*
     * find bookings from same day and time and return a list
     * compare with the current user later to prevent multiple bookings per session
     */
    public ArrayList<String> validateMultipleBookings(LocalDate date, String time) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> usernames = new ArrayList<String>();
        String query = "select * from booking where booking_date = ? and booking_time = ? and (status = ? or status = ?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, "Pending");
            preparedStatement.setString(4, "Accepted");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                usernames.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return usernames;
    }

    public boolean UsernameExistInList(String username, LocalDate date, String time) throws SQLException {

        boolean found = false;
        ArrayList<String> usernames = validateMultipleBookings(date, time);
        for (String un : usernames) {
            if (username.equals(un)) {
                found = true;
            }
        }
        return found;
    }

    /*
     * insert bookings into database
     */
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
        } catch (Exception e) {
            bool = false;
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();

            }
        }
        return bool;
    }

    /*
     * Search previous booking of user
     */
    public String previousBooking(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from booking where username = ? ORDER BY ROWID DESC LIMIT 1;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                seatId = resultSet.getString("seat_id");
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return seatId;
    }

    /*
     * get all accepted bookings
     */
    public ArrayList<String> isBooked(LocalDate date, String time) throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> seats = new ArrayList<String>();
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from booking where booking_date = ? and booking_time = ? and status != ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, "Cancelled");

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                seatId = resultSet.getString("seat_id");
                seats.add(seatId);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return seats;
    }

    /*
     * get all seat ids
     */
    public ArrayList<String> allSeatId() throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> seats = new ArrayList<String>();
        ResultSet resultSet = null;
        String seatId = null;
        String query = "select * from seat ";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seatId = resultSet.getString("id");
                seats.add(seatId);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return seats;
    }

    /*
     * get start date of COVID condition
     */
    public LocalDate getConditionStartDate() throws SQLException {
        PreparedStatement preparedStatement = null;
        LocalDate date = null;
        ResultSet resultSet = null;
        String query = "select * from seat where status = 0 Limit 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {

                date = resultSet.getDate("start_date").toLocalDate();

            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return date;
    }

    /*
     * get end date of COVID condition
     */
    public LocalDate getConditionEndDate() throws SQLException {
        PreparedStatement preparedStatement = null;
        LocalDate date = null;
        ResultSet resultSet = null;
        String query = "select * from seat where status = 0 Limit 1";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                date = resultSet.getDate("end_date").toLocalDate();
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return date;
    }

    /*
     * get last checked in booking
     */
    public Booking previousSit(String username) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Booking booking = null;
        String query = "select * from booking where username = ? and check_in = ? ORDER BY ROWID DESC LIMIT 1;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, "Y");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String bookId = resultSet.getString("id");
                String un = resultSet.getString("username");
                String seatId = resultSet.getString("seat_id");
                LocalDate bookDate = resultSet.getDate("booking_date").toLocalDate();
                String bookTime = resultSet.getString("booking_time");
                String status = resultSet.getString("status");
                String checkIn = resultSet.getString("check_in");
                booking = new Booking(bookId, un, seatId, bookDate, status, bookTime, checkIn);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return booking;
    }

    /*
     * get same Employees that have been sitten previously
     */
    public ArrayList<String> getAdjacentUserOfPreviousSit(LocalDate date, String time, String seatId) throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> usernames = new ArrayList<String>();
        ResultSet resultSet = null;
        // get seat id of left employee
        int leftSeat = Integer.parseInt(seatId) - 1;
        if (leftSeat == 0) {
            leftSeat = 16;
        }
        // get seat id of right employee
        int rightSeat = Integer.parseInt(seatId) + 1;
        if (rightSeat == 17) {
            rightSeat = 1;
        }
        String query = "select * from booking where booking_date = ? and booking_time = ? and (seat_id = ? or seat_id = ?)";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, String.valueOf(leftSeat));
            preparedStatement.setString(4, String.valueOf(rightSeat));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                usernames.add(resultSet.getString("username"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }

        return usernames;
    }

    /*
     * get seats of same Employees that have been sitten previously
     */
    public ArrayList<String> getSeatsOfPreviousAdjacentUser(LocalDate date, String time, ArrayList<String> adjacentUsers) throws SQLException {
        PreparedStatement preparedStatement = null;
        ArrayList<String> seats = new ArrayList<String>();
        ResultSet resultSet = null;
        String firstUser = null;
        String secondUser = null;
        // if seated with one employee last sit
        if (adjacentUsers.size() == 1) {
            firstUser = adjacentUsers.get(0);
        }
        // if seated with two employee last sits
        if (adjacentUsers.size() == 2) {
            firstUser = adjacentUsers.get(0);
            secondUser = adjacentUsers.get(1);
        }
        String query = "select * from booking where booking_date = ? and booking_time = ? and (username = ? or username = ?)";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, time);
            preparedStatement.setString(3, firstUser);
            preparedStatement.setString(4, secondUser);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seats.add(resultSet.getString("seat_id"));
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return seats;
    }


    /*
     * get seats beside same Employees that have been sitten previously
     */
    public ArrayList<String> SeatsBesidePrevUser(ArrayList<String> seatIds) {

        ArrayList<String> seats = new ArrayList<String>();
        for (String seat : seatIds) {
            if (seat != null) {
                if (seat.equals("1")) {
                    String right = "16";
                    seats.add(right);
                    int leftNum = Integer.parseInt(seat) + 1;
                    String left = String.valueOf(leftNum);
                    seats.add(left);
                } else if (seat.equals("16")) {
                    String left = "1";
                    seats.add(left);
                    int rightNum = Integer.parseInt(seat) - 1;
                    String right = String.valueOf(rightNum);
                    seats.add(right);
                } else {
                    int leftNum = Integer.parseInt(seat) - 1;
                    String left = String.valueOf(leftNum);
                    seats.add(left);
                    int rightNum = Integer.parseInt(seat) + 1;
                    String right = String.valueOf(rightNum);
                    seats.add(right);
                }
            }
        }
        return seats;
    }
}
