package GUI;

import Controller.FlightManagementController;
import Modules.Flight;
import Systems.FlightManagementSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class FlightManagementGUI {

    private FlightManagementController<Flight, FlightManagementSystem> controller;
    private FlightManagementSystem flightManagementSystem;

    private JPanel panel;
    private JTable ticketsTable;
    private JTextField passengerId;
    private JTextField ticketPrice;
    private JTextField selectTicket;

    public FlightManagementGUI() {
        this.flightManagementSystem = new FlightManagementSystem();

        controller = new FlightManagementController<Flight, FlightManagementSystem>(flightManagementSystem);
        panel = new JPanel();

        // String input fields
        JLabel label1 = new JLabel("Departure");
        label1.setBounds(10, 0, 107, 28);
        JTextField departureLocation = new JTextField();
        departureLocation.setBounds(10, 33, 136, 28);
        JLabel label2 = new JLabel("Landing");
        label2.setBounds(156, 0, 116, 28);
        JTextField landingLocation = new JTextField();
        landingLocation.setBounds(156, 33, 136, 28);

        // Date input fields
        JLabel label3 = new JLabel("Departure Date (yyyy-MM-dd):");
        label3.setBounds(10, 69, 164, 28);
        JFormattedTextField departureDate = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        departureDate.setBounds(10, 105, 164, 28);
        JLabel label4 = new JLabel("Landing Date (yyyy-MM-dd):");
        label4.setBounds(208, 67, 158, 30);
        JFormattedTextField landingDate = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
        landingDate.setBounds(208, 105, 158, 28);

        // Integer input field
        JLabel label5 = new JLabel("Capacity:");
        label5.setBounds(302, 0, 72, 28);
        JSpinner capacity = new JSpinner();
        capacity.setSize(83, 28);
        capacity.setLocation(302, 33);

        JTextField selectRow = new JTextField();
        selectRow.setBounds(21, 190, 96, 19);
        selectRow.setVisible(false);

        // Create the table model
        String[] columnNames = { "Departure Location", "Landing Location", "Departure Date", "Landing Date", "Capacity" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Refresh the table data from the database
        refreshTable(model);

        // Create the table
        JTable table = new JTable(model);

        table.setBounds(426, 49, 329, 165);
        panel.add(table);

        // Submit button
        JButton addButton = new JButton("Add Modules.Flight");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date departDate = dateFormat.parse(departureDate.getText());
                    Date landDate = dateFormat.parse(landingDate.getText());
                    String insertFlightSQL = "INSERT INTO flights (departure, landing, capacity, departure_date, landing_date) VALUES (?, ?, ?, ?, ?)";
                    try (Connection connection = DatabaseConnection.getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement(insertFlightSQL)) {
                        preparedStatement.setString(1, departureLocation.getText());
                        preparedStatement.setString(2, landingLocation.getText());
                        preparedStatement.setInt(3, (int) capacity.getValue());
                        preparedStatement.setDate(4, new java.sql.Date(departDate.getTime()));
                        preparedStatement.setDate(5, new java.sql.Date(landDate.getTime()));
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(panel, "Modules.Flight added successfully!");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Error adding flight: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    refreshTable(model);
                    departureLocation.setText("");
                    landingLocation.setText("");
                    departureDate.setText("");
                    landingDate.setText("");
                    capacity.setValue(0);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

            }
        });

        addButton.setBounds(10, 143, 164, 33);
        panel.setLayout(null);

        // Add components to the panel
        panel.add(label1);
        panel.add(departureLocation);
        panel.add(label2);
        panel.add(landingLocation);
        panel.add(label3);
        panel.add(departureDate);
        panel.add(label4);
        panel.add(landingDate);
        panel.add(label5);
        panel.add(capacity);
        panel.add(addButton);
        panel.add(selectRow);

        JButton removeButton = new JButton("Remove Modules.Flight");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String flightIdStr = selectRow.getText();
                if (flightIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please select a flight to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int flightId = Integer.parseInt(flightIdStr);
                String deleteFlightSQL = "DELETE FROM flights WHERE id = ?";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(deleteFlightSQL)) {
                    preparedStatement.setInt(1, flightId);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(panel, "Modules.Flight deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(panel, "Modules.Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error deleting flight: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                refreshTable(model);
                selectRow.setText("");
            }
        });

        removeButton.setBounds(208, 143, 164, 33);
        panel.add(removeButton);

        ticketsTable = new JTable();
        ticketsTable.setBounds(797, 49, 268, 165);
        panel.add(ticketsTable);

        JLabel lblNewLabel = new JLabel("Current Flights");
        lblNewLabel.setBounds(426, 20, 123, 19);
        panel.add(lblNewLabel);

        JLabel lblManageTickets = new JLabel("Manage Tickets");
        lblManageTickets.setBounds(797, 20, 123, 19);
        panel.add(lblManageTickets);

        JLabel lblNewLabel_1 = new JLabel("Modules.Passenger ID");
        lblNewLabel_1.setBounds(797, 239, 96, 19);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Modules.Ticket Price");
        lblNewLabel_1_1.setBounds(907, 239, 96, 19);
        panel.add(lblNewLabel_1_1);

        passengerId = new JTextField();
        passengerId.setBounds(797, 269, 96, 28);
        panel.add(passengerId);
        passengerId.setColumns(10);

        ticketPrice = new JTextField();
        ticketPrice.setColumns(10);
        ticketPrice.setBounds(907, 268, 96, 28);
        panel.add(ticketPrice);

        JButton btnBuyTicket = new JButton("Buy Modules.Ticket");
        btnBuyTicket.setBounds(797, 324, 100, 21);
        panel.add(btnBuyTicket);
        btnBuyTicket.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String flightIdStr = selectRow.getText();
                String passengerIdStr = passengerId.getText();
                String ticketPriceStr = ticketPrice.getText();

                if (flightIdStr.isEmpty() || passengerIdStr.isEmpty() || ticketPriceStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int flightId = Integer.parseInt(flightIdStr);
                int passengerId = Integer.parseInt(passengerIdStr);
                float price = Float.parseFloat(ticketPriceStr);

                String insertTicketSQL = "INSERT INTO tickets (flight_id, passenger_id, price) VALUES (?, ?, ?)";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(insertTicketSQL)) {
                    preparedStatement.setInt(1, flightId);
                    preparedStatement.setInt(2, passengerId);
                    preparedStatement.setFloat(3, price);
                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(panel, "Modules.Ticket purchased successfully!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error buying ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton btnCancelTicket = new JButton("Cancel Modules.Ticket");
        btnCancelTicket.setBounds(907, 324, 116, 21);
        panel.add(btnCancelTicket);
        btnCancelTicket.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String ticketIdStr = selectTicket.getText();
                if (ticketIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please select a ticket to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int ticketId = Integer.parseInt(ticketIdStr);
                String deleteTicketSQL = "DELETE FROM tickets WHERE id = ?";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(deleteTicketSQL)) {
                    preparedStatement.setInt(1, ticketId);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(panel, "Modules.Ticket canceled successfully!");
                    } else {
                        JOptionPane.showMessageDialog(panel, "Modules.Ticket not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error canceling ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        selectTicket = new JTextField();
        selectTicket.setBounds(927, 20, 96, 19);
        panel.add(selectTicket);
        selectTicket.setColumns(10);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        // Get the values from the selected row
                        String deptLocation = (String) table.getValueAt(selectedRow, 0);
                        String landLocation = (String) table.getValueAt(selectedRow, 1);
                        String deptDate = (String) table.getValueAt(selectedRow, 2);
                        String landDate = (String) table.getValueAt(selectedRow, 3);
                        int cap = Integer.parseInt(table.getValueAt(selectedRow, 4).toString());

                        // Update the input fields with the selected values
                        departureLocation.setText(deptLocation);
                        landingLocation.setText(landLocation);
                        departureDate.setText(deptDate);
                        landingDate.setText(landDate);
                        capacity.setValue(cap);
                        selectRow.setText(String.valueOf(selectedRow + 1));
                    }
                }
            }
        });

        ticketsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedTicket = ticketsTable.getSelectedRow();
                    if (selectedTicket >= 0) {
                        // Get the values from the selected row
                        selectTicket.setText(String.valueOf(selectedTicket));
                        passengerId.setText(ticketsTable.getValueAt(selectedTicket, 1).toString());
                    }
                }
            }
        });
    }

    public JPanel getPanel() {
        return this.panel;
    }

    private void refreshTable(DefaultTableModel model) {
        model.setRowCount(0);
        String selectFlightsSQL = "SELECT * FROM flights";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectFlightsSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String departure = resultSet.getString("departure");
                String landing = resultSet.getString("landing");
                String departureDate = resultSet.getString("departure_date");
                String landingDate = resultSet.getString("landing_date");
                int capacity = resultSet.getInt("capacity");
                model.addRow(new Object[]{departure, landing, departureDate, landingDate, capacity});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error loading flights: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
