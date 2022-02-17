package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.server.features.collaboration.Collaboration;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;

import java.util.Set;
import java.util.stream.Collectors;

public class ListCollaborationTasksHandler extends CollaborationHandler {

    public ListCollaborationTasksHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        Collaboration collaboration = this.user.getCollaboration(this.name);

        Set<Task> tasks = collaboration.getTasks();

        return tasks.stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator() + SEPARATOR + System.lineSeparator()));
    }
}
