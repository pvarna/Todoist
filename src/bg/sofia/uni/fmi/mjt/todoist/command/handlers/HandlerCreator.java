package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.command.CommandHandler;

public class HandlerCreator {

    public static CommandHandler of(Command command, String username) {
        switch (command.mainCommand()) {
            case "REGISTER" -> {
                return new RegisterHandler(command);
            }
            case "LOGIN" -> {
                return new LoginHandler(command);
            }

            case "ADD-TASK" -> {
                return new AddTaskHandler(command, username);
            }

            case "UPDATE-TASK" -> {
                return new UpdateTaskHandler(command, username);
            }

            case "SET-DATE" -> {
                return new SetDateHandler(command, username);
            }

            case "REMOVE-DATE" -> {
                return new RemoveDateHandler(command, username);
            }

            case "CHANGE-DATE" -> {
                return new ChangeDateHandler(command, username);
            }
        }

        return null;
    }
}
