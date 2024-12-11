package Modules;

public class BusinessPassenger extends AirportPassenger {

    private static double discount=1.1;

    public BusinessPassenger(String name, String surname, int luggageCount, int yearOfBirth) {
        super(name, surname, luggageCount, yearOfBirth);
    }

    public double getDiscount() {
        return discount;
    }


}