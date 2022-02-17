package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SetDateHandlerTest {

    @Test
    void testExecuteWithPersonalTask() {
        String assertMessage = "Wrong date setting";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("set-p", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task")), "set-p");
        handler.execute();
        handler = HandlerCreator.of(new Command("SET-DATE", List.of("name=task", "date=15.02.2022")), "set-p");

        assertEquals("Task date set successfully", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithCollaborationTask() {
        String assertMessage = "Wrong date setting";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("set-c", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "set-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "collaboration=colab")), "set-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("SET-DATE", List.of("name=task", "collaboration=colab", "date=15.02.2022")), "set-c");

        assertEquals("Task date set successfully", handler.execute(), assertMessage);
    }
}