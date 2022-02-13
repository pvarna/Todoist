package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

import java.time.LocalDate;

public class UpdateTaskHandler extends TaskHandler {

    public UpdateTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        this.updateTask(this.user.getTask(this.taskName, this.taskDate));

        return "Task is updated successfully";
    }

    private void updateTask(Task toUpdate) {
        if (this.doubleArguments.containsKey("DUE-DATE")) {
            toUpdate.setDueDate(LocalDate.parse(this.doubleArguments.get("DUE-DATE"), FORMATTER));
        }

        if (this.doubleArguments.containsKey("DESCRIPTION")) {
            toUpdate.setDescription(this.doubleArguments.get("DESCRIPTION"));
        }
    }
}
