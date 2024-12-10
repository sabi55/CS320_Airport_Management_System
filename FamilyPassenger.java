public class FamilyPassenger extends AirportPassenger {

	private static double discount=0.85;
	
	public FamilyPassenger(String name, String surname, int luggageCount, int yearOfBirth) {
		super(name, surname, luggageCount, yearOfBirth);
	}

	public double getDiscount() {
		return discount;
	}
	
}
