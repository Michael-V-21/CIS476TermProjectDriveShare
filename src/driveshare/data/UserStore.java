package driveshare.data;

import driveshare.model.User;
import java.util.ArrayList;
import java.util.List;

// List type database for all users

public class UserStore
{
    // List of all users
    private static List<User> users = new ArrayList<>();

    public static void addUser(User user) {
        users.add(user);
    }


    public static boolean emailExists(String email) {
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    // Finds user by email and password -- for login
    public static User findUser(String email, String password) {
        for (User user : users) {
            if (user.getEmail().equals(email) &&
                    user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Finds user by email -- used for password recovery
    public static User findUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    // Gives all users -- used in my dropdowns
    public static List<User> getUsers() {
        return users;
    }
}