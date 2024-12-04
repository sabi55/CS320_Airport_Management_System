public class Plane {
	private String planeId;
	private int capacity;
	private boolean isAvailable;

	public Plane(String planeId, int capacity, boolean isAvailable) {
		super();
		this.planeId = planeId;
		this.capacity = capacity;
		this.isAvailable = isAvailable;
	}

	public String getPlaneId() {
		return planeId;
	}

	public void setPlaneId(String planeId) {
		this.planeId = planeId;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
