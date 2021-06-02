package main.model;

import main.Main;
import main.SQLConnection;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResetPasswordModel {

    Connection connection;

    public ResetPasswordModel(){

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

    public Boolean validateUsername(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
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
                User user = new User(empId, fn, ln, role, un, pass, ques, ans);
                Main.stage.setUserData(user);
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception e)
        {
            return false;
        }finally {
           preparedStatement.close();
           resultSet.close();
        }

    }

    public Boolean validateAnswer(String answer) {

        User user = (User) Main.stage.getUserData();
        if(user.getAnswer().equals(answer)) {
            return true;
        }
        else {
            return false;
        }
    }

    public String generateRandomPassword(int len)
    {

        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }

        return sb.toString();
    }

    public Boolean updatePassword(String password) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        User user = (User) Main.stage.getUserData();
        String username = user.getUsername();
        boolean bool = false;
        String query = "UPDATE Employee SET password = ? WHERE username = ?;";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
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
