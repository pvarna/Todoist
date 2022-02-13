package bg.sofia.uni.fmi.mjt.todoist.command;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongAuthenticationException;
import bg.sofia.uni.fmi.mjt.todoist.server.user.UserDatabase;
import utils.Utils;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public abstract class CommandHandler {

    private static final int PARTS_OF_DOUBLE_ARGUMENT = 2;
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final String DOUBLE_ARGUMENT_SEPARATOR = "=";
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final Map<String, NumberOfArguments> availableCommands;
    protected static final UserDatabase users = new UserDatabase();

    protected Command command;
    protected String username;
    protected Map<String, String> doubleArguments;

    public CommandHandler(Command command, String username) {
        this.command = command;
        this.username = username;
        this.doubleArguments = new HashMap<>();

        this.parseDoubleArguments();
    }

    public abstract String execute();

    public static void assertCommandIsValid(Command command, String username) {
        Utils.assertNonNull(command, "Command");

        String mainCommand = command.mainCommand();

        if (!availableCommands.containsKey(mainCommand)) {
            throw new InvalidCommandException("There isn't such command (type 'help' to see all valid commands)");
        }

        int numberOfArguments = command.arguments().size();
        if (numberOfArguments < availableCommands.get(mainCommand).minArguments() ||
                numberOfArguments > availableCommands.get(mainCommand).maxArguments()) {
            throw new InvalidCommandException("Invalid number of arguments " +
                    "(type 'help' to check the correct syntax of the commands)");
        }

        if (!mainCommand.equals("LOGIN") && !mainCommand.equals("REGISTER") &&
             username == null) {
            throw new WrongAuthenticationException("You must be logged in to use this feature");
        }
    }

    private void parseDoubleArguments() {
        for (String argument : this.command.arguments()) {
            if (argument.contains(DOUBLE_ARGUMENT_SEPARATOR)) {
                String[] separateParts = argument.split(DOUBLE_ARGUMENT_SEPARATOR);

                if (separateParts.length != PARTS_OF_DOUBLE_ARGUMENT) {
                    throw new InvalidCommandException("Invalid command syntax " +
                            "(type 'help' to check the correct syntax of the commands)");
                }

                this.doubleArguments.put(separateParts[FIRST].toUpperCase(),
                                         separateParts[SECOND]);
            }
        }
    }

    static {
        availableCommands = Map.ofEntries(
                Map.entry("LOGIN", new NumberOfArguments(2,2)),
                Map.entry("REGISTER", new NumberOfArguments(2,2)),
                Map.entry("ADD-TASK", new NumberOfArguments(1,4)),
                Map.entry("UPDATE-TASK", new NumberOfArguments(2,4)),
                Map.entry("SET-DATE", new NumberOfArguments(2,2)),
                Map.entry("REMOVE-DATE", new NumberOfArguments(2,2)),
                Map.entry("CHANGE-DATE", new NumberOfArguments(3,3)),
                Map.entry("DELETE-TASK", new NumberOfArguments(1,2))
                );
    }
}

record NumberOfArguments(int minArguments, int maxArguments) {}