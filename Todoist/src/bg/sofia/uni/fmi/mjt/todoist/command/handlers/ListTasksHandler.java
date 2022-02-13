package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;

import java.util.Set;
import java.util.stream.Collectors;

public class ListTasksHandler extends TaskHandler {

    public ListTasksHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        Set<Task> tasks = this.taskDate == null ? this.user.getTasksFromInbox() :
                                                  this.user.getTasksForGivenDate(this.taskDate);

        if (this.taskCompleted) {
            tasks = tasks.stream().filter(Task::isCompleted).collect(Collectors.toSet());
        }

        StringBuilder sb = new StringBuilder();
        for (Task current : tasks) {
            sb.append(current).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
