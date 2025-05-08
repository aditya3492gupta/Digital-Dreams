package service;

import entity.*;
import entity.cart.Cart;
import entity.cart.CartItem;
import entity.cart.EventSpaceCartItem;
import entity.cart.RoomCartItem;
import entity.cart.TransportationCartItem;
import entity.cart.WellnessFacilityCartItem;
import repository.CartRepository;
import repository.RoomRepository;
import repository.TransportationRepository;
import repository.WellnessFacilityRepository;

public class CartService {
    private final CartRepository cartRepository;
    private final RoomService roomService;
    private final RoomRepository roomRepository;
    private final EventSpaceService eventSpaceService;
    private final TransportationService transportationService;
    private final WellnessFacilityService wellnessFacilityService;
    private final TransportationRepository transportationRepository;
    private final WellnessFacilityRepository facilityRepository;

    public CartService(
            CartRepository cartRepository,
            RoomService roomService,
            RoomRepository roomRepository,
            EventSpaceService eventSpaceService,
            TransportationService transportationService,
            WellnessFacilityService wellnessFacilityService,
            TransportationRepository transportationRepository,
            WellnessFacilityRepository facilityRepository) {
        this.cartRepository = cartRepository;
        this.roomService = roomService;
        this.roomRepository = roomRepository;
        this.eventSpaceService = eventSpaceService;
        this.transportationService = transportationService;
        this.wellnessFacilityService = wellnessFacilityService;
        this.transportationRepository = transportationRepository;
        this.facilityRepository = facilityRepository;
    }

    public Cart getCartForUser(String userId) {
        return cartRepository.getCartForUser(userId);
    }

    public boolean addRoomToCart(String userId, String roomId, String roomType, double cost,int noOfDays) {
        Cart cart = getCartForUser(userId);
        cart.addItem(new RoomCartItem(roomId, roomType, cost,noOfDays));
        cartRepository.saveCart(cart);
        return true;
    }

    public boolean addEventSpaceToCart(String userId, String spaceId, String spaceType, double cost,int noOfDays) {
        Cart cart = getCartForUser(userId);
        cart.addItem(new EventSpaceCartItem(spaceId, spaceType, cost, noOfDays));
        cartRepository.saveCart(cart);
        return true;
    }

    public boolean addVehicleToCart(String userId, String vehicleId, String vehicleType, double cost,int noOfDays) {
        Cart cart = getCartForUser(userId);
        cart.addItem(new TransportationCartItem(vehicleId, vehicleType, cost,noOfDays));
        cartRepository.saveCart(cart);
        return true;
    }

    public boolean addWellnessFacilityToCart(String userId, String facilityId, String facilityType, int hours,
            double costPerHour) {
        Cart cart = getCartForUser(userId);
        cart.addItem(new WellnessFacilityCartItem(facilityId, facilityType, hours, costPerHour));
        cartRepository.saveCart(cart);
        return true;
    }

    public boolean removeItemFromCart(String userId, String itemId) {
        Cart cart = getCartForUser(userId);
        boolean removed = cart.removeItem(itemId);
        if (removed) {
            cartRepository.saveCart(cart);
        }
        return removed;
    }

    public void clearCart(String userId) {
        Cart cart = getCartForUser(userId);
        cart.clearCart();
        cartRepository.saveCart(cart);
    }

    public boolean checkout(String userId) {
        Cart cart = getCartForUser(userId);

        // Process each item in the cart
        boolean allSuccessful = true;
        for (CartItem item : cart.getItems()) {
            boolean success = false;

            switch (item.getResourceType()) {
                case "Room":
                    success = finalizeRoomBooking((RoomCartItem) item);
                    break;
                case "EventSpace":
                    success = finalizeEventSpaceBooking((EventSpaceCartItem) item);
                    break;
                case "Transportation":
                    success = finalizeTransportationBooking((TransportationCartItem) item);
                    break;
                case "WellnessFacility":
                    success = finalizeWellnessFacilityBooking((WellnessFacilityCartItem) item);
                    break;
            }

            if (!success) {
                allSuccessful = false;
                break;
            }
        }

        if (allSuccessful) {
            clearCart(userId);
        }

        return allSuccessful;
    }

    // Helper methods to finalize bookings for each resource type
    private boolean finalizeRoomBooking(RoomCartItem item) {
        return roomService.bookSpecificRoom(item.getResourceId());
    }

    private boolean finalizeEventSpaceBooking(EventSpaceCartItem item) {
        return eventSpaceService.bookEventSpace(item.getResourceId()) != null;
    }

    private boolean finalizeTransportationBooking(TransportationCartItem item) {
        return transportationService.bookSpecificVehicle(item.getResourceId());
    }

    private boolean finalizeWellnessFacilityBooking(WellnessFacilityCartItem item) {
        return wellnessFacilityService.bookSpecificFacility(item.getResourceId(), item.getHours());
    }

    public boolean bookSpecificRoom(String roomId) {
        Room room = roomRepository.getRoomById(roomId);
        if (room != null && room.isAvailable()) {
            room.setAvailable(false);
            roomRepository.updateRoom(room);
            System.out.println("Room booked: " + room.getRoomId() + " - " + room.getType());
            return true;
        }
        System.out.println("Room " + roomId + " is not available for booking.");
        return false;
    }

    // TransportationService.java - add this method
    public boolean bookSpecificVehicle(String vehicleId) {
        Transportation vehicle = transportationRepository.getVehicleById(vehicleId);
        if (vehicle != null && vehicle.isAvailable()) {
            vehicle.setAvailable(false);
            transportationRepository.updateVehicle(vehicle);
            System.out.println("Vehicle booked: " + vehicle.getVehicleId() + " - " + vehicle.getVehicleType());
            System.out.println("Cost: ₹" + vehicle.getCost());
            return true;
        }
        System.out.println("Vehicle " + vehicleId + " is not available for booking.");
        return false;
    }

    public boolean bookSpecificFacility(String facilityId, int hours) {
        WellnessFacility facility = facilityRepository.getFacilityById(facilityId);
        if (facility != null && facility.isAvailable()) {
            facility.setAvailable(false);
            facilityRepository.updateFacility(facility);
            double totalCost = facility.getPricePerHour() * hours;
            System.out.println("Facility booked: " + facility.getFacilityId() + " - " + facility.getType());
            System.out.println("Hours: " + hours);
            System.out.println("Total cost: ₹" + totalCost);
            return true;
        }
        System.out.println("Facility " + facilityId + " is not available for booking.");
        return false;
    }

}