package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeDateHandlerTest {

    @Test
    void testExecuteWithPersonalTask() {
        String assertMessage = "Wrong date change";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("change-p", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2022")), "change-p");
        handler.execute();
        handler = HandlerCreator.of(new Command("CHANGE-DATE", List.of("name=task", "date=15.02.2022", "new-date=16.02.2022")), "change-p");

        assertEquals("Task date changed successfully", handler.execute(), assertMessage);
    }

    @Test
    void testExecuteWithCollaborationTask() {
        String assertMessage = "Wrong date change";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("change-c", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "change-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "date=15.02.2022", "collaboration=colab")), "change-c");
        handler.execute();
        handler = HandlerCreator.of(new Command("CHANGE-DATE", List.of("name=task", "collaboration=colab", "date=15.02.2022", "new-date=16.02.2022")), "change-c");

        assertEquals("Task date changed successfully", handler.execute(), assertMessage);
    }
}