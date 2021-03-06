package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public abstract class TaskHandler extends FeatureHandler {

    protected static final String TASK_SEPARATOR = "-----------------------------------------";

    private static final Map<String, Set<String>> MANDATORY_ARGUMENTS;

    protected String taskName;
    protected LocalDate taskDate;
    protected LocalDate taskDueDate;
    protected String taskDescription;
    protected LocalDate taskNewDate;
    protected Boolean taskCompleted;
    protected String collaborationName;

    public TaskHandler(Command command, String username) {
        super(command, username);

        this.loadTaskName();
        this.loadTaskDate();
        this.loadTaskDueDate();
        this.loadTaskDescription();
        this.loadTaskNewDate();
        this.loadTaskCompleted();
        this.loadCollaborationName();

        this.assertAllMandatoryArgumentsAreAvailable(MANDATORY_ARGUMENTS);
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

    private void loadCollaborationName() {
        if (this.doubleArguments.containsKey("COLLABORATION")) {
            this.collaborationName = this.doubleArguments.get("COLLABORATION");
        }
    }

    static {
        MANDATORY_ARGUMENTS = Map.ofEntries(
                Map.entry("ADD-TASK", Set.of("NAME")),
                Map.entry("UPDATE-TASK", Set.of("NAME")),
                Map.entry("SET-DATE", Set.of("NAME", "DATE")),
                Map.entry("REMOVE-DATE", Set.of("NAME", "DATE")),
                Map.entry("CHANGE-DATE", Set.of("NAME", "DATE", "NEW-DATE")),
                Map.entry("DELETE-TASK", Set.of("NAME")),
                Map.entry("GET-TASK", Set.of("NAME")),
                Map.entry("LIST-DASHBOARD", Collections.emptySet()),
                Map.entry("FINISH-TASK", Set.of("NAME")),
                Map.entry("LIST-TASKS", Collections.emptySet())
        );
    }
}
