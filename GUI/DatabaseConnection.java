package GUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/airportmanagementsystem?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

    private static final String USER = "new_user";
    private static final String PASSWORD = "123456";


    public static Connection getConnection() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Connection to MySQL failed.");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection connection = getConnection();
        if (connection != null) {
            System.out.println("Connected to the database successfully!");

            try (Statement statement = connection.createStatement()) {

                String createTableSQL = "CREATE TABLE IF NOT EXISTS passengers ("
                        + "id INT AUTO_INCREMENT PRIMARY KEY, "
                        + "name VARCHAR(100), "
                        + "passport_number VARCHAR(50))";
                statement.execute(createTableSQL);
                System.out.println("Table 'passengers' created or already exists.");

                String insertDataSQL = "INSERT INTO passengers (name, passport_number) "
                        + "VALUES ('John Doe', 'A1234567'), "
                        + "('Jane Smith', 'B7654321')";
                statement.executeUpdate(insertDataSQL);
                System.out.println("Data inserted into 'passengers' table.");

                String querySQL = "SELECT * FROM passengers";
                ResultSet resultSet = statement.executeQuery(querySQL);

                System.out.println("Querying 'passengers' table:");
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String passportNumber = resultSet.getString("passport_number");
                    System.out.printf("ID: %d, Name: %s, Passport Number: %s%n", id, name, passportNumber);
                }



            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    connection.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }
}
