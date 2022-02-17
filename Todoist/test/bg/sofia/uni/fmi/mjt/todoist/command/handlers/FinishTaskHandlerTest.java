package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FinishTaskHandlerTest {

    @Test
    void testExecuteWithInboxTask() {
        String assertMessage = "Wrong finishing";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("finish-in", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task")), "finish-in");
        handler.execute();
        handler = HandlerCreator.of(new Command("FINISH-TASK", List.of("name=task")), "finish-in");

        assertEquals("Task marked as completed successfully", handler.execute());
    }

    @Test
    void testExecuteWithDatedTask() {
        String assertMessage = "Wrong finishing";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("finish-d", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=16.02.2022")), "finish-d");
        handler.execute();
        handler = HandlerCreator.of(new Command("FINISH-TASK", List.of("name=task", "date=16.02.2022")), "finish-d");

        assertEquals("Task marked as completed successfully", handler.execute());
    }

    @Test
    void testExecuteWithCollaborationTask() {
        String assertMessage = "Wrong finishing";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("finish-c", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "finish-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "collaboration=colab")), "finish-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("FINISH-TASK", List.of("name=task", "collaboration=colab")), "finish-c");

        assertEquals("Task marked as completed successfully", handler.execute());
    }
}