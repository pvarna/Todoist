package bg.sofia.uni.fmi.mjt.todoist.command;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandParserTest {

    @Test
    void testBuildCommandWithNull() {
        String assertMessage = "Command line shouldn't be null";

        assertThrows(IllegalArgumentException.class, () -> CommandParser.buildCommand(null), assertMessage);
    }

    @Test
    void testBuildCommandWithEmpty() {
        String assertMessage = "Command line shouldn't be empty";

        assertThrows(IllegalArgumentException.class, () -> CommandParser.buildCommand(""), assertMessage);
    }

    @Test
    void testBuildCommandWithOddNumberOfQuoter() {
        String assertMessage = "The command line should contain even number of quotes";

        assertThrows(InvalidCommandException.class,
                () -> CommandParser.buildCommand("register \"p varna\" \"alo"), assertMessage);
    }

    @Test
    void testBuildCommandWithOnlyMainCommand() {
        String assertMessage = "Wrong parsing";

        Command command = CommandParser.buildCommand("list-dashboard");
        assertEquals("LIST-DASHBOARD", command.mainCommand(), assertMessage);
        assertEquals(0, command.arguments().size(), assertMessage);
    }

    @Test
    void testBuildCommandWithBasicThreeArgumentCommand() {
        String assertMessage = "Wrong parsing";

        Command command = CommandParser.buildCommand("register p_varna pass");
        assertEquals("REGISTER", command.mainCommand(), assertMessage);
        assertEquals(2, command.arguments().size(), assertMessage);
        assertEquals("p_varna", command.arguments().get(0));
        assertEquals("pass", command.arguments().get(1));
    }

    @Test
    void testBuildCommandWithMultiwordArgument() {
        String assertMessage = "Wrong parsing";

        Command command = CommandParser.buildCommand("add-task name=\"really long name\"");
        assertEquals("ADD-TASK", command.mainCommand(), assertMessage);
        assertEquals(1, command.arguments().size(), assertMessage);
        assertEquals("name=\"really long name\"", command.arguments().get(0));
    }

    @Test
    void testBuildCommandWithMoreComplexCommand() {
        String assertMessage = "Wrong parsing";

        Command command = CommandParser.buildCommand("     get-task      name=\"loooong name\"     date=18.02.2022      ");
        assertEquals("GET-TASK", command.mainCommand(), assertMessage);
        assertEquals(2, command.arguments().size(), assertMessage);
        assertEquals("name=\"loooong name\"", command.arguments().get(0));
        assertEquals("date=18.02.2022", command.arguments().get(1));
    }
}