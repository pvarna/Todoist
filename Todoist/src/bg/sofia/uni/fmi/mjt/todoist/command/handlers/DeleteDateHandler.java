package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;

public class DeleteDateHandler extends TaskHandler {

    public DeleteDateHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        Task toUpdate = this.user.removeFromDatedTasks(this.taskName, this.taskDate);
        toUpdate.setDate(null);
        this.user.addTaskToInbox(toUpdate);

        return "Task successfully moved to inbox";
    }
}
