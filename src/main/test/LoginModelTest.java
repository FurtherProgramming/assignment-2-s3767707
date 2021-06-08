package main.test;


import main.model.LoginModel;
import main.model.User;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginModelTest {

    private static LoginModel loginModel;

    @BeforeAll
    static void setUpBeforeClass() {

        loginModel = new LoginModel();
    }

    @Test
    @Order(1)
    void testUsername_returnNull_IfUsernameIsEmpty() throws SQLException {

        User user = loginModel.isLogin("", "test");
        assertNull(user);
    }

    @Test
    @Order(2)
    void testPassword_returnNull_IfPasswordIsEmpty() throws SQLException {

        User user = loginModel.isLogin("test", "");
        assertNull(user);
    }

    @Test
    @Order(3)
    void testPassword_returnNotNull_IfUsernameAndPasswordIsValid() throws SQLException {

        User user = loginModel.isLogin("test", "test");
        assertNotNull(user);
    }

    @Test
    @Order(4)
    void testPassword_returnNull_IfUsernameIsInvalid() throws SQLException {

        User user = loginModel.isLogin("test1", "test");
        assertNull(user);
    }

    @Test
    @Order(5)
    void testPassword_returnNull_IfPasswordIsInvalid() throws SQLException {

        User user = loginModel.isLogin("test", "test1");
        assertNull(user);
    }

    @Test
    @Order(6)
    void testAdmin_returnFalse_IfUserNotAdmin() throws SQLException {

        boolean bool = loginModel.isAdmin("a", "a");
        assertFalse(bool);
    }

    @Test
    @Order(7)
    void testAdmin_returnTrue_IfUserIsAdmin() throws SQLException {

        boolean bool = loginModel.isAdmin("test", "test");
        assertTrue(bool);
    }

    @Test
    @Order(8)
    void testUsername_returnTrue_IfUsernameExist() throws SQLException {

        boolean bool = loginModel.usernameExist("test");
        assertTrue(bool);
    }

    @Test
    @Order(9)
    void testUsername_returnTrue_IfUsernameNotExist() throws SQLException {

        boolean bool = loginModel.usernameExist("test1");
        assertFalse(bool);
    }
}
