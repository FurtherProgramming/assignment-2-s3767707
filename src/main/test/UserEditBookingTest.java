package main.test;

import main.SQLConnection;
import main.model.Booking;
import main.model.LoginModel;
import main.model.RegisterModel;
import main.model.UserEditBookingModel;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserEditBookingTest {

    private static UserEditBookingModel userEditBookingModel = new UserEditBookingModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass(){

        userEditBookingModel = new UserEditBookingModel();
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @AfterAll
    static  void setUpAfterClass() throws SQLException {

        connection.close();

    }

    @Test
    @Order(1)
    void testGetUserBookings_returnBookingList_IfUsernameAndStatusMatched() throws SQLException {

        ArrayList<Booking> bookings = userEditBookingModel.getUserBookings("a", "Pending");
        for (Booking booking : bookings) {
            assertNotNull(booking.getBookingId());
        }
    }

    @Test
    @Order(2)
    void testGetUserBookings_returnNull_IfUsernameNotMatched() throws SQLException {

        ArrayList<Booking> bookings = userEditBookingModel.getUserBookings("b", "Pending");
        for (Booking booking : bookings) {
            assertNotNull(booking.getBookingId());
        }
    }

    @Test
    @Order(3)
    void testGetUserBookings_returnNull_IfStatusNotMatched() throws SQLException {

        ArrayList<Booking> bookings = userEditBookingModel.getUserBookings("a", "XXX");
        for (Booking booking : bookings) {
            assertNull(booking.getBookingId());
        }
    }

    @Test
    @Order(4)
    void testCheckHourBeforeEdit_returnTrue_IfDateAndTimeMoreThan48Hours() throws SQLException {

        Boolean bool = userEditBookingModel.checkHourBeforeEdit(LocalDate.of(2021,6,4), "1400", LocalDate.of(2021,6,2), LocalDateTime.of(2021, 6, 2, 13, 59));
        assertTrue(bool);
    }

    @Test
    @Order(5)
    void testCheckHourBeforeEdit_returnFalse_IfDateTimeEquals48Hours() throws SQLException {

        Boolean bool = userEditBookingModel.checkHourBeforeEdit(LocalDate.of(2021,6,4), "1400", LocalDate.of(2021,6,2), LocalDateTime.of(2021, 6, 2, 14, 00));
        assertFalse(bool);
    }

    @Test
    @Order(6)
    void testCheckHourBeforeEdit_returnFalse_IfDateTimeWithin48Hours() throws SQLException {

        Boolean bool = userEditBookingModel.checkHourBeforeEdit(LocalDate.of(2021,6,4), "1400", LocalDate.of(2021,6,3), LocalDateTime.of(2021, 6, 3, 14, 00));
        assertFalse(bool);
    }

    @Test
    @Order(7)
    void testRemoveBooking_returnTrue_IfBookingIdValid() throws SQLException {
        userEditBookingModel.deleteBooking("edOua1");
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO Booking (id, username, seat_id, booking_date, booking_time, status, check_in) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String query2 = "Select * from booking where id = ?";
        try {
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "edOua1");
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                fail();
            }
            else {
                assertNull(null); ;
            }
            LocalDate date = LocalDate.of(2022, 6, 1);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "edOua1");
            preparedStatement.setString(2, "a");
            preparedStatement.setString(3, "1");
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
    @Order(8)
    void testRemoveBooking_returnFalse_IfBookingIdNotValid() throws SQLException {
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        int count2 = 0;
        String query = "Select * from booking ";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count++;
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
        userEditBookingModel.deleteBooking("qwe");
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count2++;
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
        assertEquals(count, count2);
    }

    @Test
    @Order(9)
    void testupdateBooking_returnUpdatedInfo_IfUpdatedBookingFound() throws SQLException {

        userEditBookingModel.updateBooking("edOua1", "2", "1400");
        String query = "Select * from booking where id = ? and seat_id = ? and booking_time = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "edOua1");
            preparedStatement.setString(2, "2");
            preparedStatement.setString(3, "1400");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("2", resultSet.getString("seat_id"));
                assertEquals("1400", resultSet.getString("booking_time"));
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
        userEditBookingModel.updateBooking("edOua1", "1", "0800");
    }
}
