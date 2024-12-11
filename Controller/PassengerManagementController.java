package Controller;

import Modules.AirportPassenger;
import Systems.PassengerManagementSystem;

import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class PassengerManagementController<T, E> implements ControllerInterface<AirportPassenger, PassengerManagementSystem> {
    private PassengerManagementSystem passengerManagementSystem;

    public PassengerManagementController(PassengerManagementSystem pms) {

        this.passengerManagementSystem = pms;
    }

    @Override
    public void deleteData(Integer index) {
        // TODO Auto-generated method stub
        ArrayList<AirportPassenger> passengers = passengerManagementSystem.getPassengerList();
        passengerManagementSystem.removePassenger(passengers.get(index));

    }


    @Override
    public void updateData(AirportPassenger passenger, Integer index) {
        // TODO Auto-generated method stub
        ArrayList<AirportPassenger> passengers = passengerManagementSystem.getPassengerList();
        passengers.set(index, passenger);

    }

    @Override
    public DefaultTableModel refresh() {
        // TODO Auto-generated method stub
        String[] columnNames = { "Modules.Passenger ID","Name", "Surname", "Modules.Passenger Type", "Luggage Count",
                "Year of Birth" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (AirportPassenger passenger: passengerManagementSystem.getPassengerList()) {
            String type=getPassengerType(passenger);

            model.addRow(new String[] { String.valueOf(passenger.getPassengerID()),passenger.getName(), passenger.getSurname(),getPassengerType(passenger),
                    String.valueOf(passenger.getLuggageCount()),String.valueOf(passenger.getYearOfBirth()) });
        }
        return model;
    }

    private String getPassengerType(AirportPassenger passenger) {
        if (passenger.getClass().equals(BusinessPassenger.class)) {
            return("Business");
        }
        if (passenger.getClass().equals(EconomyPassenger.class)) {
            return("Economy");
        }
        if (passenger.getClass().equals(FamilyPassenger.class)) {
            return("Family");
        }
        return "";
    }

    @Override
    public void addData(AirportPassenger passenger) {
        // TODO Auto-generated method stub
        passengerManagementSystem.addPassenger(passenger);

    }



}
