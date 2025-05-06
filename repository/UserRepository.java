package repository;

import entity.user.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {


    private final Map<Integer, RegularUser> regularUserMap = new HashMap<>();
    private final Map<Integer, ResourceManager> resourceManagerMap = new HashMap<>();
    private final Map<Integer, Admin> adminMap = new HashMap<>();
    private static int userIdCounter = 1;

    public UserRepository() {
        // Preload a default admin
        Admin adminUser = new Admin(userIdCounter, "Admin", "admin@system.com", "admin123", 35);
        adminMap.put(adminUser.getId(), adminUser);
        userIdCounter++;

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


    // Add new admin (if needed)
    public void addAdmin(Admin admin) {
        adminMap.put(admin.getId(), admin);
    }

    // Get regular user by ID
    public RegularUser getRegularUserById(int id) {
        return regularUserMap.get(id);

    }

    // List all users
    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }


    // Get admin by ID
    public Admin getAdminById(int id) {
        return adminMap.get(id);
    }

    // Get user by Email (searches all maps)
    public User getUserByEmail(String email) {
        for (User user : regularUserMap.values()) {
            if (user.getEmail().equalsIgnoreCase(email))
                return user;
        }
        for (User user : resourceManagerMap.values()) {
            if (user.getEmail().equalsIgnoreCase(email))
                return user;
        }
        for (User user : adminMap.values()) {
            if (user.getEmail().equalsIgnoreCase(email))
                return user;
        }
        return null;
    }

    // Authenticate user
    public User authenticate(String email, String password) {
        for (User user : regularUserMap.values()) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password))
                return user;
        }
        for (User user : resourceManagerMap.values()) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password))
                return user;
        }
        for (User user : adminMap.values()) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password))
                return user;
        }
        return null;

    }

    // List only RegularUsers
    public List<RegularUser> getAllRegularUsers() {

        return new ArrayList<>(regularUserMap.values());
    }

    // Get all resource managers
    public List<ResourceManager> getAllResourceManagers() {
        return new ArrayList<>(resourceManagerMap.values());
    }

    // Get all admins
    public List<Admin> getAllAdmins() {
        return new ArrayList<>(adminMap.values());
    }

    // Update regular user
    public boolean updateRegularUser(int id, RegularUser updatedUser) {
        if (regularUserMap.containsKey(id)) {
            regularUserMap.put(id, updatedUser);
            return true;

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

        return false;
    }

    // Update admin
    public boolean updateAdmin(int id, Admin updatedAdmin) {
        if (adminMap.containsKey(id)) {
            adminMap.put(id, updatedAdmin);
            return true;
        }
        return false;
    }

    // Delete regular user by email
    public boolean deleteRegularUser(String email) {
        User user = getUserByEmail(email);
        if (user != null && user instanceof RegularUser) {
            regularUserMap.remove(user.getId());
            return true;
        }
        return false;
    }

    // Delete resource manager by email
    public boolean deleteResourceManager(String email) {
        User user = getUserByEmail(email);
        if (user != null && user instanceof ResourceManager) {
            resourceManagerMap.remove(user.getId());
            return true;
        }
        return false;
    }

    // Delete admin by email
    public boolean deleteAdmin(String email) {
        User user = getUserByEmail(email);
        if (user != null && user instanceof Admin) {
            adminMap.remove(user.getId());
            return true;
        }
        return false;
    }

    // Check if email is already taken
    public boolean isEmailTaken(String email) {
        return getUserByEmail(email) != null;

    }
}
