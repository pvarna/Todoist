package bg.sofia.uni.fmi.mjt.todoist.server.features.storages;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class InboxTest {

    @Test
    void testAddTaskWithNullTask() {
        String assertMessage = "The task should not be null";

        assertThrows(IllegalArgumentException.class, () -> new Inbox().addTask(null), assertMessage);
    }

    @Test
    void testAddTasksWithRepeatingTasks() {
        String assertMessage = "There shouldn't be repeating tasks in the inbox";

        Inbox inbox = new Inbox();
        inbox.addTask(new Task("task", null, null, null));

        assertThrows(TaskAlreadyExistsException.class,
                () -> inbox.addTask(new Task("task", null, null, null)),
                assertMessage);
    }

    @Test
    void testAddTaskWithSingleTask() {
        String assertMessage = "The size of all tasks isn't correct";

        Inbox inbox = new Inbox();
        inbox.addTask(new Task("task", null, null, null));

        assertEquals(1, inbox.getTasks().size(), assertMessage);
    }

    @Test
    void testAddTaskWithThreeTask() {
        String assertMessage = "The size of all tasks isn't correct";

        Inbox inbox = new Inbox();

        inbox.addTask(new Task("task1", null, null, null));
        inbox.addTask(new Task("task2", null, null, null));
        inbox.addTask(new Task("task3", null, null, null));

        assertEquals(3, inbox.getTasks().size(), assertMessage);
    }

    @Test
    void testGetTaskWithNullName() {
        String assertMessage = "The task should not be null";

        assertThrows(IllegalArgumentException.class, () -> new Inbox().getTask(null), assertMessage);
    }

    @Test
    void testGetTaskWithEmptyName() {
        String assertMessage = "The name of the task should not be empty";

        assertThrows(IllegalArgumentException.class, () -> new Inbox().getTask(""), assertMessage);
    }

    @Test
    void testGetTaskWithNonExistingTask() {
        String assertMessage = "Only getting an existing task is allowed";

        Inbox inbox = new Inbox();
        inbox.addTask(new Task("task", null, null, null));

        assertThrows(NoSuchTaskException.class, () -> inbox.getTask("taskk"), assertMessage);
    }

    @Test
    void testGetTaskWithExistingTask() {
        String assertMessage = "Wrong task is returned";

        Inbox inbox = new Inbox();
        Task toAdd = new Task("task", null, null, null);
        inbox.addTask(toAdd);

        assertEquals(inbox.getTask("task"), toAdd, assertMessage);
    }

    @Test
    void testRemoveWithNullName() {
        String assertMessage = "The task name should not be null";

        assertThrows(IllegalArgumentException.class, () -> new Inbox().remove(null), assertMessage);
    }

    @Test
    void testRemoveWithEmptyName() {
        String assertMessage = "The name of the task should not be empty";

        assertThrows(IllegalArgumentException.class, () -> new Inbox().remove(""), assertMessage);
    }

    @Test
    void testRemoveTaskWithNonExistingTask() {
        String assertMessage = "Only removing an existing task is allowed";

        Inbox inbox = new Inbox();
        inbox.addTask(new Task("task", null, null, null));

        assertThrows(NoSuchTaskException.class, () -> inbox.remove("taskk"), assertMessage);
    }

    @Test
    void testRemoveTaskWithExistingTask() {
        String assertMessage = "Wrong task removal";

        Inbox inbox = new Inbox();

        inbox.addTask(new Task("task", null, null, null));
        inbox.remove("task");

        assertThrows(NoSuchTaskException.class, inbox::getTasks, assertMessage);
    }

    @Test
    void testGetTasksIsUnmodifiable() {
        String assertMessage = "Returned collection is not unmodifiable";

        Inbox inbox = new Inbox();
        inbox.addTask(new Task("task1", null, null, null));
        inbox.addTask(new Task("task2", null, null, null));

        Set<Task> tasks = inbox.getTasks();

        try {
            tasks.clear();
        } catch (UnsupportedOperationException e) {
            return;
        }

        fail(assertMessage);
    }
}