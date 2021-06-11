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

    public AccountManagementModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    /*
     * Get all user from database except current user
     */
    public ArrayList<User> getAllUser(String currentUsername) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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
                String st = resultSet.getString("status");
                if(currentUsername != null) {
                    if(!un.equals(currentUsername)) {
                        User user = new User(empId, fn, ln, role, un, p, ques, ans, st);
                        users.add(user);
                    }
                }
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
        return users;
    }

    /*
     * Remove user from database by employee id
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
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return bool;
    }

    /*
     * Remove bookings from database by username
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
        } catch (Exception e) {
            e.printStackTrace();
            bool = false;
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return bool;
    }

    /*
     * Get user from database with employee id
     */
    public User getUserById(String empId) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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
                String st = resultSet.getString("status");
                user = new User(emp_Id, fn, ln, role, un, p, ques, ans, st);
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
        return user;
    }

    /*
     * Get user from database by username
     */
    public User getUserByUsername(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
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
                String st = resultSet.getString("status");
                user = new User(emp_Id, fn, ln, role, un, p, ques, ans, st);
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
        return user;
    }

    /*
     * Update account details by employee id
     */
    public Boolean updateAccount(String empId, String fn, String ln, String role, String un, String pass, String ques, String ans, User user) throws SQLException {

        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "UPDATE employee SET emp_id = ?, firstname = ?, lastname = ?, role = ?, username = ?, password = ?, secret_question = ?, answer = ? WHERE emp_id = ?;";
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
            preparedStatement.setString(9, user.getEmployeeId());
            preparedStatement.executeUpdate();
            bool = true;
        } catch (Exception e) {
            bool = false;
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return bool;
    }

    /*
     * update account details by employee id
     */
    public Boolean activateOrDeactivate(String empId, String status) throws SQLException {

        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "UPDATE employee SET status = ? WHERE emp_id = ?;";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, empId);

            preparedStatement.executeUpdate();
            bool = true;
        } catch (Exception e) {
            bool = false;
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
        return bool;
    }

    /*
     * Return true if username exist
     * Allows admin to reuse previous account username
     */
    public Boolean usernameExist(String username, String prevUsername) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean valid = false;
        String query = "select * from employee where username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("username").equals(prevUsername)) { // if username entered equals to previous username
                    valid = false;
                } else {
                    valid = true;
                }
            }
        } catch (Exception e) {
            valid = false;
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return valid;
    }

    /*
     * Return true if username exist
     * Allows admin to reuse previous account username
     */
    public Boolean empIdExist(String empId, String prevEmpId) throws SQLException {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        boolean valid = false;
        String query = "select * from employee where emp_id = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, empId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getString("emp_id").equals(prevEmpId)) { // if employer id entered equals to previous employer id
                    valid = false;
                } else {
                    valid = true;
                }
            }
        } catch (Exception e) {
            valid = false;
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return valid;
    }
}
