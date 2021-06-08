package main.model;

import main.SQLConnection;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * Class:		BookingHolder
 * Description:	A singleton class that is used to pass booking information between scenes
 * Author:		Anson Go Guang Ping
 */
public class ResetPasswordModel {

    Connection connection;

    public ResetPasswordModel() {

        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);

    }

    /*
     * return true if username valid
     */
    public User validateUsername(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = null;
        String query = "select * from employee where username = ?";
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
                String pass = resultSet.getString("password");
                String ques = resultSet.getString("secret_question");
                String ans = resultSet.getString("answer");
                String st = resultSet.getString("status");
                user = new User(empId, fn, ln, role, un, pass, ques, ans, st);
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
     * return true if answer matches secret question
     */
    public Boolean validateAnswer(String answer, String txtAnswer) {

        if (answer.equals(txtAnswer)) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * generates random password
     */
    public String generateRandomPassword(int len) {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    /*
     * return true if password updated successfully
     */
    public Boolean updatePassword(String username, String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        boolean bool = false;
        String query = "UPDATE Employee SET password = ? WHERE username = ?;";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
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

}
