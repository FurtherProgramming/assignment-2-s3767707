package main.model;

import main.Main;
import main.SQLConnection;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class AdminReportModel {

    Connection connection;

    public AdminReportModel(){

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

    public void exportEmployeeTable() throws SQLException, IOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
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
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
            fileWriter.close();
        }

    }

    public void exportBookingTable() throws SQLException, IOException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String csvFilePath = "Booking-report.csv";
        BufferedWriter fileWriter = null;
        String query = "select * from booking";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            fileWriter = new BufferedWriter(new FileWriter(csvFilePath));
            fileWriter.write("booking_id, username, seat_id, booking_date, booking_time, status, check_in");
            while (resultSet.next()) {
                String bookId = resultSet.getString("id");
                String un = resultSet.getString("username");
                String seatId = resultSet.getString("seat_id");
                Date bookDate = resultSet.getDate("booking_date");
                String date = String.valueOf(bookDate);
                String time = resultSet.getString("booking_time");
                String st = resultSet.getString("status");
                String check = resultSet.getString("check_in");
                String line = String.format("\"%s\",%s,%s,%s,%s,%s,%s",
                        bookId, un, seatId, date, time, st, check);
                fileWriter.newLine();
                fileWriter.write(line);
            }
        }
        catch (Exception e) {

            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
            fileWriter.close();
        }
    }

    public ArrayList<Booking> getAllBookings() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        ArrayList<Booking> bookings = new ArrayList<Booking>();

        String query = "select * from booking where status = ? ";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Accepted");
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
