package service;

import entity.user.RegularUser;
import entity.user.ResourceManager;
import entity.user.User;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Register a new RegularUser
    public boolean registerRegularUser(String name, String email, String password, int age, String address,
            String phoneNumber) {
        if (userRepository.isEmailTaken(email))
            return false;

        int id = userRepository.generateUserId();
        RegularUser user = new RegularUser(id, name, email, password, age, address, phoneNumber);
        userRepository.addRegularUser(user);
        return true;
    }

    // Register a new ResourceManager
    public boolean registerResourceManager(String name, String email, String password, int age, String phoneNumber) {
        if (userRepository.isEmailTaken(email))
            return false;

        int id = userRepository.generateUserId();
        ResourceManager manager = new ResourceManager(id, name, email, password, age, phoneNumber);
        userRepository.addResourceManager(manager);
        return true;
    }

    // Authenticate user by email & password
    public User login(String email, String password) {
        return userRepository.authenticate(email, password);
    }

    // Get RegularUser by ID
    public RegularUser getRegularUserById(int id) {
        return userRepository.getRegularUserById(id);
    }

    // Get ResourceManager by ID
    public ResourceManager getResourceManagerById(int id) {
        return userRepository.getResourceManagerById(id);
    }

    // Get user by email (Regular or Resource Manager)
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    // Update a user's profile based on email
    public boolean updateUserProfile(String email, String name, String password) {
        User user = getUserByEmail(email);
        if (user == null) {
            return false;
        }

        // Update the user's profile
        if (user instanceof RegularUser) {
            RegularUser updatedUser = new RegularUser(user.getId(), name, email, password,
                    ((RegularUser) user).getAge(), ((RegularUser) user).getAddress(),
                    ((RegularUser) user).getPhoneNumber());
            return userRepository.updateRegularUser(user.getId(), updatedUser);
        } else if (user instanceof ResourceManager) {
            ResourceManager updatedManager = new ResourceManager(user.getId(), name, email, password,
                    ((ResourceManager) user).getAge(), ((ResourceManager) user).getPhoneNumber());
            return userRepository.updateResourceManager(user.getId(), updatedManager);
        }
        return false;
    }

    // Update a regular user's profile
    public boolean updateRegularUser(int id, String name, String email, String password, int age, String address,
            String phoneNumber) {
        RegularUser existing = getRegularUserById(id);
        if (existing == null)
            return false;

        RegularUser updated = new RegularUser(id, name, email, password, age, address, phoneNumber);
        return userRepository.updateRegularUser(id, updated);
    }

    // Update a resource manager's profile
    public boolean updateResourceManager(int id, String name, String email, String password, int age,
            String phoneNumber) {
        ResourceManager existing = getResourceManagerById(id);
        if (existing == null)
            return false;

        ResourceManager updated = new ResourceManager(id, name, email, password, age, phoneNumber);
        return userRepository.updateResourceManager(id, updated);
    }

    // Delete Regular User by email
    public boolean deleteRegularUser(String email) {
        return userRepository.deleteRegularUser(email);
    }

    // Delete Resource Manager by email
    public boolean deleteResourceManager(String email) {
        return userRepository.deleteResourceManager(email);
    }

    // Delete Admin by email
    public boolean deleteAdmin(String email) {
        return userRepository.deleteAdmin(email);
    }

    // Get all registered regular users
    public List<RegularUser> getAllRegularUsers() {
        return userRepository.getAllRegularUsers();
    }

    // Get all resource managers
    public List<ResourceManager> getAllResourceManagers() {
        return userRepository.getAllResourceManagers();
    }

}
