package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.server.features.collaboration.Collaboration;

public class ListCollaborationUsersHandler extends CollaborationHandler {

    public ListCollaborationUsersHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        Collaboration collaboration = this.user.getCollaboration(this.name);

        return collaboration.getUsersInfo();
    }
}
