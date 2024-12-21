package Systems;

import GUI.DatabaseConnection;
import Modules.Plane;

import java.sql.*;
import java.util.ArrayList;

public class PlaneManagementSystem {
	private ArrayList<Plane> planeList;

	public ArrayList<Plane> getPlaneList() {
		if (planeList == null) {
			planeList = new ArrayList<>();
			String selectPlanesSQL = "SELECT * FROM planes";
			try (Connection connection = DatabaseConnection.getConnection();
				 PreparedStatement preparedStatement = connection.prepareStatement(selectPlanesSQL);
				 ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					String planeId = resultSet.getString("plane_id");
					int capacity = resultSet.getInt("capacity");
					boolean available = resultSet.getBoolean("available");

					// Correct argument order
					Plane plane = new Plane(planeId, capacity, available);
					planeList.add(plane);

					// Debugging
					System.out.println("Loaded plane: " + planeId);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
		return planeList;
	}

	public void addPlane(String planeId, int capacity, boolean available) {
		if (planeList == null) {
			planeList = new ArrayList<>();
		}
		Plane plane = new Plane(planeId, capacity, available);
		planeList.add(plane);

		System.out.println("Plane added: " + planeId);
		System.out.println("Current planes: " + planeList);
	}

	public void addPlane(Plane plane) {
		if (planeList == null) {
			planeList = new ArrayList<>();
		}
		planeList.add(plane);

		System.out.println("Plane added: " + plane.getPlaneId());
		System.out.println("Current planes: " + planeList);
	}

	public void removePlane(Plane plane) {
		if (planeList != null) {
			planeList.remove(plane);
			// Debugging
			System.out.println("Plane removed: " + plane.getPlaneId());
			System.out.println("Current planes: " + planeList);
		}
	}
}