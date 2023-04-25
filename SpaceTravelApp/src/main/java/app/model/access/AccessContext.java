package app.model.access;

import app.model.users.User;

import java.util.List;

public class AccessContext {
    private Accessible accessStrategy;

    public void setAccessStrategy(Accessible accessStrategy) {
        this.accessStrategy = accessStrategy;
    }

    public boolean signup(String username,int birthYear, int password, List<User> existingUsers) {
        return accessStrategy.signup(username, password,birthYear, existingUsers);
    }

    public boolean login(String username, int password, List<User> existingUsers) {
        return accessStrategy.login(username, password, existingUsers);
    }
}
