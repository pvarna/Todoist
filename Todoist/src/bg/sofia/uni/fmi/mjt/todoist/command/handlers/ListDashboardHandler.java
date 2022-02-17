package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

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

        return tasksForTheDay.stream()
                .map(Task::toString)
                .collect(Collectors.joining(System.lineSeparator() + TASK_SEPARATOR + System.lineSeparator()));
    }
}
