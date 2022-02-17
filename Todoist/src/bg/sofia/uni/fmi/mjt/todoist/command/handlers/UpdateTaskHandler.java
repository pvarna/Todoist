package bg.sofia.uni.fmi.mjt.todoist.command.handlers;

import bg.sofia.uni.fmi.mjt.todoist.command.Command;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;

import java.time.LocalDate;

public class UpdateTaskHandler extends TaskHandler {

    public UpdateTaskHandler(Command command, String username) {
        super(command, username);
    }

    @Override
    public String execute() {
        Task toUpdate;

        if (this.collaborationName == null) {
            toUpdate = this.taskDate == null ? this.user.getFromInbox(this.taskName) :
                                               this.user.getFromDatedTasks(this.taskName, this.taskDate);
        } else {
            toUpdate = this.user.getCollaboration(this.collaborationName).getTask(this.taskName);
        }

        this.updateTask(toUpdate);

        return "Task updated successfully";
    }

    private void updateTask(Task toUpdate) {
        if (this.doubleArguments.containsKey("DUE-DATE")) {
            toUpdate.setDueDate(LocalDate.parse(this.doubleArguments.get("DUE-DATE"), FORMATTER));
        }

        if (this.doubleArguments.containsKey("DESCRIPTION")) {
            toUpdate.setDescription(this.doubleArguments.get("DESCRIPTION"));
        }
    }
}
