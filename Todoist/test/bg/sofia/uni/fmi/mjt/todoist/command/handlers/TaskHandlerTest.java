package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskHandlerTest {

    @Test
    void testWrongDate() {
        String assertMessage = "Wrong date format";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("date", "add")), null);
        handler.execute();

        assertThrows(InvalidCommandException.class,
                () -> HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=2022-06-11")), "date"),
                assertMessage);
    }

    @Test
    void testWrongDueDate() {
        String assertMessage = "Wrong date format";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("due-date", "add")), null);
        handler.execute();

        assertThrows(InvalidCommandException.class,
                () -> HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "due-date=11/6/2022")), "due-date"),
                assertMessage);
    }

    @Test
    void testWrongNewDate() {
        String assertMessage = "Wrong date format";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("new-date", "add")), null);
        handler.execute();

        assertThrows(InvalidCommandException.class,
                () -> HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "new-date=6-11-2022")), "new-date"),
                assertMessage);
    }

    @Test
    void testWronBoolean() {
        String assertMessage = "Wrong boolean value";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("boolean", "add")), null);
        handler.execute();

        assertThrows(InvalidCommandException.class,
                () -> HandlerCreator.of(new Command("LIST-TASKS", List.of("completed=alabala")), "boolean"),
                assertMessage);
    }
}