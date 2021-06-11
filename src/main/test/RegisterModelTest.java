package main.test;

import main.SQLConnection;
import main.model.LoginModel;
import main.model.RegisterModel;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegisterModelTest {

    private static RegisterModel registerModel = new RegisterModel();
    private static LoginModel loginModel = new LoginModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass() {

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
    void testEmployeeId_returnTrue_IfEmployeeIdExists() throws SQLException {

        boolean bool = registerModel.employeeIdExist("1");
        assertTrue(bool);
    }

    @Test
    @Order(2)
    void testEmployeeId_returnTrue_IfEmployeeIdNotExists() throws SQLException {

        boolean bool = registerModel.employeeIdExist("10");
        assertFalse(bool);
    }

    @Test
    @Order(3)
    void testUsername_returnTrue_IfUsernameExists() throws SQLException {

        boolean bool = loginModel.usernameExist("test");
        assertTrue(bool);
    }

    @Test
    @Order(4)
    void testUsername_returnFalse_IfUsernameNotExists() throws SQLException {

        boolean bool = loginModel.usernameExist("d");
        assertFalse(bool);
    }

    @Test
    @Order(5)
    void testRegister_returnTrue_IfAllInfoIsValid() throws SQLException {

        boolean bool = registerModel.register("c", "c", "c", "user", "c", "c", "What is your favourite colour?", "c");
        assertTrue(bool);
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM Employee WHERE emp_id = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "c");
            preparedStatement.executeUpdate();
            bool = true;
        } catch (Exception e) {
            bool = false;
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }

        }

    }

    @Test
    @Order(6)
    void testRegister_returnTrue_IfDatabaseIsUpdated() throws SQLException {

        boolean bool = registerModel.employeeIdExist("1");
        assertTrue(bool);

    }

}
