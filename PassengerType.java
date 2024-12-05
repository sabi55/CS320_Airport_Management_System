import java.util.Date;

public class Passenger {
  private int id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private Integer bookingId; 
    private Integer baggageCount; 

    public Passenger(int id, String name, String surname, Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.bookingId = 0;
        this.baggageCount = 0;
    }
  
    public int getPassengerId() {
        return id;
    }

    public void setPassengerId(int id) {
        this.id = id;
    }

    public String getPassengerName() {
        return name;
    }

    public void setPassengerName(String name) {
        this.name = name;
    }

    public String getPassengerSurname() {
        return surname;
    }

    public void setPassengerSurname(String surname) {
        this.surname = surname;
    }

    public Date getPassengerDateOfBirth() {
        return dateOfBirth;
    }

    public void setPassengerDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getPassengerBookingId() {
        return bookingId;
    }

    public void setPassengerBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getPassengerBaggageCount() {
        return baggageCount;
    }

    public void setPassengerBaggageCount(Integer baggageCount) {
        this.baggageCount = baggageCount;
    }
}
