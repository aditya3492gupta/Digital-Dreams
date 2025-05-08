package entity;

public class Transportation {
	private String vehicleId;
	private String vehicleType; // e.g., "2-wheeler" or "4-wheeler"
	private double cost;
	private boolean available;
	private int noOfDays;

	public Transportation(String vehicleId, String vehicleType, double cost, boolean available) {
		this.vehicleId = vehicleId;
		this.vehicleType = vehicleType;
		this.cost = cost;
		this.available = available;
	}

	public Transportation(String vehicleId, String vehicleType, double cost, boolean available,int noOfDays) {
		this.vehicleId = vehicleId;
		this.vehicleType = vehicleType;
		this.cost = cost;
		this.available = available;
		this.noOfDays=noOfDays;
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

	public int getNoOfDays() {
		return this.noOfDays;
	}

	public void setAvailable(int noOfDays) {
		this.noOfDays=noOfDays;
	}


	@Override
	public String toString() {
		return "ID: " + vehicleId +
				", Type: " + vehicleType +
				", Cost: " + cost +
				", Available: " + available;
	}
}
