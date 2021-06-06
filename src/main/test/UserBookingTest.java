package main.test;

import main.SQLConnection;
import main.model.UserBookingModel;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserBookingTest {

    private static UserBookingModel userBookingModel = new UserBookingModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass() {

        userBookingModel = new UserBookingModel();
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
    void testvalidateMultipleBookings_returnBookingList_IfUsernameAndStatusMatched() throws SQLException {

        assertNotNull(userBookingModel.validateMultipleBookings(LocalDate.of(2022, 6, 1), "0800"));
    }

    @Test
    @Order(2)
    void testBookFunction_returnBooking_IfDatabaseUpdated() throws SQLException {

        userBookingModel.book("124", "1", LocalDate.of(2021, 1, 1), "a", "0800", "N");
        String query = "Select * from booking where id = ?";
        String query2 = "DELETE from booking where id = ? ";
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "124");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                assertEquals("124", resultSet.getString("id"));
            } else {
                fail();
            }
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "124");
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
    void testUsernameExistInList_returnTrue_IfUsernameExist() throws SQLException {

        assertTrue(userBookingModel.UsernameExistInList("q", LocalDate.of(2022, 7, 1), "0800"));
    }

    @Test
    @Order(4)
    void testPreviousBooking_EqualPreviousBookingSeatId_IfSearchForPreviousBooking() throws SQLException {

        assertEquals("1", userBookingModel.previousBooking("a"));
    }

    @Test
    @Order(5)
    void testCheckHourBeforeEdit_returnFalse_IfDateTimeEquals48Hours() throws SQLException {

        ArrayList<String> seatIds = userBookingModel.isBooked(LocalDate.of(2022, 6, 1), "0800");
        for (String id : seatIds) {
            assertNotNull(id);
        }
    }

    @Test
    @Order(6)
    void testCheckHourBeforeEdit_returnFalse_IfDateTimeWithin48Hours() throws SQLException {

        int count = 0;
        ArrayList<String> seatIds = userBookingModel.allSeatId();
        for (String id : seatIds) {
            count++;
        }
        assertEquals(16, count);
    }

    @Test
    @Order(7)
    void testPreviousSit_EqualsTo_IfBookingIdMatchesPreviousBookingId() throws SQLException {

        assertEquals("OIwIa1", userBookingModel.previousSit("a").getBookingId());
    }

    @Test
    @Order(8)
    void testGetAdjacentUserOfPreviousSit_EqualsTo_IfUsernameOfEmployeeThatSitBesidePreviousCheckInMatches() throws SQLException {

        ArrayList<String> usernames = userBookingModel.getAdjacentUserOfPreviousSit(LocalDate.of(2021, 7, 1), "0800", "2");
        assertEquals("w", usernames.get(0));
    }

    @Test
    @Order(9)
    void testGetSeatsOfPreviousAdjacentUser_EqualsTo_IfSeatIdMatchesSeatIdOfPreviousUser() throws SQLException {

        ArrayList<String> usernames = userBookingModel.getAdjacentUserOfPreviousSit(LocalDate.of(2021, 7, 1), "0800", "2");
        ArrayList<String> seats = userBookingModel.getSeatsOfPreviousAdjacentUser(LocalDate.of(2021, 7, 3), "0800", usernames);
        assertEquals("16", seats.get(0));
    }

    @Test
    @Order(10)
    void testSeatsBesidePrevUser_EqualsTo_IfSeatIdMatchesSeatIdBesidePreviousUser() throws SQLException {

        ArrayList<String> usernames = userBookingModel.getAdjacentUserOfPreviousSit(LocalDate.of(2021, 7, 1), "0800", "2");
        ArrayList<String> seats = userBookingModel.getSeatsOfPreviousAdjacentUser(LocalDate.of(2021, 7, 3), "0800", usernames);
        ArrayList<String> seatsBesidePrevUsers = userBookingModel.SeatsBesidePrevUser(seats);
        assertEquals("1", seatsBesidePrevUsers.get(0));
        assertEquals("15", seatsBesidePrevUsers.get(1));
    }
}
