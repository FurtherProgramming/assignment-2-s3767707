package main.test;

import main.SQLConnection;
import main.model.AccountManagementModel;
import main.model.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountManagementTest {

    private static AccountManagementModel accountManagementModel = new AccountManagementModel();
    static Connection connection;

    @BeforeAll
    static void setUpBeforeClass(){

        accountManagementModel = new AccountManagementModel();
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @Test
    @Order(1)
    void testGetAllUser_returnUserList_IfDatabaseNotEmpty() throws SQLException {

        ArrayList<User> users = accountManagementModel.getAllUser();
        for(User user : users) {
            assertNotNull(user.getEmployerId());
        }
    }

    @Test
    @Order(2)
    void testRemoveAccount_returnTrue_IfDatabaseUpdated() throws SQLException {

        accountManagementModel.removeAccount("3");
        String query = "Select * from employee where emp_id = ? ";
        String query2 = "INSERT INTO Employee (emp_id, firstname, lastname, role, username, password, secret_question, answer) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "3");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fail();
            }
            else {
                assertTrue(true);
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "3");
            preparedStatement.setString(2, "b");
            preparedStatement.setString(3, "b");
            preparedStatement.setString(4, "user");
            preparedStatement.setString(5, "b");
            preparedStatement.setString(6, "b");
            preparedStatement.setString(7, "What is your favourite colour?");
            preparedStatement.setString(8, "b");
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Test
    @Order(3)
    void testRemoveBookings_returnTrue_IfDatabaseUpdated() throws SQLException {

        accountManagementModel.removeBookings("b");
        String query = "Select * from booking where username = ? ";
        String query2 = "INSERT INTO booking (id, username, seat_id, booking_date, booking_time, status, check_in) VALUES(?, ?, ?, ?, ?, ?, ?)";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "b");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fail();
            }
            else {
                assertTrue(true);
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "aHbZa16");
            preparedStatement.setString(2, "b");
            preparedStatement.setString(3, "a16");
            LocalDate date = LocalDate.of(2022, 6, 1);
            preparedStatement.setDate(4, Date.valueOf(date));
            preparedStatement.setString(5, "0800");
            preparedStatement.setString(6, "Pending");
            preparedStatement.setString(7, "N");
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Test
    @Order(4)
    void testGetUserById_returnUser_IfSearchByEmployerId() throws SQLException {

        assertEquals("test", accountManagementModel.getUserById("1").getUsername());
    }

    @Test
    @Order(5)
    void testGetUserByUsername_returnUser_IfSearchByUsername() throws SQLException {

        assertEquals("test", accountManagementModel.getUserByUsername("test").getUsername());
    }

    @Test
    @Order(6)
    void testUpdateAccount_lastnameEquals_IfComparedWithUpdatedResult() throws SQLException {

        accountManagementModel.updateAccount("3", "b", "c", "user", "b", "What is your favourite colour?", "b");
        String query = "Select * from employee where emp_id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "3");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("c", resultSet.getString("lastname"));
            }
            else {
                fail();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
        accountManagementModel.updateAccount("3", "b", "b", "user", "b", "What is your favourite colour?", "b");
    }

    @Test
    @Order(7)
    void testUpdateAccount_answerEquals_IfComparedWithUpdatedResult() throws SQLException {

        accountManagementModel.updateAccount("3", "b", "c", "user", "b", "What is your favourite colour?", "c");
        String query = "Select * from employee where emp_id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "3");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("c", resultSet.getString("answer"));
            }
            else {
                fail();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
        }
        accountManagementModel.updateAccount("3", "b", "b", "user", "b", "What is your favourite colour?", "b");
    }

}
