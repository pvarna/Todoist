package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongCreatorException;
import bg.sofia.uni.fmi.mjt.todoist.features.collaboration.Collaboration;
import bg.sofia.uni.fmi.mjt.todoist.user.User;

public class AddUserHandler extends CollaborationHandler {

    public AddUserHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {

        Collaboration toUpdate = this.user.getCollaboration(this.collaboration);

        if (!toUpdate.getAdmin().getUsername().equals(this.username)) {
            throw new WrongCreatorException("Only the creator of the collaboration can add new users");
        }

        User toAdd = USERS.getUser(this.newUserName);
        toUpdate.addNewCollaborator(toAdd);

        toAdd.addCollaboration(toUpdate);

        return "New user successfully added";
    }
}
