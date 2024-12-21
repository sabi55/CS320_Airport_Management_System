package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class ProjectGUI extends JFrame {
    private JFrame frame;

    public ProjectGUI() {
        showLoginScreen();
    }

    private void showLoginScreen() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new GridLayout(3, 2, 10, 10));

        // Add login fields
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JLabel errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setForeground(Color.RED);

        Map<String, String> users = new HashMap<>();
        users.put("admin", "administrator");
        users.put("employee1", "employee");
        users.put("customer1", "customer");

        Map<String, String> passwords = new HashMap<>();
        passwords.put("admin", "password");
        passwords.put("employee1", "password123");
        passwords.put("customer1", "customerpass");

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);
        loginFrame.add(errorLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (users.containsKey(username) && passwords.get(username).equals(password)) {
                    String role = users.get(username);
                    loginFrame.dispose();
                    showMainApplication(role);
                } else {
                    errorLabel.setText("Invalid username or password");
                }
            }
        });

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    private void showMainApplication(String role) {
        frame = new JFrame("Modules.Airport Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 708);

        JTabbedPane tabbedPane = new JTabbedPane();

        FlightManagementGUI flightManagementGUI = new FlightManagementGUI();
        JPanel flightTabPanel = flightManagementGUI.getPanel();
        tabbedPane.addTab("Modules.Flight Management System", null, flightTabPanel, "Manage Flights");

        PassengerManagementGUI passengerManagementGUI = new PassengerManagementGUI();
            JPanel passengerTabPanel = passengerManagementGUI.getPanel();
            tabbedPane.addTab("Modules.Passenger Management System", null, passengerTabPanel, "Manage Passengers");

        if (!role.equals("customer")) {
            PlaneManagementGUI planeManagementGUI = new PlaneManagementGUI();
            JPanel planeTabPanel = planeManagementGUI.getPanel();
            tabbedPane.addTab("Modules.Plane Management System", null, planeTabPanel, "Manage Planes");
        }

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
        	frame.dispose();
        	showLoginScreen();
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(logoutButton, BorderLayout.EAST);

        frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
