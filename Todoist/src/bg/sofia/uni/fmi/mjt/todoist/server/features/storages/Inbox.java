package bg.sofia.uni.fmi.mjt.todoist.server.features.storages;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyCompletedException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Inbox {

    private final Map<String, Task> tasks;

    public Inbox() {
        this.tasks = new HashMap<>();
    }
    public void addTask(Task toAdd) {
        if (this.tasks.containsKey(toAdd.getName())) {
            throw new TaskAlreadyExistsException("A task with the same name already exists in the inbox.");
        }

        this.tasks.put(toAdd.getName(), toAdd);
    }

    public Task getTask(String taskName) {
        if (!this.tasks.containsKey(taskName)) {
            throw new NoSuchTaskException("There isn't a task with such name in the inbox.");
        }

        return this.tasks.get(taskName);
    }

    public Task remove(String taskName) {
        Task toRemove = this.getTask(taskName);

        this.tasks.remove(taskName);

        return toRemove;
    }

    public void finishTask(String taskName) {
        Task toFinish = this.getTask(taskName);

        if (toFinish.isCompleted()) {
            throw new TaskAlreadyCompletedException("This task is already completed");
        }

        toFinish.finish();
    }

    public Set<Task> getTasks() {
        return Set.copyOf(this.tasks.values());
    }
}
