package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
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

        if (this.taskCompleted != null) {
            tasks = tasks.stream().
                    filter(task -> task.isCompleted() == this.taskCompleted).
                    collect(Collectors.toSet());
        }

        this.assertNotEmpty(tasks);

        StringBuilder sb = new StringBuilder();
        for (Task current : tasks) {
            sb.append(current).append(System.lineSeparator());
        }

        return sb.toString();
    }

    private void assertNotEmpty(Set<Task> tasks) {
        if (tasks.isEmpty()) {
            if (this.taskCompleted) {
                throw new NoSuchTaskException("There aren't any completed tasks");
            } else {
                throw new NoSuchTaskException("There aren't any uncompleted tasks");
            }
        }
    }
}
