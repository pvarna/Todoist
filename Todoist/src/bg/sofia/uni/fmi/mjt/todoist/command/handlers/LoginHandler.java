package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;

public class LoginHandler extends CommandHandler {

    public LoginHandler(Command command) {
        super(command, null);
    }

    @Override
    public String execute() {
        users.login(this.command.arguments().get(0), this.command.arguments().get(1));

        return "Login successful";
    }
}
