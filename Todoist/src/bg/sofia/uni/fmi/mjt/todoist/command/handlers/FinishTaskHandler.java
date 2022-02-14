package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;

public class FinishTaskHandler extends TaskHandler {

    public FinishTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        if (this.taskDate == null) {
            this.user.finishTaskFromInbox(this.taskName);
        } else {
            this.user.finishTaskFromDatedTasks(this.taskName, this.taskDate);
        }

        return "Task marked as completed successfully";
    }
}
