package main.test;

import main.SQLConnection;
import main.model.ResetPasswordModel;
import main.model.User;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ResetPasswordTest {

    private static ResetPasswordModel resetPasswordModel = new ResetPasswordModel();
    private static Connection connection;

    @BeforeAll
    static void setUpBeforeClass() {

        resetPasswordModel = new ResetPasswordModel();
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
    void testValidateUsername_returnNotNull_IfUsernameValid() throws SQLException {

        User user = resetPasswordModel.validateUsername("a");
        assertNotNull(user);
    }

    @Test
    @Order(2)
    void testValidateUsername_returnNull_IfUsernameNotValid() throws SQLException {

        User user = resetPasswordModel.validateUsername("qwe");
        assertNull(user);
    }

    @Test
    @Order(3)
    void testValidateAnswer_returnTrue_IfAnswerIsValid()  {

        assertTrue(resetPasswordModel.validateAnswer("a", "a"));
    }

    @Test
    @Order(4)
    void testValidateAnswer_returnFalse_IfAnswerIsNotValid() {

        assertFalse(resetPasswordModel.validateAnswer("a", "b"));
    }

    @Test
    @Order(5)
    void testGenerateRandomPassword_StringLengthEquals4_IfGenerateRandomStringWith4Character() {

        assertEquals(4, resetPasswordModel.generateRandomPassword(4).length());
    }

    @Test
    @Order(6)
    void testUpdatePassword_PasswordEquals_IfPasswordSuccessfullyUpdated() throws SQLException {

        resetPasswordModel.updatePassword("a", "b");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * from employee where username = ? and password = ?";
        String query2 = "UPDATE employee SET password = ? WHERE username = ?";
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "a");
            preparedStatement.setString(2, "b");
            resultSet = preparedStatement.executeQuery();
            assertEquals("b", resultSet.getString("password"));
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setString(1, "a");
            preparedStatement.setString(2, "a");
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
}
