package bg.sofia.uni.fmi.mjt.todoist.features.storages;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DatedTasks {

    private final Map<LocalDate, Map<String, Task>> datedTasks;

    public DatedTasks() {
        this.datedTasks = new HashMap<>();
    }

    public void addTask(Task toAdd) {
        Utils.assertNonNull(toAdd, "Task to add");

        if (this.datedTasks.containsKey(toAdd.getDate())) {

            if (this.datedTasks.get(toAdd.getDate()).containsKey(toAdd.getName())) {
                throw new TaskAlreadyExistsException("A task with the same name already exists for the given date.");
            }
            this.datedTasks.get(toAdd.getDate()).put(toAdd.getName(), toAdd);
        }
        else {
            this.datedTasks.put(toAdd.getDate(), new HashMap<>());
            this.datedTasks.get(toAdd.getDate()).put(toAdd.getName(), toAdd);
        }
    }

    public Task getTask(String taskName, LocalDate taskDate) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonNull(taskDate, "Task date");
        Utils.assertNonEmpty(taskName, "Task name");

        if (!this.datedTasks.containsKey(taskDate)) {
            throw new NoSuchTaskException("There aren't any tasks with such date.");
        }

        Map<String, Task> correctDateTasks = this.datedTasks.get(taskDate);

        if (!correctDateTasks.containsKey(taskName)) {
            throw new NoSuchTaskException("There isn't a task with such name in the dated tasks.");
        }

        return correctDateTasks.get(taskName);
    }

    public Set<Task> getTasksForGivenDate(LocalDate taskDate) {
        Utils.assertNonNull(taskDate, "Task date");

        if (!this.datedTasks.containsKey(taskDate)) {
            throw new NoSuchTaskException("There aren't any tasks for the given day");
        }

        return Set.copyOf(this.datedTasks.get(taskDate).values());
    }

    public Set<Task> getTasks() {
        Set<Task> result = new HashSet<>();

        for (LocalDate date : this.datedTasks.keySet()) {
            result.addAll(this.datedTasks.get(date).values());
        }

        return Set.copyOf(result);
    }

    public Task remove(String taskName, LocalDate taskDate) {
        Utils.assertNonNull(taskName, "Task name");
        Utils.assertNonNull(taskDate, "Task date");
        Utils.assertNonEmpty(taskName, "Task name");

        Task toRemove = this.getTask(taskName, taskDate);

        this.datedTasks.get(taskDate).remove(taskName);
        if (this.datedTasks.get(taskDate).isEmpty()) {
            this.datedTasks.remove(taskDate);
        }

        return toRemove;
    }

    public List<String> serialize() {
        List<String> result = new ArrayList<>();

        for (LocalDate date : this.datedTasks.keySet()) {
            for (Task task : this.datedTasks.get(date).values()) {
                result.add(task.serialize());
            }
        }

        return result;
    }
}
