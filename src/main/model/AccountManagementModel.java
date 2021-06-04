package main.model;

import main.Main;
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

    /*
     * get all user from database
     */
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

    /*
     * remove user from database by employer id
     */
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

    /*
     * remove bookings from database by username
     */
    public Boolean removeBookings(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "DELETE FROM booking WHERE username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
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

    /*
     * get user from database with employer id
     */
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

    /*
     * get user from database by username
     */
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

    /*
     * update account details by employer id
     */
    public Boolean updateAccount(String empId, String fn, String ln, String role, String pass, String ques, String ans) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        boolean bool = false;
        String query = "UPDATE employee SET firstname = ?, lastname = ?, role = ?, password = ?, secret_question = ?, answer = ? WHERE emp_id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, fn);
            preparedStatement.setString(2, ln);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, pass);
            preparedStatement.setString(5, ques);
            preparedStatement.setString(6, ans);
            preparedStatement.setString(7, empId);
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
