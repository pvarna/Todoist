package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

public class SetDateHandler extends TaskHandler {

    public SetDateHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        Task toUpdate = this.user.removeFromInbox(this.taskName);
        toUpdate.setDate(this.taskDate);
        this.user.addTaskToDatedTasks(toUpdate);

        return "Task successfully moved to dated tasks";
    }
}
