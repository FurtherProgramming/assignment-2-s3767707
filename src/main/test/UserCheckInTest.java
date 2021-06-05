package main.test;

import main.SQLConnection;
import main.model.Booking;
import main.model.UserCheckInModel;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserCheckInTest {

    private static UserCheckInModel userCheckInModel = new UserCheckInModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass(){

        userCheckInModel = new UserCheckInModel();
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @AfterAll
    static  void setUpAfterClass() throws SQLException {

        connection.close();

    }

    @Test
    @Order(47)
    void testisTime_returnTrue_IfCheckInTimeIsValid() throws SQLException {

        assertTrue(userCheckInModel.isTime("edOua1", 8));
    }

    @Test
    @Order(48)
    void testisTime_returnFalse_IfCheckInTimeIsInvalid() throws SQLException {

        assertFalse(userCheckInModel.isTime("edOua1", 7));
    }

    @Test
    @Order(49)
    void testisCheckedIn_returnFalse_IfNotCheckedIn() throws SQLException {

        assertFalse(userCheckInModel.isCheckedIn("edOua1"));
    }

    @Test
    @Order(50)
    void testisCheckedIn_returnTrue_IfCheckedIn() throws SQLException {

        PreparedStatement preparedStatement = null;
        String query = "UPDATE booking SET check_in = ? WHERE id = ?;";
        String query2 = "UPDATE booking SET check_in = ? WHERE id = ?;";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Y");
            preparedStatement.setString(2, "edOua1");
            preparedStatement.executeUpdate();
            assertTrue(userCheckInModel.isCheckedIn("edOua1"));
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "N");
            preparedStatement.setString(2, "edOua1");
            preparedStatement.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            preparedStatement.close();

        }
    }

    @Test
    @Order(51)
    void testcheckIn_returnCheckedIn_IfDatabaseSuccessfullyUpdated() throws SQLException {

        userCheckInModel.checkIn("edOua1");
        String query = "Select * from booking where id = ? and check_in = ? ";
        String query2 = "UPDATE booking set check_in = ? where id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "edOua1");
            preparedStatement.setString(2, "Y");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("Y", resultSet.getString("check_in"));
            }
            else {
                fail();
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "N");
            preparedStatement.setString(2, "edOua1");
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
    @Order(52)
    void testGetUserBooking_returnNotNull_IfBookingExist() throws SQLException {

        LocalDate date = LocalDate.of(2022, 6,1 );
        ArrayList<Booking> bookings = userCheckInModel.getUserBooking("a", "Pending", date);
        assertNotNull(bookings);
    }

    @Test
    @Order(53)
    void testGetUserBooking_returnNull_IfBookingNotExist() throws SQLException {

        LocalDate date = LocalDate.of(2024, 6,1 );
        ArrayList<Booking> bookings = userCheckInModel.getUserBooking("a", "Pending", date);
        assertNotNull(bookings);
    }
}
