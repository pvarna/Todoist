package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;

public class ChangeDateHandler extends TaskHandler {

    public ChangeDateHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        if (this.collaborationName == null) {
            this.changeDateForPersonalTask();
        } else {
            this.changeDateForCollaborationTask();
        }

        return "Task date changed successfully";
    }

    private void changeDateForPersonalTask() {
        Task toUpdate = this.user.removeFromDatedTasks(this.taskName, this.taskDate);
        toUpdate.setDate(this.taskNewDate);
        this.user.addTaskToDatedTasks(toUpdate);
    }

    private void changeDateForCollaborationTask() {
        Task toUpdate = this.user.getCollaboration(this.collaborationName).getTask(this.taskName);
        toUpdate.setDate(this.taskNewDate);
    }
}
