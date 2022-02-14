package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;

import java.time.LocalDate;
import java.util.Set;

public class ListDashboardHandler extends TaskHandler {

    public ListDashboardHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        Set<Task> tasksForTheDay;

        try {
            tasksForTheDay = this.user.getTasksForGivenDate(LocalDate.now());
        } catch (NoSuchTaskException e) {
            throw new NoSuchTaskException("There aren't any tasks for today");
        }

        StringBuilder sb = new StringBuilder();
        for (Task current : tasksForTheDay) {
            sb.append(current).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
