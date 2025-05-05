package entity;

public class Transportation {

	private String VehicleId;
	private String type;
	private boolean isAvailable;

	public Transportation(String VehicleId, String type, boolean isAvailable) {
		this.VehicleId = VehicleId;
		this.type = type;
		this.isAvailable = isAvailable;

	}

	public String getVehicleId() {
		return VehicleId;

	}

	public void setVehicleId(String VehicleId) {
		this.VehicleId = VehicleId;
	}

	public String getType() {
		return type;

	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean getAvailable() {
		return isAvailable;

	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	@Override
	public String toString() {
		return "Transportation [VehicleId=" + VehicleId + ", type=" + type + ", isAvailable=" + isAvailable + "]";
	}

}
