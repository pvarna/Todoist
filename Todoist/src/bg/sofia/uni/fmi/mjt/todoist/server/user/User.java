package bg.sofia.uni.fmi.mjt.todoist.server.user;

import bg.sofia.uni.fmi.mjt.todoist.server.features.storages.DatedTasks;
import bg.sofia.uni.fmi.mjt.todoist.server.features.storages.Inbox;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.time.LocalDate;
import java.util.Set;

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

    public void addTaskToInbox(Task toAdd) {
        this.inbox.addTask(toAdd);
    }

    public void addTaskToDatedTasks(Task toAdd) {
        this.datedTasks.addTask(toAdd);
    }

    public Task getFromInbox(String taskName) {
        Utils.assertNonNull(taskName, "Task name");

        return this.inbox.getTask(taskName);
    }

    public Task getFromDatedTasks(String taskName, LocalDate taskDate) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonNull(taskDate, "Task date");

        return this.datedTasks.getTask(taskName, taskDate);
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

    public Set<Task> getTasksForGivenDate(LocalDate date) {
        return this.datedTasks.getTasksForGivenDate(date);
    }

    public Set<Task> getTasksFromInbox() {
        return this.inbox.getTasks();
    }

    public void finishTaskFromInbox(String taskName) {
        this.inbox.finishTask(taskName);
    }

    public void finishTaskFromDatedTasks(String taskName, LocalDate taskDate) {
        this.datedTasks.finishTask(taskName, taskDate);
    }
}
