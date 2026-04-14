package driveshare.patterns.singleton;

import driveshare.model.User;

// SINGLETON CLASS -- manages user session - only 1 at a time

public class SessionManager
{
    // Only 1 SessionManager instance
    private static SessionManager instance;
    private User currentUser;

    // Private constructor prevents other from logging in
    private SessionManager() {
    }

    // How i provided access for the single instance -- SINGLETON PATTERN!!
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        currentUser = user;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}