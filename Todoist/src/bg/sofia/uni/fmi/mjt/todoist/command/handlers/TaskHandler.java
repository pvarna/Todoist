package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public abstract class TaskHandler extends CommandHandler {

    private static Map<String, Set<String>> mandatoryArguments;

    protected User user;

    protected String taskName;
    protected LocalDate taskDate;
    protected LocalDate taskDueDate;
    protected String taskDescription;
    protected LocalDate taskNewDate;
    protected boolean taskCompleted;

    public TaskHandler(Command command, String username) {
        super(command, username);

        if (this.command.arguments().size() != this.doubleArguments.size()) {
            throw new InvalidCommandException("Invalid command syntax " +
                    "(type 'help' to check the correct syntax of the commands)");
        }

        this.user = users.getUser(this.username);

        this.loadTaskName();
        this.loadTaskDate();
        this.loadTaskDueDate();
        this.loadTaskDescription();
        this.loadTaskNewDate();
        this.loadTaskCompleted();
    }

    private void assertAllMandatoryArgumentsAreAvailable() {
        Set<String> mandatory = mandatoryArguments.get(this.command.mainCommand());

        int counter = 0;
        for (String current : mandatory) {
            if (this.doubleArguments.containsKey(current)) {
                ++counter;
            }
        }

        if (counter != mandatory.size()) {
            throw new InvalidCommandException("There are missing mandatory arguments " +
                    "(type 'help' to check the correct syntax of the commands)");
        }
    }

    private void loadTaskName() {
        if (this.doubleArguments.containsKey("NAME")) {
            this.taskName = this.doubleArguments.get("NAME");
        }
    }

    private void loadTaskDate() {
        if (this.doubleArguments.containsKey("DATE")) {
            try {
                this.taskDate = LocalDate.parse(this.doubleArguments.get("DATE"), FORMATTER);
            } catch (DateTimeParseException e) {
                throw new InvalidCommandException("Wrong date format " +
                        "(type 'help' to check the correct syntax of the commands)");
            }

        }
    }

    private void loadTaskDueDate() {
        if (this.doubleArguments.containsKey("DUE-DATE")) {
            try {
                this.taskDueDate = LocalDate.parse(this.doubleArguments.get("DUE-DATE"), FORMATTER);
            } catch (DateTimeParseException e) {
                throw new InvalidCommandException("Wrong date format " +
                        "(type 'help' to check the correct syntax of the commands)");
            }
        }
    }

    private void loadTaskDescription() {
        if (this.doubleArguments.containsKey("DESCRIPTION")) {
            this.taskDescription = this.doubleArguments.get("DESCRIPTION");
        }
    }

    private void loadTaskNewDate() {
        if (this.doubleArguments.containsKey("NEW-DATE")) {
            try {
                this.taskNewDate = LocalDate.parse(this.doubleArguments.get("NEW-DATE"), FORMATTER);
            } catch (DateTimeParseException e) {
                throw new InvalidCommandException("Wrong date format " +
                        "(type 'help' to check the correct syntax of the commands)");
            }
        }
    }

    private void loadTaskCompleted() {
        if (this.doubleArguments.containsKey("COMPLETED")) {
            String value = this.doubleArguments.get("COMPLETED");

            if (value.equalsIgnoreCase("TRUE")) {
                this.taskCompleted = true;
            } else if (value.equalsIgnoreCase("FALSE")) {
                this.taskCompleted = false;
            } else {
                throw new InvalidCommandException("Invalid command syntax " +
                        "(type 'help' to check the correct syntax of the commands)");
            }
        }
    }

    static {
        mandatoryArguments = Map.ofEntries(
                Map.entry("ADD-TASK", Set.of("NAME")),
                Map.entry("UPDATE-TASK", Set.of("NAME")),
                Map.entry("SET-DATE", Set.of("NAME", "DATE")),
                Map.entry("REMOVE-DATE", Set.of("NAME", "DATE")),
                Map.entry("CHANGE-DATE", Set.of("NAME", "DATE", "NEW-DATE")),
                Map.entry("DELETE-TASK", Set.of("NAME")),
                Map.entry("GET-TASK", Set.of("NAME")),
                Map.entry("LIST-DASHBOARD", Collections.emptySet()),
                Map.entry("FINISH-TASK", Set.of("NAME"))
        );
    }
}
