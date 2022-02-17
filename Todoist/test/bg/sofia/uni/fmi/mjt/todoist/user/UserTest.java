package bg.sofia.uni.fmi.mjt.todoist.user;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchTaskException;
import bg.sofia.uni.fmi.mjt.todoist.features.collaboration.Collaboration;
import bg.sofia.uni.fmi.mjt.todoist.features.task.Task;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserTest {

    @Test
    void testUserConstructorWithNullUsername() {
        String assertMessage = "Username shouldn't be null";

        assertThrows(IllegalArgumentException.class, () -> new User(null, "pass"),
                assertMessage);
    }

    @Test
    void testUserConstructorWithEmptyUsername() {
        String assertMessage = "Username shouldn't be empty";

        assertThrows(IllegalArgumentException.class, () -> new User("", "pass"),
                assertMessage);
    }

    @Test
    void testUserConstructorWithNullPassword() {
        String assertMessage = "Password shouldn't be null";

        assertThrows(IllegalArgumentException.class, () -> new User("user", null),
                assertMessage);
    }

    @Test
    void testUserConstructorWithEmptyPassword() {
        String assertMessage = "Password shouldn't be empty";

        assertThrows(IllegalArgumentException.class, () -> new User("user", ""),
                assertMessage);
    }

    @Test
    void testAddTaskToInbox() {
        String assertMessage = "Wrong task adding";

        User user = new User("pesho", "nz");
        user.addTaskToInbox(new Task("task", null, LocalDate.of(2022, 2, 22), "dsc"));

        assertEquals(1, user.getTasksFromInbox().size(), assertMessage);

        Task task = user.getFromInbox("task");
        assertEquals(task, new Task("task", null, LocalDate.of(2022, 2, 22), "dsc"),
                assertMessage);
    }

    @Test
    void testRemoveTaskFromInbox() {
        String assertMessage = "Wrong task removal";

        User user = new User("pesho", "nz");
        user.addTaskToInbox(new Task("task", null, LocalDate.of(2022, 2, 22), "dsc"));
        user.removeFromInbox("task");

        assertThrows(NoSuchTaskException.class, user::getTasksFromInbox, assertMessage);
    }

    @Test
    void testAddTaskToDatedTasks() {
        String assertMessage = "Wrong task adding";

        User user = new User("pesho", "nz");
        user.addTaskToDatedTasks(new Task("task", LocalDate.of(2022,2,18), LocalDate.of(2022, 2, 22), "dsc"));

        assertEquals(1, user.getTasksForGivenDate(LocalDate.of(2022,2,18)).size(), assertMessage);

        Task task = user.getFromDatedTasks("task", LocalDate.of(2022,2,18));
        assertEquals(task, new Task("task", LocalDate.of(2022,2,18), LocalDate.of(2022, 2, 22), "dsc"),
                assertMessage);
    }

    @Test
    void testRemoveTaskFromDatedTasks() {
        String assertMessage = "Wrong task removal";

        User user = new User("pesho", "nz");
        user.addTaskToDatedTasks(new Task("task", LocalDate.of(2022,2,18), LocalDate.of(2022, 2, 22), "dsc"));

        user.removeFromDatedTasks("task", LocalDate.of(2022,2,18));

        assertThrows(NoSuchTaskException.class, () -> user.getTasksForGivenDate(LocalDate.of(2022,2,18)), assertMessage);
    }

    @Test
    void testAddCollaboration() {
        String assertMessage = "Wrong collaboration adding";

        User user = new User("pesho", "nz");
        user.addCollaboration(new Collaboration("col", user));

        assertEquals(1, user.getCollaborations().size(), assertMessage);

        Collaboration collaboration = user.getCollaboration("col");
        assertEquals(collaboration, new Collaboration("col", user), assertMessage);
    }

    @Test
    void testRemoveCollaboration() {
        String assertMessage = "Wrong collaboration removal";

        User user = new User("pesho", "nz");
        user.addCollaboration(new Collaboration("col", user));
        user.removeCollaboration("col");

        assertEquals(0, user.getCollaborations().size());
    }
}