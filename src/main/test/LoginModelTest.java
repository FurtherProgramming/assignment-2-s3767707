package main.test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;
import main.model.LoginModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class LoginModelTest {

    private static LoginModel loginModel;

    @BeforeAll
    static void setUpBeforeClass(){

        loginModel = new LoginModel();
    }

    @Test
    void testUsername_returnFalse_IfUsernameIsEmpty() throws SQLException {

        boolean bool = loginModel.isLogin("","test");
        assertFalse(bool);
    }

    @Test
    void testPassword_returnFalse_IfPasswordIsEmpty() throws SQLException {

        boolean bool = loginModel.isLogin("test","");
        assertFalse(bool);
    }

    @Test
    void testPassword_returnTrue_IfUsernameAndPasswordIsValid() throws SQLException {

        boolean bool = loginModel.isLogin("test","test");
        assertTrue(bool);
    }

    @Test
    void testPassword_returnFalse_IfUsernameIsInvalid() throws SQLException {

        boolean bool = loginModel.isLogin("test1","test");
        assertFalse(bool);
    }

    @Test
    void testPassword_returnFalse_IfPasswordIsInvalid() throws SQLException {

        boolean bool = loginModel.isLogin("test","test1");
        assertFalse(bool);
    }
}
