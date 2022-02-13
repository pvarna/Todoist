package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;

public class GetTaskHandler extends TaskHandler {

    public GetTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        return this.taskDate == null ? this.user.getFromInbox(this.taskName).toString() :
                                       this.user.getFromDatedTasks(this.taskName, this.taskDate).toString();
    }
}
