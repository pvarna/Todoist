package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchCollaborationException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.collaboration.Collaboration;

import java.util.Set;

public class ListCollaborationsHandler extends CollaborationHandler {

    public ListCollaborationsHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        StringBuilder sb = new StringBuilder();

        Set<Collaboration> collaborations = this.user.getCollaborations();

        if (collaborations.isEmpty()) {
            throw new NoSuchCollaborationException("There aren't any collaborations");
        }

        for (Collaboration current : collaborations) {
            sb.append(current.toString());
            sb.append(System.lineSeparator()).append(COLLABORATION_SEPARATOR).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
