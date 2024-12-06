import java.util.ArrayList;

public class PassengerManagementSystem {
    private ArrayList<AirportPassenger> passengerList;



    public PassengerManagementSystem() {
        passengerList=new ArrayList<>();
    }

    public ArrayList<AirportPassenger> getPassengerList() {
        return passengerList;
    }

    public void addPassenger(AirportPassenger passenger) {
        passengerList.add(passenger);
    }

    public void removePassenger(AirportPassenger passenger) {
        passengerList.remove(passenger);
    }

}
