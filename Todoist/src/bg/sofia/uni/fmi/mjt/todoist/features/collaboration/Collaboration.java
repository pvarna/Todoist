package bg.sofia.uni.fmi.mjt.todoist.features.collaboration;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchUserException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.features.task.CollaborationTask;
import bg.sofia.uni.fmi.mjt.todoist.user.User;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Collaboration {

    private static final String TASK_SEPARATOR = "-----------------------------------------";

    private final String name;
    private final User admin;
    private final Map<String, User> collaborators;
    private final Map<String, CollaborationTask> tasks;

    public Collaboration(String name, User admin) {
        Utils.assertNonNull(name, "Collaboration name");
        Utils.assertNonEmpty(name, "Collaboration name");
        Utils.assertNonNull(admin, "Admin");

        this.name = name;
        this.admin = admin;
        this.collaborators = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    public void addNewTask(CollaborationTask task) {
        Utils.assertNonNull(task, "Task");

        if (this.tasks.containsKey(task.getName())) {
            throw new TaskAlreadyExistsException("The collaboration already has a task with such name");
        }

        this.tasks.put(task.getName(), task);
    }

    public void addNewCollaborator(User user) {
        Utils.assertNonNull(user, "User");

        if (this.collaborators.containsKey(user.getUsername()) || this.admin.getUsername().equals(user.getUsername())) {
            throw new UserAlreadyExistsException("The collaboration already has a user with such username");
        }

        this.collaborators.put(user.getUsername(), user);
    }

    public void assignTask(String taskName, String username) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonEmpty(taskName, "Task name");
        Utils.assertNonNull(username, "Username");
        Utils.assertNonEmpty(username, "Username");

        if (!this.collaborators.containsKey(username) && !this.admin.getUsername().equals(username)) {
            throw new NoSuchUserException("The collaboration doesn't have a collaborator with such username");
        }

        Task task = this.getTask(taskName);
        ((CollaborationTask) task).setAssignee(username);
    }

    public Task getTask(String taskName) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonEmpty(taskName, "Task name");

        if (!this.tasks.containsKey(taskName)) {
            throw new NoSuchTaskException("The collaboration doesn't have a task with such name");
        }

        return this.tasks.get(taskName);
    }

    public void removeTask(String taskName) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonEmpty(taskName, "Task name");

        if (!this.tasks.containsKey(taskName)) {
            throw new NoSuchTaskException("The collaboration doesn't have a task with such name");
        }

        this.tasks.remove(taskName);
    }

    public Set<Task> getTasks() {
        if (this.tasks.isEmpty()) {
            throw new NoSuchTaskException("There aren't any tasks in the collaboration");
        }

        return Set.copyOf(this.tasks.values());
    }

    public String getName() {
        return this.name;
    }

    public User getAdmin() {
        return this.admin;
    }

    public Set<User> getCollaborators() {
        return Set.copyOf(this.collaborators.values());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ").append(this.name).append(System.lineSeparator());
        sb.append("Creator: ").append(this.admin.getUsername());
        if (!this.collaborators.isEmpty()) {
            sb.append(System.lineSeparator()).append("Collaborators: ");
            sb.append(String.join(", ", this.collaborators.keySet()));
        }

        if (!this.tasks.isEmpty()) {
            sb.append(System.lineSeparator()).append("Tasks: ").append(System.lineSeparator());

            sb.append(this.tasks.values().stream()
                    .map(Task::toString)
                    .collect(Collectors.joining(System.lineSeparator() +
                            TASK_SEPARATOR + System.lineSeparator())));
        }

        return sb.toString();
    }

    public List<String> serialize() {
        List<String> result = new ArrayList<>();

        result.add("add-collaboration name=\"" + this.name + "\"");

        for (User user : this.collaborators.values()) {
            result.add("add-user collaboration=\"" + this.name + "\" user=" + user.getUsername());
        }

        for (CollaborationTask task : this.tasks.values()) {
            result.add(String.format(task.serialize(), this.name, this.name, task.getAssignee()));
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collaboration that = (Collaboration) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
