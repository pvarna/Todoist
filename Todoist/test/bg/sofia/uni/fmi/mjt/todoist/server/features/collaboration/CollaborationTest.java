package bg.sofia.uni.fmi.mjt.todoist.server.features.collaboration;


import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.storages.Collaborations;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.CollaborationTask;
import bg.sofia.uni.fmi.mjt.todoist.server.features.task.Task;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CollaborationTest {

    @Test
    void testConstructorWithNullName() {
        String assertMessage = "The collaboration name shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new Collaboration(null, new User(null, null)), assertMessage);
    }

    @Test
    void testConstructorWithEmptyName() {
        String assertMessage = "The collaboration name shouldn't be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new Collaboration("", new User(null, null)), assertMessage);
    }

    @Test
    void testConstructorWithNullUser() {
        String assertMessage = "The collaboration admin shouldn't be null";

        assertThrows(IllegalArgumentException.class, () -> new Collaboration("col", null), assertMessage);
    }

    @Test
    void testAddTaskWithNull() {
        String assertMessage = "The task shouldn't be null";

        assertThrows(IllegalArgumentException.class, () -> new Collaboration("col",
                new User("pesho", "nz")).addNewTask(null), assertMessage);
    }

    @Test
    void testAddTaskWithRepeatingTasks() {
        String assertMessage = "Tasks in a collaboration shouldn't be repeated";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        collaboration.addNewTask(new CollaborationTask("task1", null, null, null));

        assertThrows(TaskAlreadyExistsException.class,
                () -> collaboration.addNewTask(new CollaborationTask("task1", null, null, null)), assertMessage);
    }

    @Test
    void testAddTaskWithThreeTasks() {
        String assertMessage = "Wrong count of returned tasks";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        collaboration.addNewTask(new CollaborationTask("task1", null, null, null));

        assertThrows(TaskAlreadyExistsException.class,
                () -> collaboration.addNewTask(new CollaborationTask("task1", null, null, null)), assertMessage);
    }

}