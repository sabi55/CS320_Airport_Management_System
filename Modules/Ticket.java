package Modules;

public abstract class Ticket {
	private AirportPassenger passenger;
	private int seatNumber;
	private double ticketPrice;
	
	public double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(double price) {
		this.ticketPrice = price;
	}
	public Ticket(int seatNumber) {
		super();
		this.seatNumber = seatNumber;
	}
	public AirportPassenger getPassenger() {
		return passenger;
	}
	public void setPassenger(AirportPassenger passenger) {
		this.passenger = passenger;
	}
	public int getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	public abstract double calculatePrice(double ticketPrice, AirportPassenger passenger2);
	
}
