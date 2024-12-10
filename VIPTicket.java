public class VIPTicket extends Ticket{

	
	
	public VIPTicket(int seatNumber) {
		super(seatNumber);
	
	}

	@Override
	protected double calculatePrice(double ticketPrice,AirportPassenger passenger) {
		// TODO Auto-generated method stub
		return  (ticketPrice*passenger.getDiscount());
	}

	
	

}
