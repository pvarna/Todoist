package bg.sofia.uni.fmi.mjt.todoist.server.user;

import bg.sofia.uni.fmi.mjt.todoist.server.features.storages.DatedTasks;
import bg.sofia.uni.fmi.mjt.todoist.server.features.storages.Inbox;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.time.LocalDate;

public class User {
    private final String username;
    private final String password;

    private final Inbox inbox;
    private final DatedTasks datedTasks;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

        this.inbox = new Inbox();
        this.datedTasks = new DatedTasks();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void addTask(Task toAdd) {
        if (toAdd.getDate() == null) {
            this.inbox.addTask(toAdd);
        } else {
            this.datedTasks.addTask(toAdd);
        }
    }

    public Task getTask(String taskName, LocalDate taskDate) {
        Utils.assertNonNull(taskName, "Task name");

        return taskDate == null ? this.inbox.getTask(taskName) :
                                  this.datedTasks.getTask(taskName, taskDate);
    }

    public Task removeFromInbox(String taskName) {
        Utils.assertNonNull(taskName, "Task name");

        return this.inbox.remove(taskName);
    }

    public Task removeFromDatedTasks(String taskName, LocalDate taskDate) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonNull(taskName, "Task date");

        return this.datedTasks.remove(taskName, taskDate);
    }
}
