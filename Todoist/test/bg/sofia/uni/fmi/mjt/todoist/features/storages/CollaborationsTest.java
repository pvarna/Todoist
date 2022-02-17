package bg.sofia.uni.fmi.mjt.todoist.features.storages;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.CollaborationAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchCollaborationException;
import bg.sofia.uni.fmi.mjt.todoist.features.collaboration.Collaboration;
import bg.sofia.uni.fmi.mjt.todoist.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CollaborationsTest {

    @Test
    void testConstructorWithNullOwnerName() {
        String assertMessage = "The owner name shouldn't be null";

        assertThrows(IllegalArgumentException.class, () -> new Collaborations(null), assertMessage);
    }

    @Test
    void testConstructorWithEmptyOwnerName() {
        String assertMessage = "The owner name shouldn't be empty";

        assertThrows(IllegalArgumentException.class, () -> new Collaborations(""), assertMessage);
    }

    @Test
    void testAddCollaborationWithNull() {
        String assertMessage = "The collaboration shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new Collaborations("pesho").addCollaboration(null), assertMessage);
    }

    @Test
    void testAddCollaborationWithRepeatedCollaborations() {
        String assertMessage = "The collaborations shouldn't be repeated";

        Collaborations collaborations = new Collaborations("pesho");
        collaborations.addCollaboration(new Collaboration("col1", new User("pesho", "nz")));

        assertThrows(CollaborationAlreadyExistsException.class,
                () -> collaborations.addCollaboration(new Collaboration("col1", new User("pesho", "nz"))), assertMessage);
    }

    @Test
    void testAddCollaborationWithOneCollaboration() {
        String assertMessage = "Wrong number of returned collaborations";

        Collaborations collaborations = new Collaborations("pesho");
        collaborations.addCollaboration(new Collaboration("col1", new User("pesho", "nz")));

        assertEquals(1, collaborations.getCollaborations().size(), assertMessage);
    }

    @Test
    void testAddCollaborationWithThreeCollaboration() {
        String assertMessage = "Wrong number of returned collaborations";

        Collaborations collaborations = new Collaborations("pesho");
        collaborations.addCollaboration(new Collaboration("col1", new User("pesho", "nz")));
        collaborations.addCollaboration(new Collaboration("col2", new User("pesho", "nz")));
        collaborations.addCollaboration(new Collaboration("col3", new User("pesho", "nz")));

        assertEquals(3, collaborations.getCollaborations().size(), assertMessage);
    }

    @Test
    void testGetCollaborationWithNull() {
        String assertMessage = "Collaboration name shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new Collaborations("pesho").getCollaboration(null),
                assertMessage);
    }

    @Test
    void testGetCollaborationWithEmptyName() {
        String assertMessage = "Collaboration name shouldn't be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new Collaborations("pesho").getCollaboration(""),
                assertMessage);
    }

    @Test
    void testGetCollaborationWithNonExistingTask() {
        String assertMessage = "Only getting an existing collaboration is allowed";

        Collaborations collaborations = new Collaborations("pesho");
        collaborations.addCollaboration(new Collaboration("col1", new User("pesho", "nz")));

        assertThrows(NoSuchCollaborationException.class,
                () -> collaborations.getCollaboration("col2"), assertMessage);
    }

    @Test
    void testGetCollaborationWithExistingTask() {
        String assertMessage = "Wrong collaboration returned";

        Collaborations collaborations = new Collaborations("pesho");
        Collaboration col = new Collaboration("col1", new User("pesho", "nz"));
        collaborations.addCollaboration(col);

        assertEquals(collaborations.getCollaboration("col1"), new Collaboration("col1", new User("pesho", "nz")), assertMessage);
    }

    @Test
    void testDeleteCollaborationWithNull() {
        String assertMessage = "The collaboration name shouldn't be null";

        assertThrows(IllegalArgumentException.class,
                () -> new Collaborations("pesho").deleteCollaboration(null),
                assertMessage);
    }

    @Test
    void testDeleteCollaborationWithEmptyName() {
        String assertMessage = "The collaboration name shouldn't be empty";

        assertThrows(IllegalArgumentException.class,
                () -> new Collaborations("pesho").deleteCollaboration(null),
                assertMessage);
    }

    @Test
    void testDeleteCollaborationWithNonExistingCollaboration() {
        String assertMessage = "Only removing an existing task is allowed";

        assertThrows(NoSuchCollaborationException.class,
                () -> new Collaborations("pesho").deleteCollaboration("alo"),
                assertMessage);
    }

    @Test
    void testDeleteCollaborationWithExistingCollaboration() {
        String assertMessage = "Wrong collaboration removal";

        Collaborations collaborations = new Collaborations("pesho");

        Collaboration toAdd = new Collaboration("col1", new User("pesho", "nz"));
        User user = new User("pesho", "nz");
        user.addCollaboration(toAdd);

        collaborations.addCollaboration(new Collaboration("col1", user));
        collaborations.deleteCollaboration("col1");

        assertEquals(0, collaborations.getCollaborations().size(), assertMessage);
    }
}