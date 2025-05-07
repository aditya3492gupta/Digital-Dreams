package controller;
import java.util.List;
import java.util.Scanner;

import entity.EventSpace;
import entity.GoldEventSpace;
import entity.PlatinumEventSpace;
import entity.Room;
import entity.SilverEventSpace;
import entity.user.Admin;
import entity.user.RegularUser;
import entity.user.ResourceManager;
import entity.user.User;
import repository.EventSpaceRepository;
import repository.RoomRepository;
import repository.TransportationRepository;
import repository.UserRepository;
import service.EventSpaceService;
import service.RoomService;
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
            adminMenu();
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
            userMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void resourceManagerLogin() {
        String email = v.getStringInput("Email: ");
        String password = v.getStringInput("Password: ");
        User user = userService.login(email, password);
        if (user instanceof ResourceManager) {
            System.out.println("Resource Manager logged in: " + user.getName());
            managerMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void registerRegularUser() {
        String name = v.getStringInput("Name: ");
        String email = v.getStringInput("Email: ");
        String password = v.getStringInput("Password: ");
        int age = v.getIntInput("Age: ");
        String address = v.getStringInput("Address: ");
        String phone = v.getStringInput("Phone: ");

        boolean success = userService.registerRegularUser(name, email, password, age, address, phone);
        System.out.println(success ? "User registered." : "Email already taken.");
    }

    private void registerResourceManager() {
        String name = v.getStringInput("Name: ");
        String email = v.getStringInput("Email: ");
        String password = v.getStringInput("Password: ");
        int age = v.getIntInput("Age: ");
        String phone = v.getStringInput("Phone: ");

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
            System.out.println("6. Update User Profile");
            System.out.println("7. View Available Resources");
            System.out.println("8. Logout");
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
                    String email = v.getStringInput("Enter email to update: ");
                    String name = v.getStringInput("New Name: ");
                    String password = v.getStringInput("New Password: ");

                    boolean updated = userService.updateUserProfile(email, name, password);
                    System.out.println(updated ? "User profile updated." : "User not found.");
                }
                case 7 -> {
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
                case 8 -> {
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
            System.out.println("5. Logout");
            int choice = v.getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> wellnessFacilityMenu();
                case 2 -> transportationMenu();
                case 3 -> roomMenu();
                case 4 -> eventSpaceMenu();
                case 5 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void userMenu() {
        while (true) {
            System.out.println("\n--- Regular User Menu ---");
            System.out.println("1. View Available Wellness Facilities");
            System.out.println("2. View Available Vehicles");
            System.out.println("3. View Available Rooms");
            System.out.println("4. View Available Event Spaces");
            System.out.println("5. Book Rooms");
            System.out.println("6. Book Event Space");
            System.out.println("7. Book Wellness Facility");
            System.out.println("8. Book Transportation");
            System.out.println("9. Logout");
            int choice = v.getIntInput("Choose an option: ");

            switch (choice) {
                case 1 -> wellnessFacilityService.showAvailableFacilities();
                case 2 -> transportationService.showAvailableVehicles();
                case 3 -> roomService.showAvailableRooms();
                case 4 -> {
                    List<EventSpace> availableSpaces = eventSpaceService.getAllEventSpaces();
                    availableSpaces.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
                }
                case 5 -> {
                    String type = v.getStringInput("Room Type to book (2AC / 2NAC / 4AC / 4NAC): ");

                    Room booked = roomService.bookRoom(type);
                    if (booked != null) {
                        System.out.println("Room booked: " + booked);
                    } else {
                        System.out.println("No available room.");
                    }
                }
                case 6 -> {
                    List<EventSpace> allSpaces = eventSpaceService.getAllEventSpaces();
                    allSpaces.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
                    System.out.println();
                    String type = v.getStringInput("Type to book: ");
                    eventSpaceService.bookEventSpaceByType(type);
                }
                case 7 -> {
                    String type = v.getStringInput("Enter type to book(Gym / Swimming Pool): ");
                    int hours = v.getIntInput("Enter number of hours: ");
                    wellnessFacilityService.bookFacilityWithHours(type, hours);
                }
                case 8 -> {
                    transportationService.showAvailableVehicles();
                    String type = v.getStringInput("Type vehicle Id to book: ");
                    transportationService.bookVehicle(type);
                }
                case 9 -> {
                    System.out.println("Logging out...");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
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
                    String type = v.getStringInput("Type: ");                    double price = v.getDoubleInput("Price per Hour: ");
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
        System.out.println("4. Book Event Space");
        System.out.println("5. View All Event Spaces");
        int choice = v.getIntInput("Choose an option: ");

        switch (choice) {
            case 1 -> {
                String id = v.getStringInput("Space ID: ");
                String type = v.getStringInput("Type: ");
                eventSpaceService.createEventSpace(new EventSpace(id, type, true));
            }
            case 2 -> {
                String type = v.getStringInput("Type: ");
                List<EventSpace> available = eventSpaceService.searchAvailableSpacesByType(type);
                available.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
            }
            case 3 -> {
                String id = v.getStringInput("Space ID to delete: ");
                eventSpaceService.deleteEventSpace(id);
            }
            case 4 -> {
                List<EventSpace> allSpaces = eventSpaceService.getAllEventSpaces();
                allSpaces.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
                System.out.println();
                String type = v.getStringInput("Type to book: ");
                eventSpaceService.bookEventSpaceByType(type);
            }
            case 5 -> {
                List<EventSpace> allSpaces = eventSpaceService.getAllEventSpaces();
                allSpaces.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
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

        EventSpace gold = new GoldEventSpace("GOLD1", true);
        EventSpace silver = new SilverEventSpace("SILVER1", true);
        EventSpace platinum = new PlatinumEventSpace("PLATINUM1", true);

        eventSpaceService.createEventSpace(gold);
        eventSpaceService.createEventSpace(silver);
        eventSpaceService.createEventSpace(platinum);
    }

}
