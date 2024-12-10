public class EconomyPassenger extends AirportPassenger {

	private static double discount=0.9;
	
	public EconomyPassenger(String name, String surname, int luggageCount, int yearOfBirth) {
		super(name, surname, luggageCount, yearOfBirth);
	}

	public double getDiscount() {
		return discount;
	}
	
	
}
