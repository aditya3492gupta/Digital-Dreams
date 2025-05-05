package repository;

import entity.user.RegularUser;
import entity.user.ResourceManager;
import entity.user.User;
import entity.user.Admin;
import java.util.*;

public class UserRepository {

    private final Map<Integer, RegularUser> regularUserMap = new HashMap<>();
    private final Map<Integer, ResourceManager> resourceManagerMap = new HashMap<>();
    private static int userIdCounter = 1;

    public UserRepository() {
        // Preloading a single admin user
        User adminUser = new Admin(userIdCounter, "Admin", "admin@system.com", "admin123", 35);
        resourceManagerMap.put(adminUser.getId(), (ResourceManager) adminUser);
        userIdCounter++; // Increment after assigning admin ID
    }

    // Generate unique user ID
    public int generateUserId() {
        return userIdCounter++;
    }

    // Add new regular user
    public void addRegularUser(RegularUser user) {
        regularUserMap.put(user.getId(), user);
    }

    // Add new resource manager
    public void addResourceManager(ResourceManager user) {
        resourceManagerMap.put(user.getId(), user);
    }

    // Get regular user by ID
    public RegularUser getRegularUserById(int id) {
        return regularUserMap.get(id);
    }

    // Get resource manager by ID
    public ResourceManager getResourceManagerById(int id) {
        return resourceManagerMap.get(id);
    }

    // Get user by Email (searches both maps)
    public User getUserByEmail(String email) {
        User user = regularUserMap.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);

        if (user == null) {
            user = resourceManagerMap.values().stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst().orElse(null);
        }

        return user;
    }

    // Authenticate user (searches both maps)
    public User authenticate(String email, String password) {
        User user = regularUserMap.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email)
                        && u.getPassword().equals(password))
                .findFirst().orElse(null);

        if (user == null) {
            user = resourceManagerMap.values().stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email)
                            && u.getPassword().equals(password))
                    .findFirst().orElse(null);
        }

        return user;
    }

    // Get all regular users
    public List<RegularUser> getAllRegularUsers() {
        return new ArrayList<>(regularUserMap.values());
    }

    // Get all resource managers
    public List<ResourceManager> getAllResourceManagers() {
        return new ArrayList<>(resourceManagerMap.values());
    }

    // Update regular user
    public boolean updateRegularUser(int id, RegularUser updatedUser) {
        if (regularUserMap.containsKey(id)) {
            regularUserMap.put(id, updatedUser);
            return true;
        }
        return false;
    }

    // Update resource manager
    public boolean updateResourceManager(int id, ResourceManager updatedUser) {
        if (resourceManagerMap.containsKey(id)) {
            resourceManagerMap.put(id, updatedUser);
            return true;
        }
        return false;
    }

    // Delete regular user
    public boolean deleteRegularUser(int id) {
        return regularUserMap.remove(id) != null;
    }

    // Delete resource manager
    public boolean deleteResourceManager(int id) {
        return resourceManagerMap.remove(id) != null;
    }

    // Check if email is already registered (searches both maps)
    public boolean isEmailTaken(String email) {
        return regularUserMap.values().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email)) ||
                resourceManagerMap.values().stream()
                        .anyMatch(user -> user.getEmail().equalsIgnoreCase(email));
    }
}