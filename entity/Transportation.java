package entity;

public class Transportation {
	private String vehicleId;
	private String vehicleType;
	private boolean available;

	public Transportation(String vehicleId, String vehicleType, boolean available) {
		this.vehicleId = vehicleId;
		this.vehicleType = vehicleType;
		this.available = available;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "ID: " + vehicleId + ", Type: " + vehicleType + ", Available: " + available;
	}
}