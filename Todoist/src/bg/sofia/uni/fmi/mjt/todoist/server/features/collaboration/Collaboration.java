package bg.sofia.uni.fmi.mjt.todoist.server.features.collaboration;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchUserException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.CollaborationTask;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Collaboration {

    private static final String TASK_SEPARATOR = "-----------------------------------------";

    private String name;
    private User admin;
    private Map<String, User> collaborators;
    private Map<String, CollaborationTask> tasks;

    public Collaboration(String name, User admin) {
        this.name = name;
        this.admin = admin;
        this.collaborators = new HashMap<>();
        this.tasks = new HashMap<>();
    }

    public void addNewTask(CollaborationTask task) {
        if (this.tasks.containsKey(task.getName())) {
            throw new TaskAlreadyExistsException("The collaboration already has a task with such name");
        }

        this.tasks.put(task.getName(), task);
    }

    public void addNewCollaborator(User user) {
        if (this.collaborators.containsKey(user.getUsername())) {
            throw new UserAlreadyExistsException("The collaboration already has a user with such username");
        }

        this.collaborators.put(user.getUsername(), user);
    }

    public void addAssigneeForGivenTask(String taskName, String username) {
        if (!this.collaborators.containsKey(username) && !this.admin.getUsername().equals(username)) {
            throw new NoSuchUserException("The collaboration doesn't have a collaborator with such username");
        }

        Task task = this.getTask(taskName);
        ((CollaborationTask) task).setAssignee(username);
    }

    public Task getTask(String taskName) {
        if (!this.tasks.containsKey(taskName)) {
            throw new NoSuchTaskException("The collaboration doesn't have a task with such name");
        }

        return this.tasks.get(taskName);
    }

    public void removeTask(String taskName) {
        if (!this.tasks.containsKey(taskName)) {
            throw new NoSuchTaskException("The collaboration doesn't have a task with such name");
        }

        this.tasks.remove(taskName);
    }

    public String getTasksInfo() {
        if (this.tasks.isEmpty()) {
            throw new NoSuchTaskException("There aren't any tasks in the collaboration");
        }

        StringBuilder sb = new StringBuilder();

        for (CollaborationTask current : tasks.values()) {
            sb.append(current.toString());
            sb.append(System.lineSeparator()).append(TASK_SEPARATOR).append(System.lineSeparator());
        }

        return sb.toString();
    }

    public String getUsersInfo() {
        StringBuilder sb = new StringBuilder("Admin: " + this.admin.toString());

        sb.append(System.lineSeparator()).append(TASK_SEPARATOR).append(System.lineSeparator());

        for (String current : collaborators.keySet()) {
            sb.append(current);
            sb.append(System.lineSeparator()).append(TASK_SEPARATOR).append(System.lineSeparator());
        }

        return sb.toString();
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
        sb.append("Creator: ").append(this.admin.getUsername()).append(System.lineSeparator());
        sb.append("Tasks: ").append(System.lineSeparator());

        for (Task current : this.tasks.values()) {
            sb.append(current.toString()).append(System.lineSeparator());
        }

        return sb.toString();
    }
}
