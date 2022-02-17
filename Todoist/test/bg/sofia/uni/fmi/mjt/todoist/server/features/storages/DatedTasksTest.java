package bg.sofia.uni.fmi.mjt.todoist.server.features.storages;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DatedTasksTest {

    @Test
    void testAddTaskWithNullTask() {
        String assertMessage = "The task should not be null";

        assertThrows(IllegalArgumentException.class, () -> new DatedTasks().addTask(null), assertMessage);
    }

    @Test
    void testAddTasksWithRepeatingTasks() {
        String assertMessage = "There shouldn't be repeating dated tasks";

        DatedTasks datedTasks = new DatedTasks();
        datedTasks.addTask(new Task("task", LocalDate.of(2022,2,16), null, null));

        assertThrows(TaskAlreadyExistsException.class,
                () -> datedTasks.addTask(new Task("task", LocalDate.of(2022,2,16),
                        null, null)), assertMessage);
    }

    @Test
    void testAddTaskWithSingleTask() {
        String assertMessage = "The size of all tasks isn't correct";

        DatedTasks datedTasks = new DatedTasks();
        datedTasks.addTask(new Task("task", LocalDate.of(2022,2,16), null, null));

        assertEquals(1, datedTasks.getTasks().size(), assertMessage);
    }

    @Test
    void testAddTaskWithThreeTaskWithDifferentDates() {
        String assertMessage = "The size of all tasks isn't correct";

        DatedTasks datedTasks = new DatedTasks();

        datedTasks.addTask(new Task("task1", LocalDate.of(2022,2,15), null, null));
        datedTasks.addTask(new Task("task2", LocalDate.of(2022,2,16), null, null));
        datedTasks.addTask(new Task("task3", LocalDate.of(2022,2,17), null, null));

        assertEquals(3, datedTasks.getTasks().size(), assertMessage);
    }

    @Test
    void testAddTaskWithTwoTasksWithSameDate() {
        String assertMessage = "There shouldn't be two or more tasks with same name and date";

        DatedTasks datedTasks = new DatedTasks();

        datedTasks.addTask(new Task("task1", LocalDate.of(2022,2,15), null, null));
        datedTasks.addTask(new Task("task2", LocalDate.of(2022,2,15), null, null));

        assertEquals(2, datedTasks.getTasks().size(), assertMessage);
    }

    @Test
    void testGetTaskWithNullName() {
        String assertMessage = "The task name should not be null";

        assertThrows(IllegalArgumentException.class,
                () -> new DatedTasks().getTask(null, LocalDate.of(2022,2,15)),
                assertMessage);
    }

    @Test
    void testGetTaskWithNullDate() {
        String assertMessage = "The date should not be null";

        assertThrows(IllegalArgumentException.class, () -> new DatedTasks().getTask("task", null),
                assertMessage);
    }

    @Test
    void testGetTaskWithEmptyName() {
        String assertMessage = "The task name should not be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new DatedTasks().getTask("", LocalDate.of(2022,2,15)),
                assertMessage);
    }

    @Test
    void testGetTaskWithNonExistingTask() {
        String assertMessage = "Only getting an existing task is allowed";

        DatedTasks datedTasks = new DatedTasks();
        datedTasks.addTask(new Task("task", LocalDate.of(2022,2,15), null, null));

        assertThrows(NoSuchTaskException.class,
                () -> datedTasks.getTask("taskk", LocalDate.of(2022,2,15)),
                assertMessage);
    }

    @Test
    void testGetTaskWithNonExistingDate() {
        String assertMessage = "Only getting an existing task is allowed";

        DatedTasks datedTasks = new DatedTasks();
        datedTasks.addTask(new Task("task", LocalDate.of(2022,2,15), null, null));

        assertThrows(NoSuchTaskException.class,
                () -> datedTasks.getTask("task", LocalDate.of(2022,2,16)),
                assertMessage);
    }

    @Test
    void testGetTaskWithExistingTask() {
        String assertMessage = "Wrong task is returned";

        DatedTasks datedTasks = new DatedTasks();
        Task toAdd = new Task("task", LocalDate.of(2022,2,15), null, null);
        datedTasks.addTask(toAdd);

        assertEquals(datedTasks.getTask("task", LocalDate.of(2022,2,15)), toAdd, assertMessage);
    }

    @Test
    void testRemoveWithNullName() {
        String assertMessage = "The task name should not be null";

        assertThrows(IllegalArgumentException.class,
                () -> new DatedTasks().remove(null, LocalDate.of(2022,2,15)),
                assertMessage);
    }

    @Test
    void testRemoveWithEmptyName() {
        String assertMessage = "The task name should not be empty";

        assertThrows(IllegalArgumentException.class, () -> new DatedTasks().remove("", LocalDate.of(2022,2,15)));
    }

    @Test
    void testRemoveTaskWithNonExistingTask() {
        String assertMessage = "Only removing an existing task is allowed";

        DatedTasks datedTasks = new DatedTasks();
        datedTasks.addTask(new Task("task", LocalDate.of(2022,2,15), null, null));

        assertThrows(NoSuchTaskException.class,
                () -> datedTasks.remove("taskk", LocalDate.of(2022,2,15)),
                assertMessage);
    }

    @Test
    void testRemoveTaskWithExistingTask() {
        String assertMessage = "Wrong task removal";

        DatedTasks datedTasks = new DatedTasks();

        datedTasks.addTask(new Task("task", LocalDate.of(2022,2,15), null, null));
        datedTasks.remove("task", LocalDate.of(2022,2,15));

        assertEquals(0, datedTasks.getTasks().size(), assertMessage);
    }

    @Test
    void testGetTasksForGivenDate() {
        String assertMessage = "Wrong count of tasks returned";

        DatedTasks datedTasks = new DatedTasks();
        datedTasks.addTask(new Task("task1", LocalDate.of(2022,2,16), null, null));
        datedTasks.addTask(new Task("task2", LocalDate.of(2022,2,15), null, null));
        datedTasks.addTask(new Task("task3", LocalDate.of(2022,2,15), null, null));
        datedTasks.addTask(new Task("task4", LocalDate.of(2022,2,15), null, null));

        assertEquals(3, datedTasks.getTasksForGivenDate(LocalDate.of(2022, 2, 15)).size(), assertMessage);
    }

    @Test
    void testGetTasksIsUnmodifiable() {
        String assertMessage = "Returned collection is not unmodifiable";

        DatedTasks datedTasks = new DatedTasks();
        datedTasks.addTask(new Task("task1", LocalDate.of(2022,2,15), null, null));
        datedTasks.addTask(new Task("task2", LocalDate.of(2022,2,15), null, null));

        Set<Task> tasks = datedTasks.getTasks();

        try {
            tasks.clear();
        } catch (UnsupportedOperationException e) {
            return;
        }

        fail(assertMessage);
    }
}