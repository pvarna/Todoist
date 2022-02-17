package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;

public class SetDateHandler extends TaskHandler {

    public SetDateHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        if (this.collaborationName == null) {
            this.setDateForPersonalTask();
        } else {
            this.setDateForCollaborationTask();
        }

        return "Task date set successfully";
    }

    private void setDateForPersonalTask() {
        Task toUpdate = this.user.removeFromInbox(this.taskName);
        toUpdate.setDate(this.taskDate);
        this.user.addTaskToDatedTasks(toUpdate);
    }

    private void setDateForCollaborationTask() {
        Task toUpdate = this.user.getCollaboration(this.collaborationName).getTask(this.taskName);
        toUpdate.setDate(this.taskDate);
    }
}
