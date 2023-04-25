package app.model.access;

import app.model.users.User;

import java.util.List;

public interface Accessible {
    boolean signup(String username, int birthYear, int password, List<User> existingUsers);
    boolean login(String username, int password, List<User> existingUsers);
}
