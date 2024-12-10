
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class FlightManagementController<T, E> implements ControllerInterface<Flight, FlightManagementSystem> {
	private FlightManagementSystem flightManagementSystem;

	public FlightManagementController(FlightManagementSystem fms) {

		this.flightManagementSystem = fms;
	}

	@Override
	public void deleteData(Integer index) {
		// TODO Auto-generated method stub
		ArrayList<Flight> flights = flightManagementSystem.getFlightList();
		flightManagementSystem.removeFlight(flights.get(index));

	}

	@Override
	public void addData(Flight flight) {
		// TODO Auto-generated method stub
		flightManagementSystem.addFlight(flight);
	}

	@Override
	public void updateData(Flight flight, Integer index) {
		// TODO Auto-generated method stub

	}

	@Override
	public DefaultTableModel refresh() {
		// TODO Auto-generated method stub
		String[] columnNames = { "Departure Location", "Landing Location", "Departure Date", "Landing Date",
				"Capacity" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		for (Flight flight : flightManagementSystem.getFlightList()) {
			model.addRow(new String[] { flight.getDepartureLocation(), flight.getLandingLocation(),
					dateFormat.format(flight.getDepartureDate()), dateFormat.format(flight.getLandingDate()),
					String.valueOf(flight.getCapacity()) });
		}
		return model;
	}

	public TableModel refreshTickets(Flight flight) {
		// TODO Auto-generated method stub
		String[] columnNames = { "Ticket Type", "Seat Number", "Passenger", "Ticket Price","Passenger Id"};
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		for (Ticket ticket : flight.getTicketList()) {
			if (ticket instanceof VIPTicket)
				model.addRow(new String[] { "VIP", String.valueOf(ticket.getSeatNumber()),
						ticket.getPassenger() != null
								? ticket.getPassenger().getName() + " " + ticket.getPassenger().getSurname()
								: "",
						String.valueOf(ticket.getTicketPrice()),ticket.getPassenger() != null?String.valueOf(ticket.getPassenger().getPassengerID()):"" });
			else
				model.addRow(new String[] { "Regular", String.valueOf(ticket.getSeatNumber()),
						ticket.getPassenger() != null
								? ticket.getPassenger().getName() + " " + ticket.getPassenger().getSurname()
								: "",
						String.valueOf(ticket.getTicketPrice()),ticket.getPassenger() != null?String.valueOf(ticket.getPassenger().getPassengerID()):"" });
		}
		return model;
	}

	public void buyTicket(Integer flightRow, Integer ticketRow, int passengerId, float ticketPrice) {

		Flight flight = flightManagementSystem.getFlightList().get(flightRow);
		Ticket ticket = flight.getTicketList()[ticketRow];
		AirportPassenger passenger = findPassenger(passengerId);
		if (passenger == null) {

			JOptionPane.showMessageDialog(null, "Passenger id does not exist", "Error", JOptionPane.ERROR_MESSAGE);

		} else {
			if (ticket instanceof VIPTicket && !(passenger instanceof BusinessPassenger)) {
				JOptionPane.showMessageDialog(null, "VIP Ticket can be sold to Business Passenger", "Error",
						JOptionPane.ERROR_MESSAGE);
			} else {
				ticket.setPassenger(passenger);
				double price = ticket.calculatePrice(ticketPrice, passenger);
				ticket.setTicketPrice(price);
			}

		}

	}

	private AirportPassenger findPassenger(int passengerId) {
		ArrayList<AirportPassenger> passengers = App.airportManagementSystem.passengerManagementSystem.getPassengerList();
		for (AirportPassenger passenger : passengers) {
			if (passenger.getPassengerID() == passengerId) {
				return passenger;
			}
		}
		return null;
	}

	public void cancelTicket(Integer flightRow, Integer ticketRow) {
		
		Flight flight = flightManagementSystem.getFlightList().get(flightRow);
		Ticket ticket = flight.getTicketList()[ticketRow];
		ticket.setPassenger(null);
		ticket.setTicketPrice(0.0);
	}

}
