package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.InvalidCommandException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.collaboration.Collaboration;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.CollaborationTask;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

public class AddTaskHandler extends TaskHandler {

    public AddTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        if (this.collaborationName == null) {
            this.addPersonalTask();
        } else {
            this.addTaskToCollaboration();
        }

        return "Task successfully added";
    }

    private void addPersonalTask() {
        Task toAdd = new Task(this.taskName, this.taskDate, this.taskDueDate, this.taskDescription);

        if (this.taskDate == null) {
            this.user.addTaskToInbox(toAdd);
        } else {
            this.user.addTaskToDatedTasks(toAdd);
        }
    }

    private void addTaskToCollaboration() {
        CollaborationTask toAdd = new CollaborationTask(this.taskName, this.taskDate, this.taskDueDate, this.taskDescription);

        Collaboration collaboration = this.user.getCollaboration(this.collaborationName);
        collaboration.addNewTask(toAdd);
    }
}
