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

            case "GET-TASK" -> {
                return new GetTaskHandler(command, username);
            }

            case "LIST-DASHBOARD" -> {
                return new ListDashboardHandler(command, username);
            }

            case "FINISH-TASK" -> {
                return new FinishTaskHandler(command, username);
            }

            case "LIST-TASKS" -> {
                return new ListTasksHandler(command, username);
            }

            case "DELETE-TASK" -> {
                return new DeleteTaskHandler(command, username);
            }

            case "ADD-COLLABORATION" -> {
                return new AddCollaborationHandler(command, username);
            }

            case "DELETE-COLLABORATION" -> {
                return new DeleteCollaborationHandler(command, username);
            }

            case "LIST-COLLABORATIONS" -> {
                return new ListCollaborationsHandler(command, username);
            }

            case "ADD-USER" -> {
                return new AddUserHandler(command, username);
            }

            case "ASSIGN-TASK" -> {
                return new AssignTaskHandler(command, username);
            }

            case "LIST-COLLABORATION-TASKS" -> {
                return new ListCollaborationTasksHandler(command, username);
            }

            case "LIST-COLLABORATION-USERS" -> {
                return new ListCollaborationUsersHandler(command, username);
            }
        }

        return null;
    }
}
