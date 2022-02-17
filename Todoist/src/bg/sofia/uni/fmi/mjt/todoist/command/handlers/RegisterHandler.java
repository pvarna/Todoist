package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;

public class RegisterHandler extends CommandHandler {

    public RegisterHandler(Command command) {
        super(command, null);
    }

    @Override
    public String execute() {
        USERS.register(this.command.arguments().get(0), this.command.arguments().get(1));

        return "Registration successful";
    }
}
