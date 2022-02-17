package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddCollaborationHandlerTest {
    @Test
    void testExecute() {
        String assertMessage = "Wrong adding";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("addC", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "addC");
        assertEquals("Collaboration added successfully", handler.execute(), assertMessage);
    }
}