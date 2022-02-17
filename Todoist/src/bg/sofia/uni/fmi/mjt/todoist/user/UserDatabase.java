package bg.sofia.uni.fmi.mjt.todoist.user;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchUserException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongPasswordException;
import bg.sofia.uni.fmi.mjt.todoist.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserDatabase {

    private final Map<String, User> users;

    public UserDatabase() {
        this.users = new HashMap<>();
    }

    public void register(String username, String password) {
        Utils.assertNonNull(username, "Username");
        Utils.assertNonEmpty(username, "Username");
        Utils.assertNonNull(password, "Password");
        Utils.assertNonEmpty(password, "Password");

        if (this.users.containsKey(username)) {
            throw new UserAlreadyExistsException("A user with such username already exists.");
        }

        this.users.put(username, new User(username, password));
    }

    public void login(String username, String password) {
        Utils.assertNonNull(username, "Username");
        Utils.assertNonEmpty(username, "Username");
        Utils.assertNonNull(password, "Password");
        Utils.assertNonEmpty(password, "Password");

        if (!this.users.containsKey(username)) {
            throw new NoSuchUserException("There isn't a user with such username");
        }

        if (!this.users.get(username).getPassword().equals(password)) {
            throw new WrongPasswordException("Wrong password");
        }
    }

    public User getUser(String username) {
        Utils.assertNonNull(username, "Username");
        Utils.assertNonEmpty(username, "Username");

        if (!this.users.containsKey(username)) {
            throw new NoSuchUserException("There isn't a user with such username");
        }

        return this.users.get(username);
    }

    public String serialize() {
        return this.users.values().stream().map(User::serialize).collect(Collectors.joining(System.lineSeparator()));
    }
}
