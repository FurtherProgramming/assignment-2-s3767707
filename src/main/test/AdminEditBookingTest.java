package main.test;

import main.SQLConnection;
import main.model.AdminEditBookingModel;
import main.model.Booking;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminEditBookingTest {

    private static AdminEditBookingModel adminEditBookingModel = new AdminEditBookingModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass(){

        adminEditBookingModel = new AdminEditBookingModel();
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
    void testGetUserBookings_returnNotNull_IfBookingsFound() throws SQLException {

        LocalDate date = LocalDate.of(2022, 6, 1);
        ArrayList<Booking> bookings = adminEditBookingModel.getUserBookings("Pending", date);
        for(Booking b : bookings) {
            assertNotNull(b.getBookingId());
        }
    }

    @Test
    @Order(2)
    void testEditBookingStatus_returnNotNull_IfResultFoundAfterStatusUpdated() throws SQLException {

        adminEditBookingModel.editBookingStatus("aHbZa16", "Accepted");
        String query = "Select * from booking where id = ? and status = ? ";
        String query2 = "UPDATE booking set status = ? where id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "OIwIa1");
            preparedStatement.setString(2, "Accepted");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("Accepted", resultSet.getString("status"));
            }
            else {
                fail();
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "Pending");
            preparedStatement.setString(2, "aHbZa16");
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


}
