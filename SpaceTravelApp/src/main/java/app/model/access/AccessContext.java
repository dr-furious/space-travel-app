package app.model.access;

import app.model.users.User;

import java.util.List;

public class AccessContext {
    private Accessible accessStrategy;

    public void setAccessStrategy(Accessible accessStrategy) {
        this.accessStrategy = accessStrategy;
    }

    public int signup(String username,int birthYear, int password, List<User> existingUsers, int token) {
        return accessStrategy.signup(username, password,birthYear, existingUsers, token);
    }

    public int login(String username, int password, List<User> existingUsers, int token) {
        return accessStrategy.login(username, password, existingUsers, token);
    }

    public Accessible getAccessStrategy() {
        return accessStrategy;
    }
}
