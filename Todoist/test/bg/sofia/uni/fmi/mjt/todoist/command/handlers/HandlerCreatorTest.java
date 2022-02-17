package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HandlerCreatorTest {

    private static CommandHandler handler;

    @BeforeAll
    static void registerPesho() {
        handler = HandlerCreator.of(new Command("REGISTER", List.of("p_varna", "123")), null);
        handler.execute();
    }

    @Test
    void testFactoryMethodWithRegisterCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("REGISTER", List.of("sonik27", "123"));

        handler = HandlerCreator.of(command, null);
        assertTrue(handler instanceof RegisterHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithLoginCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("LOGIN", List.of("p_varna", "123"));

        handler = HandlerCreator.of(command, null);
        assertTrue(handler instanceof LoginHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithAddTaskCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("ADD-TASK", List.of("name=task"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof AddTaskHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithUpdateTaskCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("UPDATE-TASK", List.of("name=task", "description=dsc"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof UpdateTaskHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithSetDateCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("SET-DATE", List.of("name=task", "date=18.02.2022"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof SetDateHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithRemoveDateCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("REMOVE-DATE", List.of("name=task", "date=18.02.2022"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof RemoveDateHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithChangeDateCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("CHANGE-DATE", List.of("name=task", "date=18.02.2022", "new-date=19.02.2022"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof ChangeDateHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithGetTaskCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("GET-TASK", List.of("name=task"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof GetTaskHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithListDashboardCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("LIST-DASHBOARD", Collections.EMPTY_LIST);

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof ListDashboardHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithFinishTaskCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("FINISH-TASK", List.of("name=task"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof FinishTaskHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithListTasksCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("LIST-TASKS", List.of("name=task", "completed=true"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof ListTasksHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithDeleteTaskCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("DELETE-TASK", List.of("name=task"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof DeleteTaskHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithAddCollaborationCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("ADD-COLLABORATION", List.of("name=colab"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof AddCollaborationHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithDeleteCollaborationCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("DELETE-COLLABORATION", List.of("name=colab"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof DeleteCollaborationHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithListCollaborationsCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("LIST-COLLABORATIONS", Collections.EMPTY_LIST);

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof ListCollaborationsHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithAddUserCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("ADD-USER", List.of("collaboration=colab", "user=p_varna"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof AddUserHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithAssignTaskCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("ASSIGN-TASK", List.of("collaboration=colab", "user=p_varna", "task=task"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof AssignTaskHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithListCollaborationTasksCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("LIST-COLLABORATION-TASKS", List.of("name=colab"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof ListCollaborationTasksHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithListCollaborationUsersCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("LIST-COLLABORATION-USERS", List.of("name=colab"));

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof ListCollaborationUsersHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithHelpCommand() {
        String assertMessage = "Wrong handler creation";

        Command command = new Command("HELP", Collections.EMPTY_LIST);

        handler = HandlerCreator.of(command, "p_varna");
        assertTrue(handler instanceof HelpHandler, assertMessage);
    }

    @Test
    void testFactoryMethodWithMissingMandatoryArgument() {
        String assertMessage = "There is a strictly defined list of the commands anf their mandatory arguments";

        Command command = new Command("ADD-TASK", List.of("description=zdr"));

        assertThrows(InvalidCommandException.class,
                () -> handler = HandlerCreator.of(command, "p_varna"), assertMessage);
    }

    @Test
    void testFactoryMethodWithSingleArgument() {
        String assertMessage = "All arguments should be double - separated by '='";

        Command command = new Command("ADD-TASK", List.of("description"));

        assertThrows(InvalidCommandException.class,
                () -> handler = HandlerCreator.of(command, "p_varna"), assertMessage);
    }

    @Test
    void testFactoryMethodWithWrongDoubleArgument() {
        String assertMessage = "There is a list with all valid double arguments";

        Command command = new Command("ADD-TASK", List.of("name=task wrong=nz"));

        assertThrows(InvalidCommandException.class,
                () -> handler = HandlerCreator.of(command, "p_varna"), assertMessage);
    }
}