package main.model;

import main.SQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterModel {

    Connection connection;

    public RegisterModel(){

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

    public Boolean usernameExist(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e)
        {
            return false;
        }finally {
            if(preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }

        }

    }

    public Boolean employerIdExist(String employerId) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from employee where emp_id = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, employerId);

            resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        }
        catch (Exception e)
        {
            return false;
        }finally {
            if(preparedStatement != null && resultSet != null) {
                preparedStatement.close();
                resultSet.close();
            }

        }

    }

    public Boolean register(String empId, String fn, String ln, String role, String un, String pass, String ques, String ans) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "INSERT INTO Employee (emp_id, firstname, lastname, role, username, password, secret_question, answer) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, empId);
            preparedStatement.setString(2, fn);
            preparedStatement.setString(3, ln);
            preparedStatement.setString(4, role);
            preparedStatement.setString(5, un);
            preparedStatement.setString(6, pass);
            preparedStatement.setString(7, ques);
            preparedStatement.setString(8, ans);
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


}