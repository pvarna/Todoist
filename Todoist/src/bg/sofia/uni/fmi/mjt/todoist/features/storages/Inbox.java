package bg.sofia.uni.fmi.mjt.todoist.features.storages;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Inbox {

    private final Map<String, Task> tasks;

    public Inbox() {
        this.tasks = new HashMap<>();
    }

    public void addTask(Task toAdd) {
        Utils.assertNonNull(toAdd, "Task to add");

        if (this.tasks.containsKey(toAdd.getName())) {
            throw new TaskAlreadyExistsException("A task with the same name already exists in the inbox.");
        }

        this.tasks.put(toAdd.getName(), toAdd);
    }

    public Task getTask(String taskName) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonEmpty(taskName, "Task name");

        if (!this.tasks.containsKey(taskName)) {
            throw new NoSuchTaskException("There isn't a task with such name in the inbox.");
        }

        return this.tasks.get(taskName);
    }

    public Task remove(String taskName) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonEmpty(taskName, "Task name");

        Task toRemove = this.getTask(taskName);

        this.tasks.remove(taskName);

        return toRemove;
    }

    public Set<Task> getTasks() {
        if (this.tasks.isEmpty()) {
            throw new NoSuchTaskException("There aren't any tasks in the inbox");
        }

        return Set.copyOf(this.tasks.values());
    }

    public List<String> serialize() {
        List<String> result = new ArrayList<>();

        for (Task task : this.tasks.values()) {
            result.add(task.serialize());
        }

        return result;
    }
}
