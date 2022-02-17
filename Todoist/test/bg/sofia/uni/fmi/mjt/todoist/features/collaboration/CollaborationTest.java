package bg.sofia.uni.fmi.mjt.todoist.features.collaboration;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchUserException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.TaskAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.features.task.CollaborationTask;
import bg.sofia.uni.fmi.mjt.todoist.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        collaboration.addNewTask(new CollaborationTask("task2", null, null, null));
        collaboration.addNewTask(new CollaborationTask("task3", null, null, null));

        assertEquals(3, collaboration.getTasks().size(), assertMessage);
    }

    @Test
    void testAddNewCollaboratorWithNullUser() {
        String assertMessage = "User shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new Collaboration("col", new User("pesho", "nz")).addNewCollaborator(null),
                assertMessage);
    }

    @Test
    void testAddNewCollaboratorWhoIsAlreadyAdded() {
        String assertMessage = "Collaborators shouldn't be repeated in a collaboration";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));

        assertThrows(UserAlreadyExistsException.class, () -> collaboration.addNewCollaborator(new User("pesho", "alo")),
                assertMessage);
    }

    @Test
    void testAddNewCollaboratorWithOneCollaborator() {
        String assertMessage = "Wrong count of collaborators";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        collaboration.addNewCollaborator(new User("gosho", "alo"));

        assertEquals(1, collaboration.getCollaborators().size(), assertMessage);
    }

    @Test
    void testAssignTaskWithNullTaskName() {
        String assertMessage = "Task name shouldn't be null";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(IllegalArgumentException.class, () -> collaboration.assignTask(null, "nz"), assertMessage);
    }

    @Test
    void testAssignTaskWithEmptyTaskName() {
        String assertMessage = "Task name shouldn't be empty";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(IllegalArgumentException.class, () -> collaboration.assignTask("", "nz"), assertMessage);
    }

    @Test
    void testAssignTaskWithNullUsername() {
        String assertMessage = "Username shouldn't be null";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(IllegalArgumentException.class, () -> collaboration.assignTask("nz", null), assertMessage);
    }

    @Test
    void testAssignTaskWithEmptyUsername() {
        String assertMessage = "Username shouldn't be empty";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(IllegalArgumentException.class, () -> collaboration.assignTask("nz", ""), assertMessage);
    }

    @Test
    void testAssignTaskWithNonExistingTask() {
        String assertMessage = "The task that should be assigned should be present in the collaboration";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(NoSuchTaskException.class, () -> collaboration.assignTask("task1", "pesho"), assertMessage);
    }

    @Test
    void testAssignTaskWithNonExistingUser() {
        String assertMessage = "The user to whom the task should be assigned to should be present in the collaboration";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        collaboration.addNewTask(new CollaborationTask("task", null, null, null));
        assertThrows(NoSuchUserException.class, () -> collaboration.assignTask("task", "gosho"), assertMessage);
    }

    @Test
    void testAssignTaskWithValidArguments() {
        String assertMessage = "Wrong task assignment";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        collaboration.addNewTask(new CollaborationTask("task", null, null, null));
        collaboration.assignTask("task", "pesho");

        CollaborationTask task = (CollaborationTask) collaboration.getTask("task");
        assertEquals(task.getAssignee(), collaboration.getAdmin().getUsername(), assertMessage);
    }

    @Test
    void testGetTaskWithNull() {
        String assertMessage = "Task name shouldn't be null";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(IllegalArgumentException.class, () -> collaboration.getTask(null), assertMessage);
    }

    @Test
    void testGetTaskWithEmptyTaskName() {
        String assertMessage = "Task name shouldn't be empty";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(IllegalArgumentException.class, () -> collaboration.getTask(""), assertMessage);
    }

    @Test
    void testGetTaskWithNonExistingTask() {
        String assertMessage = "Only getting existing tasks is allowed";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(NoSuchTaskException.class, () -> collaboration.getTask("task"), assertMessage);
    }

    @Test
    void testGetTaskWithExistingTask() {
        String assertMessage = "Wrong task getting";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        collaboration.addNewTask(new CollaborationTask("task", null, null, null));

        assertEquals(collaboration.getTask("task"),
                new CollaborationTask("task", null, null, null), assertMessage);
    }

    @Test
    void testRemoveTaskWithNull() {
        String assertMessage = "Task name shouldn't be null";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(IllegalArgumentException.class, () -> collaboration.removeTask(null), assertMessage);
    }

    @Test
    void testRemoveTaskWithEmptyTaskName() {
        String assertMessage = "Task name shouldn't be empty";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(IllegalArgumentException.class, () -> collaboration.removeTask(""), assertMessage);
    }

    @Test
    void testRemoveTaskWithNonExistingTask() {
        String assertMessage = "Only removing existing tasks is allowed";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        assertThrows(NoSuchTaskException.class, () -> collaboration.removeTask("task"), assertMessage);
    }

    @Test
    void testRemoveTaskWithExistingTask() {
        String assertMessage = "Wrong task removal";

        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        collaboration.addNewTask(new CollaborationTask("task", null, null, null));
        collaboration.removeTask("task");

        assertThrows(NoSuchTaskException.class, collaboration::getTasks, assertMessage);
    }

    @Test
    void testSerialize() {
        Collaboration collaboration = new Collaboration("col", new User("pesho", "nz"));
        collaboration.addNewCollaborator(new User("gosho", "nz"));
        collaboration.addNewTask(new CollaborationTask("task", null, null, null));

        assertEquals(1 + 1 + 1, collaboration.serialize().size());
    }
}