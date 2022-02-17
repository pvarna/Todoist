package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ListDashboardHandlerTest {

    @Test
    void testExecuteWithNoTasksForTheDay() {
        String assertMessage = "Wrong dashboard listing";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("dashboard", "add")), null);
        handler.execute();

        assertThrows(NoSuchTaskException.class,
                () -> HandlerCreator.of(new Command("LIST-DASHBOARD", Collections.EMPTY_LIST),
                        "dashboard").execute(), assertMessage);
    }

    @Test
    void testExecuteWithTwoTasksForTheDay() {
        String assertMessage = "Wrong dashboard listing";

        CommandHandler handler = HandlerCreator.of(new Command("REGISTER", List.of("dashboard2", "add")), null);
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task1", "date=17.02.2022", "description=\"good description\"")), "dashboard2");
        handler.execute();
        handler = HandlerCreator.of(new Command("ADD-TASK", List.of("name=task2", "date=17.02.2022", "due-date=21.02.2022")), "dashboard2");
        handler.execute();
        handler = HandlerCreator.of(new Command("LIST-DASHBOARD", Collections.EMPTY_LIST),
                "dashboard2");
        handler.execute();
    }
}