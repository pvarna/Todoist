package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetTaskHandlerTest {

    @Test
    void testExecuteWithInboxTask() {
        String assertMessage = "Wrong task getting";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("get-i", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "due-date=18.02.2022", "description=\"good description\"")), "get-i");
        handler.execute();
        handler = HandlerCreator.of(new Command("GET-TASK", List.of("name=task")), "get-i");

        String expected = "Name: task" + System.lineSeparator() +
                "Due date: 18.02.2022" + System.lineSeparator() +
                "Description: good description";
        assertEquals(expected, handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithDatedTask() {
        String assertMessage = "Wrong task getting";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("get-d", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2022", "due-date=18.02.2022", "description=\"good description\"")), "get-d");
        handler.execute();
        handler = HandlerCreator.of(new Command("GET-TASK", List.of("name=task", "date=15.02.2022")), "get-d");

        String expected = "Name: task" + System.lineSeparator() +
                "Date: 15.02.2022" + System.lineSeparator() +
                "Due date: 18.02.2022" + System.lineSeparator() +
                "Description: good description";
        assertEquals(expected, handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithCollaborationTask() {
        String assertMessage = "Wrong task getting";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("get-c", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "get-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "collaboration=colab")), "get-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("GET-TASK", List.of("name=task", "collaboration=colab")), "get-c");

        assertEquals("Name: task", handler.execute(), assertMessage);
    }
}