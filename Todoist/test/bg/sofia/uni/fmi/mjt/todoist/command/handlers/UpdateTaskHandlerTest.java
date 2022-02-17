package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateTaskHandlerTest {

    @Test
    void testExecuteWithInboxTask() {
        String assertMessage = "Wrong updating";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("update-in", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task")), "update-in");
        handler.execute();
        handler = HandlerCreator.of(new Command("UPDATE-TASK", List.of("name=task", "description=dsc", "due-date=20.02.2022")), "update-in");
        assertEquals("Task updated successfully", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithDatedTask() {
        String assertMessage = "Wrong updating";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("update-d", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2020")), "update-d");
        handler.execute();
        handler = HandlerCreator.of(new Command("UPDATE-TASK", List.of("name=task", "date=15.02.2020", "description=dsc", "due-date=20.02.2022")), "update-d");
        assertEquals("Task updated successfully", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithCollaborationTask() {
        String assertMessage = "Wrong updating";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("update-c", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "update-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "collaboration=colab")), "update-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("UPDATE-TASK", List.of("name=task", "description=dsc", "due-date=20.02.2022", "collaboration=colab")), "update-c");
        assertEquals("Task updated successfully", handler.execute(), assertMessage);
    }
}