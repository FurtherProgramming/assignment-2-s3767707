package main.model;

import main.Main;
import main.SQLConnection;
import org.sqlite.SQLiteConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    Connection connection;

    public LoginModel(){

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

    public Boolean isLogin(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where username = ? and password= ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

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
                User u = new User(empId, fn, ln, role, un, p, ques, ans);
                Main.stage.setUserData(u);
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

    public Boolean isAdmin(String user, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        String query = "select * from employee where username = ? and password= ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.getString("role").equals("admin")) {
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

    public Boolean usernameValid(String username) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet=null;
        boolean valid = false;
        String query = "select * from employee where username = ? ";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                String empId = resultSet.getString("emp_id");
                String fn = resultSet.getString("firstname");
                String ln = resultSet.getString("lastname");
                String role = resultSet.getString("role");
                String un = resultSet.getString("username");
                String p = resultSet.getString("password");
                String ques = resultSet.getString("secret_question");
                String ans = resultSet.getString("answer");
                User u = new User(empId, fn, ln, role, un, p, ques, ans);
                Main.stage.setUserData(u);
                valid = true;
            }
        }
        catch (Exception e)
        {
            valid = false;
        }finally {
            preparedStatement.close();
            resultSet.close();
        }
        return valid;
    }

    public Boolean passwordValid(String password) {

        User user = (User) Main.stage.getUserData();
        if(user.getPassword().equals(password)) {
            return true;
        }
        else {
            return false;
        }
    }
}
