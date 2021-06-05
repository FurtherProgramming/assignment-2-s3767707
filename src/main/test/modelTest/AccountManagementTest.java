package main.test.modelTest;

import main.SQLConnection;
import main.model.AccountManagementModel;
import main.model.RegisterModel;
import main.model.User;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AccountManagementTest {

    private static AccountManagementModel accountManagementModel = new AccountManagementModel();
    private static RegisterModel registerModel = new RegisterModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass() {

        accountManagementModel = new AccountManagementModel();
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @AfterAll
    static void setUpAfterClass() throws SQLException {

        connection.close();

    }

    @Test
    @Order(1)
    void testGetAllUser_returnUserList_IfDatabaseNotEmpty() throws SQLException {

        ArrayList<User> users = accountManagementModel.getAllUser();
        for (User user : users) {
            assertNotNull(user.getEmployerId());
        }
    }

    @Test
    @Order(2)
    void testRemoveAccount_returnTrue_IfDatabaseUpdated() throws SQLException {

        accountManagementModel.removeAccount("2");
        String query = "Select * from employee where emp_id = ? ";
        String query2 = "INSERT INTO Employee (emp_id, firstname, lastname, role, username, password, secret_question, answer, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "2");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fail();
            } else {
                assertTrue(true);
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "2");
            preparedStatement.setString(2, "a");
            preparedStatement.setString(3, "a");
            preparedStatement.setString(4, "user");
            preparedStatement.setString(5, "a");
            preparedStatement.setString(6, "a");
            preparedStatement.setString(7, "What is your favourite colour?");
            preparedStatement.setString(8, "a");
            preparedStatement.setString(9, "activated");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Test
    @Order(3)
    void testRemoveBookings_returnTrue_IfDatabaseUpdated() throws SQLException {

        accountManagementModel.removeBookings("q");
        String query = "Select * from booking where username = ? ";
        String query2 = "INSERT INTO booking (id, username, seat_id, booking_date, booking_time, status, check_in) VALUES(?, ?, ?, ?, ?, ?, ?)";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "q");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                fail();
            } else {
                assertTrue(true);
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "4Iri2");
            preparedStatement.setString(2, "q");
            preparedStatement.setString(3, "2");
            LocalDate date = LocalDate.of(2022, 7, 1);
            preparedStatement.setDate(4, Date.valueOf(date));
            preparedStatement.setString(5, "0800");
            preparedStatement.setString(6, "Accepted");
            preparedStatement.setString(7, "Y");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
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

        registerModel.register("4", "c", "c", "user", "c", "c", "What is your favourite colour?", "c");
        User u = new User("4", "c", "c", "user", "c", "c", "What is your favourite colour?", "c", "activated");
        accountManagementModel.updateAccount("4", "c", "d", "user", "c", "c", "What is your favourite colour?", "c", u);
        String query = "Select * from employee where emp_id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "4");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("d", resultSet.getString("lastname"));
            } else {
                fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        accountManagementModel.removeAccount("4");
    }

    @Test
    @Order(7)
    void testUpdateAccount_answerEquals_IfComparedWithUpdatedResult() throws SQLException {

        registerModel.register("4", "c", "c", "user", "c", "c", "What is your favourite colour?", "c");
        User u = new User("4", "c", "c", "user", "c", "c", "What is your favourite colour?", "c", "activated");
        accountManagementModel.updateAccount("4", "c", "c", "user", "c", "c", "What is your favourite colour?", "d", u);
        String query = "Select * from employee where emp_id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "4");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("d", resultSet.getString("answer"));
            } else {
                fail();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
        accountManagementModel.removeAccount("4");
    }

    @Test
    @Order(8)
    void testActivateOrDeactivate_statusEquals_IfDatabaseSuccessfullyUpdated() throws SQLException {
        accountManagementModel.activateOrDeactivate("2", "deactivated");
        String query = "Select * from employee where emp_id = ? and status = ? ";
        String query2 = "UPDATE employee set status = ? where emp_id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "2");
            preparedStatement.setString(2, "deactivated");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("deactivated", resultSet.getString("status"));
            } else {
                fail();
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "activated");
            preparedStatement.setString(2, "2");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Test
    @Order(9)
    void testUsernameExist_returnFalse_IfUpdatedUsernameIsPreviousUsername() throws SQLException {
        assertFalse(accountManagementModel.usernameExist("a", "a"));
    }

    @Test
    @Order(10)
    void testUsernameExist_returnTrue_IfUpdatedUsernameExistInDatabse() throws SQLException {
        assertTrue(accountManagementModel.usernameExist("q", "a"));
    }

    @Test
    @Order(11)
    void testEmployerIdExist_returnFalse_IfUpdatedEmployerIdIsPreviousEmployerId() throws SQLException {
        assertFalse(accountManagementModel.empIdExist("1", "1"));
    }

    @Test
    @Order(12)
    void testEmployerIdExist_returnTrue_IfUpdatedEmployerIdExistInDatabse() throws SQLException {
        assertTrue(accountManagementModel.empIdExist("2", "1"));
    }

}
