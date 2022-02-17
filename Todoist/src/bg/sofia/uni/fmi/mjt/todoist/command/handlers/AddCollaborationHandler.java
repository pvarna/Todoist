package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.features.collaboration.Collaboration;

public class AddCollaborationHandler extends CollaborationHandler {

    public AddCollaborationHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        Collaboration toAdd = new Collaboration(this.name, this.user);

        this.user.addCollaboration(toAdd);

        return "Collaboration added successfully";
    }
}
