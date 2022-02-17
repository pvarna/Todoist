package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;

public class DeleteTaskHandler extends TaskHandler {

    public DeleteTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        if (this.collaborationName == null) {
            this.deletePersonalTask();
        } else {
            this.deleteCollaborationTask();
        }

        return "Task deleted successfully";
    }

    private void deletePersonalTask() {
        if (this.taskDate == null) {
            this.user.removeFromInbox(this.taskName);
        } else {
            this.user.removeFromDatedTasks(this.taskName, this.taskDate);
        }
    }

    private void deleteCollaborationTask() {
        this.user.getCollaboration(this.collaborationName).removeTask(this.taskName);
    }
}
