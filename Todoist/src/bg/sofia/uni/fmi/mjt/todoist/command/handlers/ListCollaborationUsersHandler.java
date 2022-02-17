package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.features.collaboration.Collaboration;
import bg.sofia.uni.fmi.mjt.todoist.user.User;

import java.util.Set;
import java.util.stream.Collectors;

public class ListCollaborationUsersHandler extends CollaborationHandler {

    public ListCollaborationUsersHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        Collaboration collaboration = this.user.getCollaboration(this.name);

        Set<User> collaborators = collaboration.getCollaborators();

        StringBuilder result = new StringBuilder("Admin: " + collaboration.getAdmin().getUsername());
        if (!collaborators.isEmpty()) {
            result.append("Collaborators: ");
            result.append(collaborators.stream().map(User::getUsername).collect(Collectors.joining(", ")));
        }

        return result.toString();
    }
}
