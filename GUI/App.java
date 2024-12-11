package GUI;

import Systems.AirportManagementSystem;

import javax.swing.SwingUtilities;

public class App {
	static AirportManagementSystem airportManagementSystem;
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static void createAndShowGUI() throws Exception {
    	airportManagementSystem = new AirportManagementSystem();
    	new ProjectGUI();
    }
}
