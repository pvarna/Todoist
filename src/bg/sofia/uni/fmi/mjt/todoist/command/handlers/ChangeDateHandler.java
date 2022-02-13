package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

public class ChangeDateHandler extends TaskHandler {

    public ChangeDateHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        Task toUpdate = this.user.removeFromDatedTasks(this.taskName, this.taskDate);
        toUpdate.setDate(this.taskNewDate);
        this.user.addTask(toUpdate);

        return "Task date successfully updated";
    }
}
