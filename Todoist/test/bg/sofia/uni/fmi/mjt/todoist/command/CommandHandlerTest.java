package bg.sofia.uni.fmi.mjt.todoist.command;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongAuthenticationException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandHandlerTest {

    @Test
    void testAssertCommandIsValidWithNullCommand() {
        String assertMessage = "Command shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> CommandHandler.assertCommandIsValid(null, null),
                assertMessage);
    }

    @Test
    void testAssertCommandIsValidWithInvalidCommand() {
        String assertMessage = "There is a strictly defined list of commands that are supported";

        Command command = new Command("INVALID-COMMAND", null);

        assertThrows(InvalidCommandException.class,
                () -> CommandHandler.assertCommandIsValid(command, null),
                assertMessage);
    }

    @Test
    void testAssertCommandIsValidWithValidCommandAndInvalidNumberOfArguments() {
        String assertMessage = "There is a strictly defined list of the commands and the number of arguments";

        Command command = new Command("REGISTER", List.of("Pesho"));

        assertThrows(InvalidCommandException.class,
                () -> CommandHandler.assertCommandIsValid(command, null),
                assertMessage);
    }

    @Test
    void testAssertCommandIsValidWithNullUsernameWhenRequired() {
        String assertMessage = "The username shouldn't be null for commands other than 'login', 'register' and 'quit'";

        Command command = new Command("ADD-TASK", List.of("name=task"));

        assertThrows(WrongAuthenticationException.class,
                () -> CommandHandler.assertCommandIsValid(command, null),
                assertMessage);
    }

    @Test
    void testAssertCommandIsValidWithValidCommands() {
        Command command1 = new Command("REGISTER", List.of("p_varna", "123"));
        Command command2 = new Command("ADD-TASK", List.of("name=task"));


        CommandHandler.assertCommandIsValid(command1, null);
        CommandHandler.assertCommandIsValid(command2, "pesho");
    }
}