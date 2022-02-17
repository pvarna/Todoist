package bg.sofia.uni.fmi.mjt.todoist.user;

import bg.sofia.uni.fmi.mjt.todoist.features.collaboration.Collaboration;
import bg.sofia.uni.fmi.mjt.todoist.features.storages.Collaborations;
import bg.sofia.uni.fmi.mjt.todoist.features.storages.DatedTasks;
import bg.sofia.uni.fmi.mjt.todoist.features.storages.Inbox;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class User {
    private final String username;
    private final String password;

    private final Inbox inbox;
    private final DatedTasks datedTasks;
    private final Collaborations collaborations;

    public User(String username, String password) {
        Utils.assertNonNull(username, "Username");
        Utils.assertNonEmpty(username, "Username");
        Utils.assertNonNull(password, "Password");
        Utils.assertNonEmpty(password, "Password");

        this.username = username;
        this.password = password;

        this.inbox = new Inbox();
        this.datedTasks = new DatedTasks();
        this.collaborations = new Collaborations(this.username);
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
        return this.inbox.getTask(taskName);
    }

    public Task getFromDatedTasks(String taskName, LocalDate taskDate) {
        return this.datedTasks.getTask(taskName, taskDate);
    }

    public Task removeFromInbox(String taskName) {
        return this.inbox.remove(taskName);
    }

    public Task removeFromDatedTasks(String taskName, LocalDate taskDate) {
        return this.datedTasks.remove(taskName, taskDate);
    }

    public Set<Task> getTasksForGivenDate(LocalDate taskDate) {
        return this.datedTasks.getTasksForGivenDate(taskDate);
    }

    public Set<Task> getTasksFromInbox() {
        return this.inbox.getTasks();
    }

    public void addCollaboration(Collaboration toAdd) {
        this.collaborations.addCollaboration(toAdd);
    }

    public void removeCollaboration(String collaborationName) {
        this.collaborations.removeCollaboration(collaborationName);
    }

    public void deleteCollaboration(String collaborationName) {
        this.collaborations.deleteCollaboration(collaborationName);
    }

    public Collaboration getCollaboration(String collaborationName) {
        return this.collaborations.getCollaboration(collaborationName);
    }

    public Set<Collaboration> getCollaborations() {
        return this.collaborations.getCollaborations();
    }

    public String serialize() {
        List<String> commands = new ArrayList<>();
        commands.add("register " + this.username + " " + this.password);

        commands.addAll(this.inbox.serialize());
        commands.addAll(this.datedTasks.serialize());
        commands.addAll(this.collaborations.serialize());

        return commands.stream().collect(Collectors.joining(System.lineSeparator()));
    }

    @Override
    public String toString() {
        return "Username: " + this.username;
    }
}
