package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;

public class DeleteCollaborationHandler extends CollaborationHandler {

    public DeleteCollaborationHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        this.user.deleteCollaboration(this.name);

        return "Collaboration deleted successfully";
    }
}
