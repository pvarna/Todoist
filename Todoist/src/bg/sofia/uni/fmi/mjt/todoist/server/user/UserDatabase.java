package bg.sofia.uni.fmi.mjt.todoist.server.user;

import bg.sofia.uni.fmi.mjt.todoist.exceptions.NoSuchUserException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.UserAlreadyExistsException;
import bg.sofia.uni.fmi.mjt.todoist.exceptions.WrongPasswordException;

import java.util.HashMap;
import java.util.Map;

public class UserDatabase {

    private Map<String, User> users;

    public UserDatabase() {
        this.users = new HashMap<>();
    }

    public void register(String username, String password) {
        if (this.users.containsKey(username)) {
            throw new UserAlreadyExistsException("A user with such username already exists.");
        }

        this.users.put(username, new User(username, password));
    }

    public void login(String username, String password) {
        if (!this.users.containsKey(username)) {
            throw new NoSuchUserException("There isn't a user with such username");
        }

        if (!this.users.get(username).getPassword().equals(password)) {
            throw new WrongPasswordException("Wrong password");
        }
    }

    public User getUser(String username) {
        return this.users.get(username);
    }
}
