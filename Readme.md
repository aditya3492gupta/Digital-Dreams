# Digital Dreams

## Project Overview
<p>Digital Dreams is a Java-based service management system designed to efficiently manage various services, including Wellness Facilities, Transportation, Room Management, and Event Spaces. The application is built with a modular design, making it scalable and maintainable.</p>

## Features
<ul>
<li>User Authentication (Admin, ResourceManager, RegularUser)</li>
<li>Wellness Facility Management</li>
<li>Transportation Management</li>
<li>Room Management</li>
<li>Event Space Management</li>
<li>Scalable and maintainable architecture</li>
<li>Modular design following OOP principles</li>
</ul>

## Controller
```
Digital-Dreams-main
├── controller
│   ├── Main.java
│   └── MainController.java
├── entity
│   ├── user
    │   ├── Admin.java
    │   ├── RegularUser.java
    │   ├── RegularManager.java
    │   └── User.java
│   ├── cart
    │   ├── Cart.java
    │   ├── CartHistory.java
    │   ├── CartItem.java
    │   ├── RoomCartItem.java
    │   ├── EventCartItem.java
    │   └── WellnessFacilityCartItem.java
│   ├── EventSpace.java
│   ├── GoldEventSpace.java
│   ├── PlatinumEventSpace.java
│   ├── Room.java
│   ├── SilverEventSpace.java
│   ├── Transportation.java
│   └── WellnessFacility.java
└── repository
│   ├── CartRepository.java
│   ├── EventSpaceRepository.java
│   ├── RoomRepository.java
│   ├── TransportationRepository.java
│   ├── UserRepository.java
│   └── WellnessFacilityRepository.java
└── service
│   ├── CartHistoryDisplayService.java
│   ├── CartHistoryService.java
│   ├── CartService.java
│   ├── EventSpaceService.java
│   ├── RoomService.java
│   ├── TransportationService.java
│   ├── UserService.java
│   └── WellnessFacilityService.java
└── utility
│   └── Validation.java
```

## Setup Instructions
<ol>
<li>Clone the repository.</li>

<li>Make sure you have JDK 17 or above installed.</li>

<li>Compile the Java files using a Java IDE (we used VS Code) or command line.</li>

<li>Run the MainController.java file to start the application.</li>
</ol>