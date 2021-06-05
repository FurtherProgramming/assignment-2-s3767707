package main.test.modelTest;

import main.SQLConnection;
import main.model.Seat;
import main.model.SeatManagementModel;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeatManagementTest {

    private static SeatManagementModel seatManagementModel = new SeatManagementModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass() {

        seatManagementModel = new SeatManagementModel();
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @AfterAll
    static void setUpAfterClass() throws SQLException {

        connection.close();

    }

    @Test
    @Order(36)
    void testUpdateSeat_returnRestrictionCondition_IfDatabaseUpdated() throws SQLException {

        LocalDate startDate = LocalDate.of(2022, 6, 6);
        LocalDate endDate = LocalDate.of(2022, 6, 12);
        seatManagementModel.updateSeat("Restriction", startDate, endDate);
        String query = "Select * from seat where id = ? and condition = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "2");
            preparedStatement.setString(2, "Restriction");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("Restriction", resultSet.getString("condition"));
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
        seatManagementModel.resetCondition();
    }

    @Test
    @Order(37)
    void testUpdateSeat_returnLockdownCondition_IfDatabaseUpdated() throws SQLException {

        LocalDate startDate = LocalDate.of(2022, 6, 6);
        LocalDate endDate = LocalDate.of(2022, 6, 12);
        seatManagementModel.updateSeat("Lockdown", startDate, endDate);
        String query = "Select * from seat where id = ? and condition = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "2");
            preparedStatement.setString(2, "Lockdown");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("Lockdown", resultSet.getString("condition"));
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
        seatManagementModel.resetCondition();
    }

    @Test
    @Order(38)
    void testResetCondition_SeatCountIs16_IfAllNormalSeatIsSearched() throws SQLException {

        seatManagementModel.resetCondition();
        String query = "Select * from seat where condition = ?";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        int count = 0;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "Normal");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                count++;
            }
            assertEquals(16, count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

    }

    @Test
    @Order(39)
    void testGetAllSeats_SeatCountIs16_IfGetAllSeats() throws SQLException {

        ArrayList<Seat> seats = seatManagementModel.getAllSeats();
        int count = 0;
        for (Seat s : seats) {
            count++;
        }
        assertEquals(16, count);
    }

    @Test
    @Order(40)
    void testGetBookingIdAffectedByCondition_returnNotNull_IfBookingsFound() throws SQLException {

        LocalDate startDate = LocalDate.of(2021, 6, 5);
        LocalDate endDate = LocalDate.of(2021, 6, 6);
        ArrayList<String> bookingIds = seatManagementModel.getBookingIdAffectedByCondition("Restriction", startDate, endDate);
        for (String id : bookingIds) {
            assertNotNull(id);
        }
    }


}
