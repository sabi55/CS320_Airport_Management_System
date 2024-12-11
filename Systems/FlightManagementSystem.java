package Systems;

import Modules.Flight;

import java.util.ArrayList;

public class FlightManagementSystem {
	private ArrayList<Flight> flightList;

	
	
	public FlightManagementSystem() {
		flightList=new ArrayList<>();
	}

	public ArrayList<Flight> getFlightList() {
		return flightList;
	}
	
	public void addFlight(Flight flight) {
		flightList.add(flight);
	}
	public void removeFlight(Flight flight) {
		flightList.remove(flight);
	}

	
}

