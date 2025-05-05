package repository;

import entity.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {

    private final List<User> users = new ArrayList<>();

    // Add a user
    public void addUser(User user) {
        users.add(user);
    }

    // Remove a user by ID
    public boolean removeUserById(int id) {
        return users.removeIf(user -> user.getId() == id);
    }

    // Find a user by ID
    public Optional<User> findUserById(int id) {
        return users.stream()
                .filter(user -> user.getId() == id)
                .findFirst();
    }

    // Find a user by email
    public Optional<User> findUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // Authenticate a user by email and password
    public boolean authenticate(String email, String password) {
        return users.stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password));
    }

    // List all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    // List only ResourceManagers
    public List<ResourceManager> getAllResourceManagers() {
        List<ResourceManager> managers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof ResourceManager) {
                managers.add((ResourceManager) user);
            }
        }
        return managers;
    }

    // List only RegularUsers
    public List<RegularUser> getAllRegularUsers() {
        List<RegularUser> regularUsers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof RegularUser) {
                regularUsers.add((RegularUser) user);
            }
        }
        return regularUsers;
    }

    // List all Admins
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Admin) {
                admins.add((Admin) user);
            }
        }
        return admins;
    }
}
