package bg.sofia.uni.fmi.mjt.todoist.server.features.collaboration;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchUserException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.CollaborationTask;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (this.collaborators.containsKey(user.getUsername()) || this.admin.getUsername().equals(user.getUsername())) {
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

        return this.tasks.values().stream()
                .map(CollaborationTask::toString)
                .collect(Collectors.joining(System.lineSeparator() + TASK_SEPARATOR + System.lineSeparator()));
    }

    public String getUsersInfo() {

        String result = "Admin: " + this.admin.getUsername() + System.lineSeparator();

        if (!this.collaborators.isEmpty()) {
            result += "Collaborators: " + String.join(", ", this.collaborators.keySet());
        }

        return result;
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
            sb.append(System.lineSeparator()).append("Collaborators: ").append(String.join(", ", this.collaborators.keySet()));
        }

        if (!this.tasks.isEmpty()) {
            sb.append(System.lineSeparator()).append("Tasks: ").append(System.lineSeparator());

            sb.append(this.tasks.values().stream()
                    .map(Task::toString)
                    .collect(Collectors.joining(System.lineSeparator() + TASK_SEPARATOR + System.lineSeparator())));
        }

        return sb.toString();
    }
}
