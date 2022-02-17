package bg.sofia.uni.fmi.mjt.todoist.user;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchUserException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDatabaseTest {

    @Test
    public void testRegisterWithNullUsername() {
        String assertMessage = "Username shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new UserDatabase().register(null, "pass"), assertMessage);
    }

    @Test
    public void testRegisterWithEmptyUsername() {
        String assertMessage = "Username shouldn't be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new UserDatabase().register("", "pass"), assertMessage);
    }

    @Test
    public void testRegisterWithNullPassword() {
        String assertMessage = "Password shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new UserDatabase().register("user", null), assertMessage);
    }

    @Test
    public void testRegisterWithEmptyPassword() {
        String assertMessage = "Password shouldn't be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new UserDatabase().register("user", ""), assertMessage);
    }

    @Test
    public void testRegisterWithExistingUser() {
        String assertMessage = "Usernames shouldn't be repeated";

        UserDatabase database = new UserDatabase();
        database.register("pesho", "nz");

        assertThrows(UserAlreadyExistsException.class, () -> database.register("pesho", "nz"),
                assertMessage);
    }

    @Test
    public void testRegisterWithOneUser() {
        String assertMessage = "Wrong registration";

        UserDatabase database = new UserDatabase();
        database.register("pesho", "nz");

        User user = database.getUser("pesho");
        assertEquals(user.getUsername(), "pesho", assertMessage);
        assertEquals(user.getPassword(), "nz", assertMessage);
    }

    @Test
    public void testLoginWithNullUsername() {
        String assertMessage = "Username shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new UserDatabase().login(null, "pass"), assertMessage);
    }

    @Test
    public void testLoginWithEmptyUsername() {
        String assertMessage = "Username shouldn't be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new UserDatabase().login("", "pass"), assertMessage);
    }

    @Test
    public void testLoginWithNullPassword() {
        String assertMessage = "Password shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new UserDatabase().login("user", null), assertMessage);
    }

    @Test
    public void testLoginWithEmptyPassword() {
        String assertMessage = "Password shouldn't be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new UserDatabase().login("user", ""), assertMessage);
    }

    @Test
    public void testLoginWithNonExistingUser() {
        String assertMessage = "Only registered users can log in";

        UserDatabase database = new UserDatabase();
        database.register("pesho", "nz");

        assertThrows(NoSuchUserException.class, () -> database.login("gosho", "nz"),
                assertMessage);
    }

    @Test
    public void testLoginWithWrongPassword() {
        String assertMessage = "The password should be correct";

        UserDatabase database = new UserDatabase();
        database.register("pesho", "nz");

        assertThrows(WrongPasswordException.class, () -> database.login("pesho", "nzz"),
                assertMessage);
    }

    @Test
    public void testSuccessfulLogin() {
        UserDatabase database = new UserDatabase();
        database.register("pesho", "nz");

        database.login("pesho", "nz");
    }
}