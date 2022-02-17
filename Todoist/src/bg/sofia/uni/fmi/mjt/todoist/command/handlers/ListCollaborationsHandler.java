package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchCollaborationException;
import bg.sofia.uni.fmi.mjt.todoist.features.collaboration.Collaboration;

import java.util.Set;
import java.util.stream.Collectors;

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

        return collaborations.stream()
                .map(Collaboration::toString)
                .collect(Collectors.joining(System.lineSeparator() + COLLABORATION_SEPARATOR + System.lineSeparator()));
    }
}
