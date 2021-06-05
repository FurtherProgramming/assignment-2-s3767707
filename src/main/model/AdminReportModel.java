package main.model;

import main.SQLConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * Class:		AdminReportModel
 * Description:	The class contains function for admin to generate report
 * Author:		Anson Go Guang Ping
 */
public class AdminReportModel {

    Connection connection;

    public AdminReportModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    /*
     * Generate a CSV report from all Employee information
     */
    public void exportEmployeeTable() throws SQLException, IOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String csvFilePath = "Employee-report.csv";
        BufferedWriter fileWriter = null;
        String query = "select * from employee";
        try {

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            fileWriter.write("employer_id,first_name,last_name,role,username,password,secret_question,answer");
            while (resultSet.next()) {
                String empId = resultSet.getString("emp_id");
                String fn = resultSet.getString("firstname");
                String ln = resultSet.getString("lastname");
                String role = resultSet.getString("role");
                String un = resultSet.getString("username");
                String pw = resultSet.getString("password");
                String sq = resultSet.getString("secret_question");
                String ans = resultSet.getString("answer");

                String line = String.format("\"%s\",%s,%s,%s,%s,%s,%s,%s",
                        empId, fn, ln, role, un, pw, sq, ans);

                fileWriter.newLine();
                fileWriter.write(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            resultSet.close();
            fileWriter.close();
        }

    }

    /*
     * Generatea CSV report from accepted booking with date
     */
    public void exportBookingTableWithDate(LocalDate date) throws SQLException, IOException {

        String csvFilePath = "Booking-report.csv";
        BufferedWriter fileWriter = null;
        fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
        fileWriter.write("booking_id, username, seat_id, booking_date, booking_time, status, check_in");
        ArrayList<Booking> bookings = getAllBookingsWithDate(date);
        for (Booking booking : bookings) {

            String line = String.format("\"%s\",%s,%s,%s,%s,%s,%s",
                    booking.getBookingId(), booking.getUsername(), booking.getSeatId(), booking.getBookingDate().toString(), booking.getBookingTime(), booking.getStatus(), booking.getCheckIn());
            fileWriter.newLine();
            fileWriter.write(line);
        }
        fileWriter.close();
    }

    /*
     * Generatea CSV report from all accepted booking
     */
    public void exportBookingTable() throws SQLException, IOException {

        String csvFilePath = "Booking-report.csv";
        BufferedWriter fileWriter = null;
        fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
        fileWriter.write("booking_id, username, seat_id, booking_date, booking_time, status, check_in");
        ArrayList<Booking> bookings = getAllBookings();
        for (Booking booking : bookings) {

            String line = String.format("\"%s\",%s,%s,%s,%s,%s,%s",
                    booking.getBookingId(), booking.getUsername(), booking.getSeatId(), booking.getBookingDate().toString(), booking.getBookingTime(), booking.getStatus(), booking.getCheckIn());
            fileWriter.newLine();
            fileWriter.write(line);
        }
        fileWriter.close();
    }

    /*
     * get all accepted bookings order by booking date
     */
    public ArrayList<Booking> getAllBookings() throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        String query = "select * from booking ORDER BY booking_date ";
        try {
            preparedStatement = connection.prepareStatement(query);
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
     * get all accepted bookings with booking date and order by status
     */
    public ArrayList<Booking> getAllBookingsWithDate(LocalDate date) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Booking> bookings = new ArrayList<Booking>();
        String query = "select * from booking where booking_date = ? ORDER BY status";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setDate(1, Date.valueOf(date));
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
}
