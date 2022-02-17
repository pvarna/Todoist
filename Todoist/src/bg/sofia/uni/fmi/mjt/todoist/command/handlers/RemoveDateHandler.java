package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;

public class RemoveDateHandler extends TaskHandler {

    public RemoveDateHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        if (this.collaborationName == null) {
            this.removeDateForPersonalTask();
        } else {
            this.removeDateForCollaborationTask();
        }

        return "Task date removed successfully";
    }

    private void removeDateForPersonalTask() {
        Task toUpdate = this.user.removeFromDatedTasks(this.taskName, this.taskDate);
        toUpdate.setDate(null);
        this.user.addTaskToInbox(toUpdate);
    }

    private void removeDateForCollaborationTask() {
        Task toUpdate = this.user.getCollaboration(this.collaborationName).getTask(this.taskName);
        toUpdate.setDate(null);
    }
}
