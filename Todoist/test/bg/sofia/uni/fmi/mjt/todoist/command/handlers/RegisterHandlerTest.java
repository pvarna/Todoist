package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegisterHandlerTest {

    @Test
    void testExecuteWithOneRegistration() {
        String assertMessage = "Wrong register";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("sonik27", "123")), null);

        assertEquals("Registration successful", handler.execute(), assertMessage);
    }
}