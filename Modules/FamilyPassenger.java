package Modules;

public class FamilyPassenger extends AirportPassenger {

	public static double discount=0.85;
	
	public FamilyPassenger(String name, String surname, int luggageCount, int yearOfBirth) {
		super(name, surname, luggageCount, yearOfBirth);
	}

	public double getDiscount() {
		return discount;
	}
	
}
