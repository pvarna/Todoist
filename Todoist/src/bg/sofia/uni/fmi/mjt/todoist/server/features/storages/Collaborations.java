package bg.sofia.uni.fmi.mjt.todoist.server.features.storages;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.CollaborationAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchCollaborationException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongCreatorException;
import bg.sofia.uni.fmi.mjt.todoist.server.features.collaboration.Collaboration;
import bg.sofia.uni.fmi.mjt.todoist.server.user.User;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Collaborations {

    private final String ownerName;
    private final Map<String, Collaboration> collaborations;

    public Collaborations(String ownerName) {
        Utils.assertNonNull(ownerName, "Admin");
        Utils.assertNonEmpty(ownerName, "Admin");

        this.collaborations = new HashMap<>();
        this.ownerName = ownerName;
    }

    public void addCollaboration(Collaboration toAdd) {
        Utils.assertNonNull(toAdd, "Collaboration to add");

        if (this.collaborations.containsKey(toAdd.getName())) {
            throw new CollaborationAlreadyExistsException("A collaboration with the same name already exists.");
        }

        this.collaborations.put(toAdd.getName(), toAdd);
    }

    public Collaboration getCollaboration(String collaborationName) {
        Utils.assertNonNull(collaborationName, "Collaboration name");
        Utils.assertNonEmpty(collaborationName, "Collaboration name");

        if (!this.collaborations.containsKey(collaborationName)) {
            throw new NoSuchCollaborationException("There isn't a collaboration with such name");
        }

        return this.collaborations.get(collaborationName);
    }

    public void deleteCollaboration(String collaborationName) {
        Utils.assertNonNull(collaborationName, "Collaboration name");
        Utils.assertNonEmpty(collaborationName, "Collaboration name");

        Collaboration toDelete = this.getCollaboration(collaborationName);

        if (!toDelete.getAdmin().getUsername().equals(this.ownerName)) {
            throw new WrongCreatorException("Only the creator of the collaboration can delete it");
        }

        for (User current : toDelete.getCollaborators()) {
            current.removeCollaboration(collaborationName);
        }

        //toDelete.getAdmin().removeCollaboration(collaborationName);
        this.removeCollaboration(collaborationName);
    }

    public void removeCollaboration(String collaborationName) {
        if (!this.collaborations.containsKey(collaborationName)) {
            throw new NoSuchCollaborationException("There isn't a collaboration with such name");
        }

        this.collaborations.remove(collaborationName);
    }

    public Set<Collaboration> getCollaborations() {
        return Set.copyOf(this.collaborations.values());
    }

    public List<String> serialize() {
        List<String> result = new ArrayList<>();

        for (Collaboration collaboration : this.collaborations.values()) {
            if (this.ownerName.equals(collaboration.getAdmin().getUsername())) {
                result.addAll(collaboration.serialize());
            }
        }

        return result;
    }
}
