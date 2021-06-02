package main.model;

import main.SQLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Class:		AccountManagement
 * Description:	The class contains function for admin to add/update/delete user's account
 * Author:		Anson Go Guang Ping
 */
public class AccountManagementModel {

    Connection connection;

    public AccountManagementModel(){

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    public ArrayList<User> getAllUser() throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        ArrayList<User> users = new ArrayList<User>();
        String query = "select * from employee";
        try {

            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String empId = resultSet.getString("emp_id");
                String fn = resultSet.getString("firstname");
                String ln = resultSet.getString("lastname");
                String role = resultSet.getString("role");
                String un = resultSet.getString("username");
                String p = resultSet.getString("password");
                String ques = resultSet.getString("secret_question");
                String ans = resultSet.getString("answer");
                User user = new User(empId, fn, ln, role, un, p, ques, ans);
                users.add(user);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return users;
    }

    public Boolean removeAccount(String empId) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "DELETE FROM employee WHERE emp_id = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, empId);
            preparedStatement.executeUpdate();
            bool = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            bool = false;
        }finally {
            preparedStatement.close();
        }
        return bool;
    }

    public User getUserById(String empId) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        User user = null;
        String query = "select * from employee where emp_id = ? ";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, empId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String emp_Id = resultSet.getString("emp_id");
                String fn = resultSet.getString("firstname");
                String ln = resultSet.getString("lastname");
                String role = resultSet.getString("role");
                String un = resultSet.getString("username");
                String p = resultSet.getString("password");
                String ques = resultSet.getString("secret_question");
                String ans = resultSet.getString("answer");
                user = new User(emp_Id, fn, ln, role, un, p, ques, ans);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
           preparedStatement.close();
           resultSet.close();
        }
        return user;
    }

    public User getUserByUsername(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        User user = null;
        String query = "select * from employee where username = ? ";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String emp_Id = resultSet.getString("emp_id");
                String fn = resultSet.getString("firstname");
                String ln = resultSet.getString("lastname");
                String role = resultSet.getString("role");
                String un = resultSet.getString("username");
                String p = resultSet.getString("password");
                String ques = resultSet.getString("secret_question");
                String ans = resultSet.getString("answer");
                user = new User(emp_Id, fn, ln, role, un, p, ques, ans);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return user;
    }

    public ArrayList<User> getUserList(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        ArrayList<User> users = new ArrayList<User>();
        String query = "select * from employee where username = ? ";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String empId = resultSet.getString("emp_id");
                String fn = resultSet.getString("firstname");
                String ln = resultSet.getString("lastname");
                String role = resultSet.getString("role");
                String un = resultSet.getString("username");
                String p = resultSet.getString("password");
                String ques = resultSet.getString("secret_question");
                String ans = resultSet.getString("answer");
                User user = new User(empId, fn, ln, role, un, p, ques, ans);
                users.add(user);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return users;
    }


    public Boolean usernameValid(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        boolean valid = true;
        String query = "select * from employee where username = ? ";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                valid = false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return valid;
    }

    public Boolean empIdValid(String empId) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        boolean valid = true;
        String query = "select * from employee where emp_id = ? ";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, empId);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {

                valid = false;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return valid;
    }


}
