package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RemoveDateHandlerTest {

    @Test
    void testExecuteWithPersonalTask() {
        String assertMessage = "Wrong date removal";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("removeD-p", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2022")), "removeD-p");
        handler.execute();
        handler = HandlerCreator.of(new Command("REMOVE-DATE", List.of("name=task", "date=15.02.2022")), "removeD-p");

        assertEquals("Task date removed successfully", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithCollaborationTask() {
        String assertMessage = "Wrong date removal";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("removeD-c", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "removeD-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2022", "collaboration=colab")), "removeD-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("REMOVE-DATE", List.of("name=task", "collaboration=colab", "date=15.02.2022")), "removeD-c");

        assertEquals("Task date removed successfully", handler.execute(), assertMessage);
    }
}