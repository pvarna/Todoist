package bg.sofia.uni.fmi.mjt.todoist.server.features.task;

import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

import java.time.LocalDate;

public class CollaborationTask extends Task {

    private String assignee;

    public CollaborationTask(String name, LocalDate date, LocalDate dueDate, String description) {
        super(name, date, dueDate, description);
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());

        if (assignee != null) {
            result.append(System.lineSeparator()).append("Assignee: ").append(this.assignee);
        }

        return result.toString();
    }
}
