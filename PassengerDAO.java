import GUI.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PassengerDAO {
    public void addPassenger(String firstName, String lastName, String passportNumber, String passengerType) {
        String insertPassengerSQL = "INSERT INTO Modules.Passenger (first_name, last_name, passport_number, passenger_type) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertPassengerSQL)) {

            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, passportNumber);
            preparedStatement.setString(4, passengerType);

            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

        } catch (SQLException e) {
            System.out.println("Failed to insert passenger.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        PassengerDAO passengerDAO = new PassengerDAO();
        passengerDAO.addPassenger("John", "Doe", "AB1234567", "Economy");
    }
}
