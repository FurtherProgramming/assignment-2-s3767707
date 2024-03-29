package main.test;

import main.SQLConnection;
import main.model.AdminReportModel;
import main.model.Booking;
import org.junit.jupiter.api.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminReportTest {

    private static AdminReportModel adminReportModel = new AdminReportModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass() {

        adminReportModel = new AdminReportModel();
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
    void testExportEmployeeTable_returnNotNull_IfCSVfileCreatedAndNotEmpty() throws SQLException, IOException {

        adminReportModel.exportEmployeeTable();
        BufferedReader bufr = new BufferedReader(new FileReader("Employee-report.csv"));
        String inputLine;
        inputLine = bufr.readLine();
        assertNotNull(inputLine);
    }

    @Test
    @Order(2)
    void testExportBookingTable_returnNotNull_IfCSVFileCreatedAndNotEmpty() throws SQLException, IOException {

        adminReportModel.exportBookingTable();
        BufferedReader bufr = new BufferedReader(new FileReader("Booking-report.csv"));
        String inputLine;
        inputLine = bufr.readLine();
        assertNotNull(inputLine);
    }

    @Test
    @Order(3)
    void testExportBookingTableWithDate_returnNotNull_IfCSVFileCreatedAndNotEmpty() throws SQLException, IOException {

        adminReportModel.exportBookingTableWithDate(LocalDate.of(2021, 6, 6));
        BufferedReader bufr = new BufferedReader(new FileReader("Booking-report.csv"));
        String inputLine;
        inputLine = bufr.readLine();
        assertNotNull(inputLine);
    }

    @Test
    @Order(4)
    void testGetAllBookings_returnNotNull_IfBookingsFound() throws SQLException {

        ArrayList<Booking> bookings = adminReportModel.getAllBookings();
        for (Booking b : bookings) {
            assertNotNull(b);
        }
    }

    @Test
    @Order(5)
    void testGetAllBookingsWithDate_returnNotNull_IfBookingsFound() throws SQLException {

        ArrayList<Booking> bookings = adminReportModel.getAllBookingsWithDate(LocalDate.of(2021, 6, 6));
        for (Booking b : bookings) {
            assertNotNull(b);
        }
    }
}
