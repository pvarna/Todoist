package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

public class AddTaskHandler extends TaskHandler {

    public AddTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        Task toAdd = new Task(this.taskName, this.taskDate, this.taskDueDate, this.taskDescription);

        if (this.taskDate == null) {
            this.user.addTaskToInbox(toAdd);
        } else {
            this.user.addTaskToDatedTasks(toAdd);
        }
        return "Task successfully added";
    }
}
