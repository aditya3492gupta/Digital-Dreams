package controller;

import entity.*;
import entity.user.*;
import java.util.List;
import java.util.Scanner;
import repository.*;
import service.*;

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

    public void start() {
        while (true) {
            System.out.println("\n===== Welcome to Resource Management System =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Regular User Login");
            System.out.println("3. Resource Manager Login");
            System.out.println("4. Register as Regular User");

            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(scanner.nextLine());

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
        System.out.print("Admin Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        User user = userService.login(email, password);
        if (user instanceof Admin) {
            System.out.println("Admin logged in: " + user.getName());
            adminMenu();
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    private void regularUserLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        User user = userService.login(email, password);
        if (user instanceof RegularUser) {
            System.out.println("Regular User logged in: " + user.getName());
            userMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void resourceManagerLogin() {
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        User user = userService.login(email, password);
        if (user instanceof ResourceManager) {
            System.out.println("Resource Manager logged in: " + user.getName());
            managerMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }

    private void registerRegularUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

        boolean success = userService.registerRegularUser(name, email, password, age, address, phone);
        System.out.println(success ? "User registered." : "Email already taken.");
    }

    private void registerResourceManager() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Phone: ");
        String phone = scanner.nextLine();

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
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

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
                    System.out.print("Enter email of Regular User to delete: ");
                    String email = scanner.nextLine();
                    if (userService.deleteRegularUser(email)) {
                        System.out.println("Regular user deleted.");
                    } else {
                        System.out.println("User not found.");
                    }
                }
                case 5 -> {
                    System.out.print("Enter email of Resource Manager to delete: ");
                    String email = scanner.nextLine();
                    if (userService.deleteResourceManager(email)) {
                        System.out.println("Manager deleted.");
                    } else {
                        System.out.println("Manager not found.");
                    }
                }
                case 6 -> {
                    System.out.print("Enter email to update: ");
                    String email = scanner.nextLine();
                    System.out.print("New Name: ");
                    String name = scanner.nextLine();
                    System.out.print("New Password: ");
                    String password = scanner.nextLine();
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
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

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
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> wellnessFacilityService.showAvailableFacilities();
                case 2 -> transportationService.showAvailableVehicles();
                case 3 -> roomService.showAvailableRooms();
                case 4 -> {
                    List<EventSpace> availableSpaces = eventSpaceService.getAllEventSpaces();
                    availableSpaces.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
                }
                case 5 -> {
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
            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());
    
            switch (choice) {
                case 1 -> {
                    System.out.print("Facility ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Type: ");
                    String type = scanner.nextLine();
                    System.out.print("Price per Hour: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    wellnessFacilityService.registerNewFacility(id, type, price);
                }
                case 2 -> {
                    System.out.print("Enter type to book: ");
                    String type = scanner.nextLine();
                    System.out.print("Enter number of hours: ");
                    int hours = Integer.parseInt(scanner.nextLine());
                    wellnessFacilityService.bookFacilityWithHours(type, hours);
                }
                case 3 -> {
                    System.out.print("Enter facility ID to release: ");
                    String id = scanner.nextLine();
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
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Vehicle ID: ");
                String id = scanner.nextLine();
                System.out.print("Type: ");
                String type = scanner.nextLine();
                System.out.print("Cost: ");
                double cost = Double.parseDouble(scanner.nextLine());
                transportationService.addVehicle(id, type, cost, true);
            }
            case 2 -> {
                transportationService.showAvailableVehicles();
                System.out.print("Type vehicle Id to book: ");
                String type = scanner.nextLine();
                transportationService.bookVehicle(type);
            }
            case 3 -> {
                System.out.print("Type vehicle ID to release: ");
                String id = scanner.nextLine();
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
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Room ID: ");
                String id = scanner.nextLine();
                System.out.print("Type: ");
                String type = scanner.nextLine();
                roomService.addRoom(id, type, true);
            }
            case 2 -> {
                System.out.print("Room Type to book: ");
                String type = scanner.nextLine();

                Room booked = roomService.bookRoom(type);
                System.out.println(booked != null ? "Room booked: " + booked.getRoomId() : "No available room.");
            }
            case 3 -> {
                System.out.print("Room ID to release: ");
                String id = scanner.nextLine();
                roomService.releaseRoom(id);
            }
            case 4 -> roomService.showAvailableRooms();
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
    }

    private void eventSpaceMenu() {
        System.out.println("\n--- Event Space ---");
        System.out.println("1. Add Event Space");
        System.out.println("2. Search Available Spaces");
        System.out.println("3. Delete Event Space");
        System.out.println("4. View All Event Spaces");
        System.out.print("Choose an option: ");
        int choice = Integer.parseInt(scanner.nextLine());

        switch (choice) {
            case 1 -> {
                System.out.print("Space ID: ");
                String id = scanner.nextLine();
                System.out.print("Type: ");
                String type = scanner.nextLine();
                eventSpaceService.createEventSpace(new EventSpace(id, type, true));
            }
            case 2 -> {
                System.out.print("Type: ");
                String type = scanner.nextLine();
                List<EventSpace> available = eventSpaceService.searchAvailableSpacesByType(type);
                available.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
            }
            case 3 -> {
                System.out.print("Space ID to delete: ");
                String id = scanner.nextLine();
                eventSpaceService.deleteEventSpace(id);
            }
            case 4 -> {
                List<EventSpace> allSpaces = eventSpaceService.getAllEventSpaces();
                allSpaces.forEach(e -> System.out.println(e.getSpaceId() + " - " + e.getType()));
            }
            default -> System.out.println("Invalid choice.");
        }
    }
}
