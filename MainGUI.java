import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI {

    private JFrame frame;
    private AirportManagementSystem airportManagementSystem;

    public MainGUI() {
        // Initialize the AirportManagementSystem from the App class
        airportManagementSystem = App.airportManagementSystem;

        // Create and display the GUI
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Create the main frame
        frame = new JFrame("Airport Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Title label
        JLabel titleLabel = new JLabel("Airport Management System", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 2, 10, 10));

        JButton addHangarButton = new JButton("Add Hangar");
        JButton removeHangarButton = new JButton("Remove Hangar");
        JButton addRunwayButton = new JButton("Add Runway");
        JButton removeRunwayButton = new JButton("Remove Runway");
        JButton reserveHangarButton = new JButton("Reserve Hangar");
        JButton reserveRunwayButton = new JButton("Reserve Runway");

        buttonPanel.add(addHangarButton);
        buttonPanel.add(removeHangarButton);
        buttonPanel.add(addRunwayButton);
        buttonPanel.add(removeRunwayButton);
        buttonPanel.add(reserveHangarButton);
        buttonPanel.add(reserveRunwayButton);

        frame.add(buttonPanel, BorderLayout.CENTER);

        // Action listeners for buttons
        addHangarButton.addActionListener(e -> {
            airportManagementSystem.addHangar();
            JOptionPane.showMessageDialog(frame, "Hangar added successfully.");
        });

        removeHangarButton.addActionListener(e -> {
            String hangarId = JOptionPane.showInputDialog(frame, "Enter Hangar ID to remove:");
            if (hangarId != null) {
                airportManagementSystem.removeHangar(hangarId);
            }
        });

        addRunwayButton.addActionListener(e -> {
            airportManagementSystem.addRunway();
            JOptionPane.showMessageDialog(frame, "Runway added successfully.");
        });

        removeRunwayButton.addActionListener(e -> {
            String runwayId = JOptionPane.showInputDialog(frame, "Enter Runway ID to remove:");
            if (runwayId != null) {
                airportManagementSystem.removeRunway(runwayId);
            }
        });

        reserveHangarButton.addActionListener(e -> {
            String hangarId = JOptionPane.showInputDialog(frame, "Enter Hangar ID to reserve:");
            String planeId = JOptionPane.showInputDialog(frame, "Enter Plane ID to assign:");
            if (hangarId != null && planeId != null) {
                airportManagementSystem.reserveHangar(new PlaneManagementSystem(), hangarId, planeId);
            }
        });

        reserveRunwayButton.addActionListener(e -> {
            String runwayId = JOptionPane.showInputDialog(frame, "Enter Runway ID to reserve:");
            String planeId = JOptionPane.showInputDialog(frame, "Enter Plane ID to assign:");
            if (runwayId != null && planeId != null) {
                airportManagementSystem.reserveRunway(new PlaneManagementSystem(), runwayId, planeId);
            }
        });

        // Display the frame
        frame.setVisible(true);
    }
}
