package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteTaskHandlerTest {

    @Test
    void testExecuteWithInboxTask() {
        String assertMessage = "Wrong deleting";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("delete-in", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task")), "delete-in");
        handler.execute();
        handler = HandlerCreator.of(new Command("DELETE-TASK", List.of("name=task")), "delete-in");
        assertEquals("Task deleted successfully", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithDatedTask() {
        String assertMessage = "Wrong deleting";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("delete-d", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2020")), "delete-d");
        handler.execute();
        handler = HandlerCreator.of(new Command("DELETE-TASK", List.of("name=task", "date=15.02.2020")), "delete-d");
        assertEquals("Task deleted successfully", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithCollaborationTask() {
        String assertMessage = "Wrong updating";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("delete-c", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "delete-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "collaboration=colab")), "delete-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("DELETE-TASK", List.of("name=task", "collaboration=colab")), "delete-c");
        assertEquals("Task deleted successfully", handler.execute(), assertMessage);
    }
}