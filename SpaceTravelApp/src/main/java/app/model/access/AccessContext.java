package app.model.access;

import app.model.users.User;

import java.util.List;

/**
 * Here is the access strategy of the Strategy design patter being picked
 */
public class AccessContext {
    private Accessible accessStrategy;

    public void setAccessStrategy(Accessible accessStrategy) {
        this.accessStrategy = accessStrategy;
    }

    /**
     * @param username username
     * @param birthYear birth year of the user
     * @param password user's password
     * @param existingUsers the list of existing users in the system
     * @param token token required when the Guide or Owner tries to signup
     * @return success code: 0 - correct signup, 1 - existing username, 3 - incorrect token
     *
     * Based on the strategy of signup (for Traveler, Guide or Owner) the corresponding signup method is called
     */
    public int signup(String username,int birthYear, int password, List<User> existingUsers, int token) {
        return accessStrategy.signup(username, password,birthYear, existingUsers, token);
    }

    /**
     * @param username username
     * @param password user's password
     * @param existingUsers the list of existing users in the system
     * @param token token required when the Guide or Owner tries to signup
     * @return success code: 0 - correct signup, 1 - existing username, 2 - incorrect password,3 - incorrect token
     *
     * Based on the strategy of login (for Traveler, Guide or Owner) the corresponding login method is called
     */
    public int login(String username, int password, List<User> existingUsers, int token) {
        return accessStrategy.login(username, password, existingUsers, token);
    }

    public Accessible getAccessStrategy() {
        return accessStrategy;
    }
}
