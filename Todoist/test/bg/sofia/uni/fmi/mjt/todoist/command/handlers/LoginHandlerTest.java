package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchUserException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoginHandlerTest {

    @Test
    void testSuccessfulLogin() {
        String assertMessage = "Wrong login";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("enev", "123")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("LOGIN", List.of("enev", "123")), null);
        assertEquals("Login successful", handler.execute(), assertMessage);
    }
}