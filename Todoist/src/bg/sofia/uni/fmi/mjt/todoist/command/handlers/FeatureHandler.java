package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.user.User;

import java.util.Map;
import java.util.Set;

public abstract class FeatureHandler extends CommandHandler {

    protected static final String SEPARATOR = "-----------------------------------------";

    protected User user;

    public FeatureHandler(Command command, String username) {
        super(command, username);

        if (this.command.arguments().size() != this.doubleArguments.size()) {
            throw new InvalidCommandException("Invalid command syntax " +
                    "(type 'help' to check the correct syntax of the commands)");
        }

        this.user = users.getUser(this.username);
    }


    protected void assertAllMandatoryArgumentsAreAvailable(Map<String, Set<String>> mandatoryArguments) {
        Set<String> mandatory = mandatoryArguments.get(this.command.mainCommand());

        int counter = 0;
        for (String current : mandatory) {
            if (this.doubleArguments.containsKey(current)) {
                ++counter;
            }
        }

        if (counter != mandatory.size()) {
            throw new InvalidCommandException("There are missing mandatory arguments " +
                    "(type 'help' to check the correct syntax of the commands)");
        }
    }
}
