
public abstract class AirportPassenger {
    private static int idCounter = 0;
    private static double discount;
    private int passengerID;
    private String name;
    private String surname;
    private int luggageCount;
    private int yearOfBirth;


    public AirportPassenger(String name, String surname, int luggageCount, int yearOfBirth) {
        super();
        this.passengerID=++idCounter;
        this.name = name;
        this.surname = surname;
        this.luggageCount = luggageCount;
        this.yearOfBirth = yearOfBirth;
    }
    public static int getIdCounter() {
        return idCounter;
    }
    public int getPassengerID() {
        return passengerID;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public int getLuggageCount() {
        return luggageCount;
    }
    public void setLuggageCount(int luggageCount) {
        this.luggageCount = luggageCount;
    }
    public int getYearOfBirth() {
        return yearOfBirth;
    }
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
    abstract double getDiscount();


}
