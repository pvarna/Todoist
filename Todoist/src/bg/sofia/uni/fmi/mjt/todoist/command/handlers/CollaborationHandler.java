package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public abstract class CollaborationHandler extends FeatureHandler {

    protected static final String COLLABORATION_SEPARATOR = ".........................................";

    private static final Map<String, Set<String>> MANDATORY_ARGUMENTS;

    protected String name;
    protected String collaboration;
    protected String newUserName;
    protected String taskName;


    public CollaborationHandler(Command command, String username) {
        super(command, username);

        this.loadName();
        this.loadCollaboration();
        this.loadNewUserName();
        this.loadTaskName();

        this.assertAllMandatoryArgumentsAreAvailable(MANDATORY_ARGUMENTS);
    }

    private void loadName() {
        if (this.doubleArguments.containsKey("NAME")) {
            this.name = this.doubleArguments.get("NAME");
        }
    }

    private void loadCollaboration() {
        if (this.doubleArguments.containsKey("COLLABORATION")) {
            this.collaboration = this.doubleArguments.get("COLLABORATION");
        }
    }

    private void loadNewUserName() {
        if (this.doubleArguments.containsKey("USER")) {
            this.newUserName = this.doubleArguments.get("USER");
        }
    }

    private void loadTaskName() {
        if (this.doubleArguments.containsKey("TASK")) {
            this.taskName = this.doubleArguments.get("TASK");
        }
    }

    static {
        MANDATORY_ARGUMENTS = Map.ofEntries(
                Map.entry("ADD-COLLABORATION", Set.of("NAME")),
                Map.entry("DELETE-COLLABORATION", Set.of("NAME")),
                Map.entry("LIST-COLLABORATIONS", Collections.emptySet()),
                Map.entry("ADD-USER", Set.of("COLLABORATION", "USER")),
                Map.entry("ASSIGN-TASK", Set.of("COLLABORATION", "USER", "TASK")),
                Map.entry("LIST-COLLABORATION-TASKS", Set.of("NAME")),
                Map.entry("LIST-COLLABORATION-USERS", Set.of("NAME"))
        );
    }
}
