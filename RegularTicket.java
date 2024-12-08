
public class RegularTicket extends Ticket{

    public RegularTicket(int seatNumber) {
        super(seatNumber);

    }

    @Override
    protected double calculatePrice(double ticketPrice,AirportPassenger passenger) {
        return ticketPrice*passenger.getDiscount();

    }

}
