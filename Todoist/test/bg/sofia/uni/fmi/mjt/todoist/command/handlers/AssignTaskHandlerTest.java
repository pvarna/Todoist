package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongCreatorException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AssignTaskHandlerTest {

    @Test
    void testExecuteWithWrongAdmin() {
        String assertMessage = "Only the admin can assign tasks";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("assign", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "assign");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task")), "assign");
        handler.execute();
        handler = HandlerCreator.of(new Command("REGISTER", List.of("assign2", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-USER", List.of("collaboration=colab", "user=assign2")), "assign");
        handler.execute();

        assertThrows(WrongCreatorException.class,
                () -> HandlerCreator.of(new Command("ASSIGN-TASK", List.of("collaboration=colab", "user=assign", "task=task")), "assign2").execute(),
                assertMessage);
    }

    @Test
    void testExecute() {
        String assertMessage = "Only the admin can assign tasks";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("assignC", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "assignC");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task", "collaboration=colab")), "assignC");
        handler.execute();
        handler = HandlerCreator.of(new Command("REGISTER", List.of("assignC2", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-USER", List.of("collaboration=colab", "user=assignC2")), "assignC");
        handler.execute();
        handler = HandlerCreator.of(new Command("ASSIGN-TASK", List.of("collaboration=colab", "user=assignC2", "task=task")), "assignC");

        assertEquals("Task assigned successfully", handler.execute(), assertMessage);
    }

}
