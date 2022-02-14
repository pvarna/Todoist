package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;

public class FinishTaskHandler extends TaskHandler {

    public FinishTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        if (this.collaborationName == null) {
            this.finishPersonalTask();
        } else {
            this.finishCollaborationTask();
        }

        return "Task marked as completed successfully";
    }

    private void finishPersonalTask() {
        if (this.taskDate == null) {
            this.user.getFromInbox(this.taskName).finish();
        } else {
            this.user.getFromDatedTasks(this.taskName, this.taskDate).finish();
        }
    }

    private void finishCollaborationTask() {
        this.user.getCollaboration(this.collaborationName).getTask(this.taskName).finish();
    }
}
