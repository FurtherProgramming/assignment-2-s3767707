package main.model;

import main.Main;
import main.SQLConnection;

import java.security.SecureRandom;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class SeatManagementModel {

    Connection connection;

    public SeatManagementModel(){

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

    public Boolean updateSeat(String condition, LocalDate startDate, LocalDate endDate) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        if(condition.equals("Restriction")) {
            String query = "UPDATE Seat SET condition = ?, start_date = ?, end_date = ? WHERE status = ?;";
            String query2 = "UPDATE Seat SET condition = ?, start_date = ?, end_date = ? WHERE status = ?;";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, condition);
                preparedStatement.setDate(2, Date.valueOf(startDate));
                preparedStatement.setDate(3, Date.valueOf(endDate));
                preparedStatement.setInt(4, 0);
                preparedStatement.executeUpdate();
                preparedStatement = connection.prepareStatement(query2);
                preparedStatement.setString(1, "Normal");
                preparedStatement.setDate(2, null);
                preparedStatement.setDate(3, null);
                preparedStatement.setInt(4, 1);
                preparedStatement.executeUpdate();
                bool = true;
            }
            catch (Exception e)
            {
                bool = false;
            }finally {
                preparedStatement.close();

            }
        }
        else {
            String query = "UPDATE Seat SET condition = ?, start_date = ?, end_date = ? ";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, condition);
                preparedStatement.setDate(2, Date.valueOf(startDate));
                preparedStatement.setDate(3, Date.valueOf(endDate));
                preparedStatement.executeUpdate();
                bool = true;
            }
            catch (Exception e)
            {
                bool = false;
            }finally {
                preparedStatement.close();
            }
        }
        return bool;
    }

    public ArrayList<String> getSeatId(String condition) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        ArrayList<String> seatIds = new ArrayList<String>();
        if(condition != null) {
            if(condition.equals("Restriction")) {
                String query = "select * from seat where status = ? ";
                try {

                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setInt(1, 0);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        seatIds.add(resultSet.getString("id"));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    preparedStatement.close();
                    resultSet.close();
                }
            }
            else {
                String query = "select * from seat ";
                try {

                    preparedStatement = connection.prepareStatement(query);
                    resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        seatIds.add(resultSet.getString("id"));
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }finally {
                    preparedStatement.close();
                    resultSet.close();
                }
            }
        }
        return seatIds;
    }

    public ArrayList<String> getBookingIdAffectedByCondition(String condition, LocalDate startDate, LocalDate endDate) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        ArrayList<String> seatIds = new ArrayList<String>();
        if(condition.equals("Restriction")) {
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
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }finally {
                preparedStatement.close();
                resultSet.close();
            }
        }
        else {
            String query = "SELECT * from booking where booking_date >= ? and booking_date <= ? ;";
            try {
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setDate(1, Date.valueOf(startDate));
                preparedStatement.setDate(2, Date.valueOf(endDate));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    seatIds.add(resultSet.getString("id"));
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }finally {
                preparedStatement.close();
                resultSet.close();
            }
        }
        return seatIds;
    }

    public Boolean resetCondition() throws SQLException {
        PreparedStatement preparedStatement = null;
        User user = (User) Main.stage.getUserData();
        String username = user.getUsername();
        boolean bool = false;
        String query = "UPDATE seat set condition = null, start_date = null, end_date = null where id in (select id from Seat)";
        try {
            preparedStatement = connection.prepareStatement(query);
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

    public String getCondition() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String condition = null;
        String query = "select * from seat where status = 0 LIMIT 1";
        try {

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                condition = resultSet.getString("condition");
            }
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return condition;
    }

    public ArrayList<String> getAllSeats() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        ArrayList<String> seats = new ArrayList<String>();
        String query = "select * from seat ";
        try {

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                seats.add(resultSet.getString("id"));
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
}
