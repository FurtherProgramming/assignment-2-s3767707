package main.model;

import main.SQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

/*
 * Class:		SeatManagementModel
 * Description:	A class that handles COVID seat management
 * Author:		Anson Go Guang Ping
 */
public class SeatManagementModel {

    Connection connection;

    public SeatManagementModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    /*
     * update seat condition and duration based on COVID condition picked
     */
    public Boolean updateSeat(String condition, LocalDate startDate, LocalDate endDate) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        //if condition equals to "Restriction", locks seats in between
        if (condition.equals("Restriction")) {
            String query = "UPDATE Seat SET condition = ?, start_date = ?, end_date = ? WHERE status = ?;";
            String query2 = "UPDATE Seat SET condition = ?, start_date = 0, end_date = 0 WHERE status = ?;";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, condition);
                preparedStatement.setDate(2, Date.valueOf(startDate));
                preparedStatement.setDate(3, Date.valueOf(endDate));
                preparedStatement.setInt(4, 0);
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setString(1, "Normal");
                preparedStatement.setInt(2, 1);
                preparedStatement.executeUpdate();
                bool = true;
            } catch (Exception e) {
                e.printStackTrace();
                bool = false;
            } finally {
                preparedStatement.close();

            }
        }
        //if conditions = "Lockdown", locks all seats
        else {
            String query = "UPDATE Seat SET condition = ?, start_date = ?, end_date = ? ";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, condition);
                preparedStatement.setDate(2, Date.valueOf(startDate));
                preparedStatement.setDate(3, Date.valueOf(endDate));
                preparedStatement.executeUpdate();
                bool = true;
            } catch (Exception e) {
                bool = false;
            } finally {
                preparedStatement.close();
            }
        }
        return bool;
    }

    /*
     * get seat id based on COVID condition
     */
    public ArrayList<String> getSeatId(String condition) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> seatIds = new ArrayList<String>();
        if (condition != null) {
            if (condition.equals("Restriction")) {
                String query = "select * from seat where status = ? ";
                try {
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, 0);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        seatIds.add(resultSet.getString("id"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    preparedStatement.close();
                    resultSet.close();
                }
            }
            if (condition.equals("Lockdown")) {
                String query = "select * from seat ";
                try {

                    preparedStatement = connection.prepareStatement(query);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        seatIds.add(resultSet.getString("id"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    preparedStatement.close();
                    resultSet.close();
                }
            } else {
                seatIds = null;
            }
        }
        return seatIds;
    }

    /*
     * get booking id affected by COVID condition
     */
    public ArrayList<String> getBookingIdAffectedByCondition(String condition, LocalDate startDate, LocalDate endDate) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<String> seatIds = new ArrayList<String>();
        if (condition.equals("Restriction")) {
            String query = "SELECT * from booking where booking_date >= ? and booking_date <= ? and seat_id in (select id from Seat where status = ?);";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, Date.valueOf(startDate));
                preparedStatement.setDate(2, Date.valueOf(endDate));
                preparedStatement.setInt(3, 0);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    seatIds.add(resultSet.getString("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                preparedStatement.close();
                resultSet.close();
            }
        } else {
            String query = "SELECT * from booking where booking_date >= ? and booking_date <= ? ;";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, Date.valueOf(startDate));
                preparedStatement.setDate(2, Date.valueOf(endDate));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    seatIds.add(resultSet.getString("id"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return seatIds;
    }

    /*
     * reset the conditions of all seats, i.e. unlock all seats
     */
    public Boolean resetCondition() throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "UPDATE seat set condition = ?, start_date = 0, end_date = 0 ";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Normal");
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
     * get all Seats
     */
    public ArrayList<Seat> getAllSeats() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        ArrayList<Seat> seats = new ArrayList<Seat>();
        String query = "select * from seat ";
        try {

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String seatId = resultSet.getString("id");
                String status = resultSet.getString("status");
                String condition = resultSet.getString("condition");
                LocalDate startDate = resultSet.getDate("start_date").toLocalDate();
                LocalDate endDate = resultSet.getDate("end_date").toLocalDate();
                Seat seat = new Seat(seatId, status, condition, startDate, endDate);
                seats.add(seat);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            preparedStatement.close();
            resultSet.close();
        }
        return seats;
    }


}
