package Systems;

import Modules.Plane;

import java.util.ArrayList;

public class PlaneManagementSystem {
	private ArrayList<Plane> planeList;

	
	
	public PlaneManagementSystem() {
		planeList=new ArrayList<>();
	}

	public ArrayList<Plane> getPlaneList() {
		return planeList;
	}
	
	public void addPlane(Plane plane) {
		planeList.add(plane);
	}
	public void removePlane(Plane plane) {
		planeList.remove(plane);
	}
	
}

