package controller;

import entity.*;
import entity.cart.Cart;
import entity.cart.CartItem;
import entity.user.*;
import java.util.List;
import java.util.Scanner;

import entity.user.Admin;
import entity.user.RegularUser;
import entity.user.ResourceManager;
import entity.user.User;
import repository.EventSpaceRepository;
import repository.RoomRepository;
import repository.TransportationRepository;
import repository.UserRepository;
import repository.CartRepository;
import repository.WellnessFacilityRepository;
import service.EventSpaceService;
import service.RoomService;
import service.CartService;
import service.TransportationService;
import service.UserService;
import service.WellnessFacilityService;
import utility.Validation;

public class MainController {

    private final UserService userService;
    private final WellnessFacilityService wellnessFacilityService;
    private final TransportationService transportationService;
    private final RoomService roomService;
    private final EventSpaceService eventSpaceService;

    private final CartService cartService;
    private String currentUserId = null;

    private String loggedInUserEmail;

    private final Scanner scanner = new Scanner(System.in);

    public MainController() {
        UserRepository userRepository = new UserRepository();
        userService = new UserService(userRepository);

        wellnessFacilityService = new WellnessFacilityService();

        TransportationRepository transportationRepository = new TransportationRepository();
        transportationService = new TransportationService(transportationRepository);

        RoomRepository roomRepository = new RoomRepository();
        roomService = new RoomService(roomRepository);

        EventSpaceRepository eventSpaceRepository = new EventSpaceRepository();
        eventSpaceService = new EventSpaceService(eventSpaceRepository);

        CartRepository cartRepository = new CartRepository();

        WellnessFacilityRepository facilityRepository = new WellnessFacilityRepository();

        cartService = new CartService(
                cartRepository,
                roomService,
                roomRepository,
                eventSpaceService,
                transportationService,
                wellnessFacilityService,
                transportationRepository,
                facilityRepository);

        initializeStaticData();
    }

    Validation v = new Validation();

    public void start() {
        while (true) {
            System.out.println("\n===== Welcome to Resource Management System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Regular User Login");
            System.out.println("3. Resource Manager Login");
            System.out.println("4. Register as Regular User");

            System.out.println("5. Exit");
            int choice = v.getIntInput("Enter your choice: ");

            switch (choice) {
                case 1 -> adminLogin();
                case 2 -> regularUserLogin();
                case 3 -> resourceManagerLogin();
                case 4 -> registerRegularUser();
                // case 5 -> registerResourceManager();
                case 5 -> {
                    System.out.println("Exiting system...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void adminLogin() {
        String email = v.getStringInput("Admin Email: ");
        String password = v.getStringInput("Password: ");
        User user = userService.login(email, password);
        if (user instanceof Admin) {
            System.out.println("Admin logged in: " + user.getName());
            currentUserId = user.getEmail(); // Set current user
            adminMenu();
            currentUserId = null; // Reset current user on logout
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private void regularUserLogin() {

        String email = v.getStringInput("Email: ");
        String password = v.getStringInput("Password: ");

        User user = userService.login(email, password);
        if (user instanceof RegularUser) {
            System.out.println("Regular User logged in: " + user.getName());

            currentUserId = user.getEmail(); // Set current user

            setLoggedInUserEmail(email);

            userMenu();
            currentUserId = null; // Reset current user on logout
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    // Comments
    private void resourceManagerLogin() {
        String email = v.getStringInput("Email: ");
        String password = v.getStringInput("Password: ");
        User user = userService.login(email, password);
        if (user instanceof ResourceManager) {
            System.out.println("Resource Manager logged in: " + user.getName());

            currentUserId = user.getEmail(); // Set current user

            setLoggedInUserEmail(email);

            managerMenu();
            currentUserId = null; // Reset current user on logout
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void registerRegularUser() {
        String name = v.getStringInput("Name: ");
        String email = v.getStringInput("Email: ");
         if (!Validation.isValidEmail(email)) {
        System.out.println("Invalid email format.");
        return;
        }
        String password = v.getStringInput("Password: ");
        if (!Validation.isValidPassword(password)) {
            System.out.println("Password too weak.");
            return;
        }
        int age = v.getIntInput("Age: ");
        String address = v.getStringInput("Address: ");
        String phone = v.getStringInput("Phone: ");
        if (!Validation.isValidPhoneNumber(phone)) {
            System.out.println("Enter correct phn no");
            return;
        }

        boolean success = userService.registerRegularUser(name, email, password, age, address, phone);
        System.out.println(success ? "User registered." : "Email already taken.");
    }

    private void registerResourceManager() {
        String name = v.getStringInput("Name: ");
        String email = v.getStringInput("Email: ");
        if (!Validation.isValidEmail(email)) {
            System.out.println("Invalid email format.");
            return;
        }
        String password = v.getStringInput("Password: ");
        if (!Validation.isValidPassword(password)) {
            System.out.println("Password too weak.");
            return;
        }
        int age = v.getIntInput("Age: ");
        String phone = v.getStringInput("Phone: ");
        if (!Validation.isValidPhoneNumber(phone)) {
            System.out.println("Enter correct phn no");
            return;
        }

        boolean success = userService.registerResourceManager(name, email, password, age, phone);
        System.out.println(success ? "Manager registered." : "Email already taken.");
    }

    private void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Users");
            System.out.println("2. Add Regular User");
            System.out.println("3. Add Resource Manager");
            System.out.println("4. Delete Regular User");
            System.out.println("5. Delete Resource Manager");

            // System.out.println("6. Update User Profile");
            System.out.println("6. View Available Resources");
            System.out.println("7. Logout");
            int choice = v.getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> {
                    System.out.println("Regular Users:");
                    userService.getAllRegularUsers()
                            .forEach(u -> System.out.println(u.getName() + " - " + u.getEmail()));
                    System.out.println("Resource Managers:");
                    userService.getAllResourceManagers()
                            .forEach(m -> System.out.println(m.getName() + " - " + m.getEmail()));
                }
                case 2 -> registerRegularUser(); // reuse existing method
                case 3 -> registerResourceManager(); // reuse existing method
                case 4 -> {
                    String email = v.getStringInput("Enter email of Regular User to delete: ");

                    if (userService.deleteRegularUser(email)) {
                        System.out.println("Regular user deleted.");
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 5 -> {
                    String email = v.getStringInput("Enter email of Resource Manager to delete: ");
                    if (userService.deleteResourceManager(email)) {
                        System.out.println("Manager deleted.");
                    } else {
                        System.out.println("Manager not found.");
                    }
                }

                case 6 -> {

                    System.out.println("Wellness Facilities:");
                    wellnessFacilityService.showAvailableFacilities();
                    System.out.println("Available Vehicles:");
                    transportationService.showAvailableVehicles();
                    System.out.println("Available Rooms:");
                    roomService.showAvailableRooms();
                    System.out.println("Available Event Spaces:");
                    List<EventSpace> available = eventSpaceService.getAllEventSpaces();
                    available.stream().filter(EventSpace::isAvailable)
                            .forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
                }
                case 7 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void managerMenu() {
        while (true) {
            System.out.println("\n--- Resource Manager Menu ---");
            System.out.println("1. Manage Wellness Facilities");
            System.out.println("2. Manage Transportation");
            System.out.println("3. Manage Rooms");
            System.out.println("4. Manage Event Spaces");

            System.out.println("5. Update Manager Profile");
            System.out.println("6. Logout");

            int choice = v.getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> wellnessFacilityMenu();
                case 2 -> transportationMenu();
                case 3 -> roomMenu();
                case 4 -> eventSpaceMenu();
                case 5 -> updateOwnProfile();
                case 6 -> {
                    System.out.println("Logging out...");
                    return;
                }

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void updateOwnProfile() {
        System.out.println("\n--- Update Profile ---");
        System.out.print("New Name: ");
        String name = scanner.nextLine();
        System.out.print("New Password: ");
        String password = scanner.nextLine();
        if (!Validation.isValidPassword(password)) {
            System.out.println("Password too weak.");
            return;
        }

        boolean updated = userService.updateUserProfile(loggedInUserEmail, name, password);
        System.out.println(updated ? "Profile updated successfully." : "Profile update failed.");
    }

    // Set the logged-in user's email when they log in
    private void setLoggedInUserEmail(String email) {
        this.loggedInUserEmail = email;
    }

    private void userMenu() {
        while (true) {
            System.out.println("\n--- Regular User Menu ---");
            System.out.println("1. View Available Wellness Facilities");
            System.out.println("2. View Available Vehicles");
            System.out.println("3. View Available Rooms");
            System.out.println("4. View Available Event Spaces");
            System.out.println("5. Add Room to Cart");
            System.out.println("6. Add Event Space to Cart");
            System.out.println("7. Add Wellness Facility to Cart");
            System.out.println("8. Add Transportation to Cart");
            System.out.println("9. View Cart");
            System.out.println("10. Checkout");
            System.out.println("11. Update User Profile");
            System.out.println("12. Logout");

            int choice = v.getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> wellnessFacilityService.showAvailableFacilities();
                case 2 -> transportationService.showAvailableVehicles();
                case 3 -> roomService.showAvailableRooms();
                case 4 -> {
                    List<EventSpace> availableSpaces = eventSpaceService.getAllEventSpaces();
                    availableSpaces.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
                }

                case 5 -> addRoomToCart();
                case 6 -> addEventSpaceToCart();
                case 7 -> addWellnessFacilityToCart();
                case 8 -> addTransportationToCart();
                case 9 -> viewCart();
                case 10 -> checkoutCart();
                case 11 -> updateOwnProfile();
                case 12 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // 5. Add methods for cart operations
    private void addRoomToCart() {
        // Show available rooms
        roomService.showAvailableRooms();

        System.out.print("Room ID to add to cart: ");
        String roomId = scanner.nextLine();

        Room room = roomService.getRoomById(roomId);
        if (room != null && room.isAvailable()) {
            cartService.addRoomToCart(currentUserId, room.getRoomId(), room.getType(), room.getCost());
            System.out.println("Room added to cart!");
        } else {
            System.out.println("Room not available or not found.");
        }
    }

    private void addEventSpaceToCart() {
        // Show available event spaces
        List<EventSpace> availableSpaces = eventSpaceService.getAllEventSpaces();
        System.out.println("\nAvailable Event Spaces:");
        availableSpaces.stream()
                .filter(EventSpace::isAvailable)
                .forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));

        System.out.print("Event Space ID to add to cart: ");
        String spaceId = scanner.nextLine();

        EventSpace space = eventSpaceService.getEventSpaceById(spaceId);
        if (space != null && space.isAvailable()) {
            // Get appropriate cost based on type
            double cost = 0.0;
            if (space instanceof GoldEventSpace gold) {
                cost = gold.getBookingCost();
            } else if (space instanceof SilverEventSpace silver) {
                cost = silver.getBookingCost();
            } else if (space instanceof PlatinumEventSpace platinum) {
                cost = platinum.getBookingCost();
            }

            cartService.addEventSpaceToCart(currentUserId, space.getSpaceId(), space.getType(), cost);
            System.out.println("Event Space added to cart!");
        } else {
            System.out.println("Event Space not available or not found.");
        }
    }

    private void addWellnessFacilityToCart() {
        // Show available facilities
        wellnessFacilityService.showAvailableFacilities();

        System.out.print("Facility ID to add to cart: ");
        String facilityId = scanner.nextLine();

        System.out.print("Number of hours: ");
        int hours = Integer.parseInt(scanner.nextLine());

        // We need to assume WellnessFacility has a getFacilityById method
        WellnessFacility facility = wellnessFacilityService.getFacilityById(facilityId);
        if (facility != null && facility.isAvailable()) {
            cartService.addWellnessFacilityToCart(
                    currentUserId,
                    facility.getFacilityId(),
                    facility.getType(),
                    hours,
                    facility.getPricePerHour());
            System.out.println("Wellness facility added to cart!");
        } else {
            System.out.println("Facility not available or not found.");
        }
    }

    private void addTransportationToCart() {
        // Show available vehicles
        transportationService.showAvailableVehicles();

        System.out.print("Vehicle ID to add to cart: ");
        String vehicleId = scanner.nextLine();

        // We need to assume TransportationService has a getVehicleById method
        Transportation vehicle = transportationService.getVehicleById(vehicleId);
        if (vehicle != null && vehicle.isAvailable()) {
            cartService.addVehicleToCart(
                    currentUserId,
                    vehicle.getVehicleId(),
                    vehicle.getVehicleType(),
                    vehicle.getCost());
            System.out.println("Vehicle added to cart!");
        } else {
            System.out.println("Vehicle not available or not found.");
        }
    }

    private void viewCart() {
        if (currentUserId == null) {
            System.out.println("No user is logged in.");
            return;
        }

        Cart cart = cartService.getCartForUser(currentUserId);
        List<CartItem> items = cart.getItems();

        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\n--- Your Cart ---");
        int index = 1;
        for (CartItem item : items) {
            System.out.println(index + ". " + item.getDescription() + " - ₹" + item.getCost());
            index++;
        }

        System.out.println("\nTotal: ₹" + cart.getTotalCost());

        System.out.println("\n1. Remove item from cart");
        System.out.println("2. Clear cart");
        System.out.println("3. Back to menu");
        System.out.print("Choose an option: ");

        int choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1 -> {
                System.out.print("Enter item number to remove: ");
                int itemIndex = Integer.parseInt(scanner.nextLine()) - 1;

                if (itemIndex >= 0 && itemIndex < items.size()) {
                    CartItem itemToRemove = items.get(itemIndex);
                    cartService.removeItemFromCart(currentUserId, itemToRemove.getItemId());
                    System.out.println("Item removed from cart.");
                } else {
                    System.out.println("Invalid item number.");
                }
            }
            case 2 -> {
                cartService.clearCart(currentUserId);
                System.out.println("Cart cleared.");
            }
            case 3 -> {
                System.out.println("Returning to menu...");
                return;
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private void checkoutCart() {
        if (currentUserId == null) {
            System.out.println("No user is logged in.");
            return;
        }

        Cart cart = cartService.getCartForUser(currentUserId);

        if (cart.getItems().isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\n--- Checkout ---");
        viewCart(); // Show the cart contents first

        System.out.print("\nProceed with checkout? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            boolean success = cartService.checkout(currentUserId);
            if (success) {
                System.out.println("Checkout successful! All items have been booked.");
            } else {
                System.out.println("Checkout failed. Some items may no longer be available.");
            }
        } else {
            System.out.println("Checkout cancelled.");
        }
    }

    private void wellnessFacilityMenu() {
        while (true) {
            System.out.println("\n--- Wellness Facility ---");
            System.out.println("1. Register New Facility");
            System.out.println("2. Book Facility");
            System.out.println("3. Release Facility");
            System.out.println("4. Show Available Facilities");
            System.out.println("5. Back to Previous Menu");
            int choice = v.getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> {
                    String id = v.getStringInput("Facility ID: ");
                    String type = v.getStringInput("Type: ");
                    double price = v.getDoubleInput("Price per Hour: ");
                    wellnessFacilityService.registerNewFacility(id, type, price);
                }
                case 2 -> {
                    String type = v.getStringInput("Enter type to book(Gym / Swimming Pool): ");
                    int hours = v.getIntInput("Enter number of hours: ");
                    wellnessFacilityService.bookFacilityWithHours(type, hours);
                }
                case 3 -> {
                    String id = v.getStringInput("Enter facility ID to release: ");
                    wellnessFacilityService.releaseFacility(id);
                }
                case 4 -> wellnessFacilityService.showAvailableFacilities();
                case 5 -> {
                    System.out.println("Returning to previous menu...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void transportationMenu() {
        System.out.println("\n--- Transportation ---");
        System.out.println("1. Add Vehicle");
        System.out.println("2. Book Vehicle");
        System.out.println("3. Release Vehicle");
        System.out.println("4. Show Available Vehicles");
        int choice = v.getIntInput("Choose an option: ");

        switch (choice) {
            case 1 -> {
                String id = v.getStringInput("Vehicle ID: ");
                String type = v.getStringInput("Type: ");
                double cost = v.getDoubleInput("Cost: ");
                transportationService.addVehicle(id, type, cost, true);
            }
            case 2 -> {
                transportationService.showAvailableVehicles();
                String type = v.getStringInput("Type vehicle Id to book: ");
                transportationService.bookVehicle(type);
            }
            case 3 -> {
                String id = v.getStringInput("Type vehicle ID to release: ");
                transportationService.releaseVehicle(id);
            }
            case 4 -> transportationService.showAvailableVehicles();
            default -> System.out.println("Invalid choice.");
        }
    }

    private void roomMenu() {
        System.out.println("\n--- Room Management ---");
        System.out.println("1. Add Room");
        System.out.println("2. Book Room");
        System.out.println("3. Release Room");
        System.out.println("4. Show Available Rooms");
        int choice = v.getIntInput("Choose an option: ");

        switch (choice) {
            case 1 -> {
                String id = v.getStringInput("Room ID: ");
                System.out.print("Type (2AC / 2NAC / 4AC / 4NAC): ");
                String type = v.getStringInput("Type (2AC / 2NAC / 4AC / 4NAC): ");
                double cost = v.getDoubleInput("Cost (in ₹): ");

                boolean added = roomService.addRoom(id, type, true, cost);
                if (added) {
                    System.out.println("Room added successfully.");
                } else {
                    System.out.println("Room already exists.");
                }
            }
            case 2 -> {
                String type = v.getStringInput("Room Type to book (2AC / 2NAC / 4AC / 4NAC): ");

                Room booked = roomService.bookRoom(type);
                if (booked != null) {
                    System.out.println("Room booked: " + booked);
                } else {
                    System.out.println("No available room.");
                }
            }
            case 3 -> {
                String id = v.getStringInput("Room ID to release: ");
                boolean released = roomService.releaseRoom(id);
                if (released) {
                    System.out.println("Room released successfully.");
                } else {
                    System.out.println("Failed to release room.");
                }
            }
            case 4 -> roomService.showAvailableRooms();
            default -> System.out.println("Invalid choice.");
        }
    }

    private void eventSpaceMenu() {
        System.out.println("\n--- Event Space ---");
        System.out.println("1. Add Event Space");
        System.out.println("2. Search Available Spaces");
        System.out.println("3. Delete Event Space");
        System.out.println("4. Book Event Space by Type");
        System.out.println("5. Book Event Space by ID");
        System.out.println("6. Release Event Space");
        System.out.println("7. View All Event Spaces");
        int choice = v.getIntInput("Choose an option: ");

        switch (choice) {
            case 1 -> {
                String id = v.getStringInput("Space ID: ");
                String type = v.getStringInput("Type: ");
                eventSpaceService.createEventSpace(new EventSpace(id, type, true));
                System.out.println("Event space added successfully!");
            }
            case 2 -> {
                String type = v.getStringInput("Type: ");
                List<EventSpace> available = eventSpaceService.searchAvailableSpacesByType(type);
                if (available.isEmpty()) {
                    System.out.println("No available event spaces of type: " + type);
                } else {
                    System.out.println("Available " + type + " event spaces:");
                    available.forEach(e -> System.out
                            .println(e.getSpaceId() + " - " + e.getType() + " - Available: " + e.isAvailable()));
                }
            }
            case 3 -> {
                String id = v.getStringInput("Space ID to delete: ");
                boolean deleted = eventSpaceService.deleteEventSpace(id);
                if (deleted) {
                    System.out.println("Event space deleted successfully!");
                } else {
                    System.out.println("Failed to delete. Event space not found.");
                }
            }
            case 4 -> {
                System.out.print("Type to book: ");
                String type = scanner.nextLine();
                EventSpace booked = eventSpaceService.bookEventSpaceByType(type);
                if (booked != null) {
                    System.out.println("Event space booked successfully!");
                }

            }
            case 5 -> {
                // Display available spaces first
                List<EventSpace> allSpaces = eventSpaceService.getAllEventSpaces();
                System.out.println("\nAll Event Spaces:");
                allSpaces.forEach(e -> System.out
                        .println(e.getSpaceId() + " - " + e.getType() + " - Available: " + e.isAvailable()));

                System.out.print("\nSpace ID to book: ");
                String id = scanner.nextLine();
                EventSpace booked = eventSpaceService.bookEventSpace(id);
                if (booked != null) {
                    System.out.println("Event space booked successfully!");
                }
            }
            case 6 -> {
                // Display all spaces first
                List<EventSpace> allSpaces = eventSpaceService.getAllEventSpaces();
                System.out.println("\nAll Event Spaces:");
                allSpaces.forEach(e -> System.out
                        .println(e.getSpaceId() + " - " + e.getType() + " - Available: " + e.isAvailable()));

                System.out.print("\nSpace ID to release: ");
                String id = scanner.nextLine();
                boolean released = eventSpaceService.releaseEventSpace(id);
                if (released) {
                    System.out.println("Event space released successfully!");
                }
            }
            case 7 -> {
                List<EventSpace> allSpaces = eventSpaceService.getAllEventSpaces();
                if (allSpaces.isEmpty()) {
                    System.out.println("No event spaces available in the system.");
                } else {
                    System.out.println("\nAll Event Spaces:");
                    allSpaces.forEach(e -> System.out
                            .println(e.getSpaceId() + " - " + e.getType() + " - Available: " + e.isAvailable()));
                }
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private void initializeStaticData() {
        // Regular Users
        userService.registerRegularUser("Amit Sharma", "amit", "amit", 28, "Delhi", "9876543210");
        userService.registerRegularUser("Neha Verma", "neha", "neha", 25, "Mumbai", "9123456789");

        // Resource Managers
        userService.registerResourceManager("Rajiv Mehta", "rajiv", "rajiv", 35, "9988776655");
        userService.registerResourceManager("Sonal Kapoor", "sonal", "sonal", 32, "8877665544");

        transportationService.addVehicle("V101", "2-wheeler", 500.0, true);
        transportationService.addVehicle("V102", "4-wheeler", 1000.0, true);
        transportationService.addVehicle("V103", "2-wheeler", 500.0, true);
        transportationService.addVehicle("V104", "4-wheeler", 1000.0, true);

        EventSpace gold = new GoldEventSpace("Gold", true);
        EventSpace silver = new SilverEventSpace("Silver", true);
        EventSpace platinum = new PlatinumEventSpace("Platinum", true);

        eventSpaceService.createEventSpace(gold);
        eventSpaceService.createEventSpace(silver);
        eventSpaceService.createEventSpace(platinum);

        wellnessFacilityService.registerNewFacility("SP1", "Swimming Pool", 200.0);
        wellnessFacilityService.registerNewFacility("SP2", "Swimming Pool", 200.0);
        wellnessFacilityService.registerNewFacility("GYM1", "Gym", 150.0);
        wellnessFacilityService.registerNewFacility("GYM2", "Gym", 150.0);
    }

}
