package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AddTaskHandlerTest {

    @Test
    void testExecuteWithInboxTask() {
        String assertMessage = "Wrong adding";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("add-in", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task")), "add-in");
        assertEquals("Task successfully added", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithDatedTask() {
        String assertMessage = "Wrong adding";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("add-d", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2022")), "add-d");
        assertEquals("Task successfully added", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithCollaborationTask() {
        String assertMessage = "Wrong adding";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("add-c", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "add-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2022", "collaboration=colab")), "add-c");
        assertEquals("Task successfully added", handler.execute(), assertMessage);
    }
}