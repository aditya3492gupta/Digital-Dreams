package entity;

public class Transportation {
	private String vehicleId;
	private String vehicleType; // e.g., "2-wheeler" or "4-wheeler"
	private double cost;
	private boolean available;

	public Transportation(String vehicleId, String vehicleType, double cost, boolean available) {
		this.vehicleId = vehicleId;
		this.vehicleType = vehicleType;
		this.cost = cost;
		this.available = available;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public double getCost() {
		return cost;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "ID: " + vehicleId +
				", Type: " + vehicleType +
				", Cost: " + cost +
				", Available: " + available;
	}
}
