package app.model.access;

import app.model.users.User;

import java.util.List;

/**
 * Interface that connects all Accesses
 */
public interface Accessible {
    int signup(String username, int birthYear, int password, List<User> existingUsers, int token);
    int login(String username, int password, List<User> existingUsers, int token);
}
