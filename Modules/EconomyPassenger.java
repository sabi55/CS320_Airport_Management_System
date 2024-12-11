package Modules;

public class EconomyPassenger extends AirportPassenger {

	public static double discount=0.9;
	
	public EconomyPassenger(String name, String surname, int luggageCount, int yearOfBirth) {
		super(name, surname, luggageCount, yearOfBirth);
	}

	public double getDiscount() {
		return discount;
	}
	
	
}
