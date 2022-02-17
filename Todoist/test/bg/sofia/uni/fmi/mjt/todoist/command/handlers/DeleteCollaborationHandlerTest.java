package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DeleteCollaborationHandlerTest {

    @Test
    void testExecutable() {
        String assertMessage = "Wrong deleting";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("deleteC", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-COLLABORATION", List.of("name=colab")), "deleteC");
        handler.execute();
        handler = HandlerCreator.of(new Command("DELETE-COLLABORATION", List.of("name=colab")), "deleteC");
        assertEquals("Collaboration deleted successfully", handler.execute(), assertMessage);
    }

}