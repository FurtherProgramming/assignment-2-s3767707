package main.test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.SQLConnection;
import main.model.LoginModel;
import main.model.RegisterModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RegisterModelTest {

    private static RegisterModel registerModel = new RegisterModel();
    static Connection connection;

    @BeforeAll
    static void setUpBeforeClass(){

        registerModel = new RegisterModel();
        connection = SQLConnection.connect();
        if (connection == null)
            System.exit(1);
    }

    @Test
    void testEmployerId_returnTrue_IfEmployerIdExists() throws SQLException {

        boolean bool = registerModel.employerIdExist("1");
        assertTrue(bool);
    }

    @Test
    void testEmployerId_returnTrue_IfEmployerIdNotExists() throws SQLException {

        boolean bool = registerModel.employerIdExist("10");
        assertFalse(bool);
    }

    @Test
    void testUsername_returnTrue_IfUsernameExists() throws SQLException {

        boolean bool = registerModel.usernameExist("test");
        assertTrue(bool);
    }

    @Test
    void testUsername_returnFalse_IfUsernameNotExists() throws SQLException {

        boolean bool = registerModel.usernameExist("d");
        assertFalse(bool);
    }

    @Test
    void testRegister_returnTrue_IfAllInfoIsValid() throws SQLException {

        boolean bool = registerModel.register("3","b","b","user","b","1234","What is your favourite colour?","Blue");
        assertTrue(bool);

    }

    @Test
    void testRegister_returnTrue_IfDatabaseIsUdated() throws SQLException {

        boolean bool = registerModel.employerIdExist("3");
        assertTrue(bool);
        PreparedStatement preparedStatement = null;
        String query = "DELETE FROM Employee WHERE emp_id = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "3");
            preparedStatement.executeUpdate();
            bool = true;
        }
        catch (Exception e)
        {
            bool = false;
        }finally {
            preparedStatement.close();

        }
    }

}
