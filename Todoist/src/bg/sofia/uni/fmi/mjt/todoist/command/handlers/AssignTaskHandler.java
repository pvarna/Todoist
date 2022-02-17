package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongCreatorException;
import bg.sofia.uni.fmi.mjt.todoist.features.collaboration.Collaboration;

public class AssignTaskHandler extends CollaborationHandler {

    public AssignTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        Collaboration toUpdate = this.user.getCollaboration(this.collaboration);

        if (!toUpdate.getAdmin().getUsername().equals(this.username)) {
            throw new WrongCreatorException("Only the creator of the collaboration can assign tasks");
        }

        toUpdate.assignTask(this.taskName, this.newUserName);

        return "Task assigned successfully";
    }
}
