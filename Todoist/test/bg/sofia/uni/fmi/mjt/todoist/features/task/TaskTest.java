package bg.sofia.uni.fmi.mjt.todoist.features.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {
    @Test
    void testTaskConstructorWithNullName() {
        String assertMessage = "Task name shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new Task(null, null, null, null), assertMessage);
    }

    @Test
    void testTaskConstructorWithEmptyName() {
        String assertMessage = "Task name shouldn't be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new Task("", null, null, null), assertMessage);
    }
}