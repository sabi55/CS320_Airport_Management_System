package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class PassengerManagementGUI {

    private JPanel panel;
    private JTextField luggageCount;
    private JTextField yearOfBirth;

    public PassengerManagementGUI() {
        panel = new JPanel();
        panel.setLayout(null);

        // String input fields
        JLabel label1 = new JLabel("Name");
        label1.setBounds(10, 234, 107, 28);
        JTextField name = new JTextField();
        name.setBounds(10, 267, 171, 28);
        JLabel label2 = new JLabel("Surname");
        label2.setBounds(191, 234, 116, 28);
        JTextField surname = new JTextField();
        surname.setBounds(191, 267, 201, 28);

        JLabel lblPassengerType = new JLabel("Modules.Passenger Type");
        lblPassengerType.setBounds(418, 234, 116, 28);
        panel.add(lblPassengerType);

        JLabel lblYearOfBirth = new JLabel("Year of Birth");
        lblYearOfBirth.setBounds(757, 234, 116, 28);
        panel.add(lblYearOfBirth);

        JComboBox<String> passengerType = new JComboBox<>();
        passengerType.setModel(new DefaultComboBoxModel<>(new String[]{"Business", "Economy", "Family"}));
        passengerType.setBounds(416, 270, 171, 25);
        panel.add(passengerType);

        JLabel label5 = new JLabel("Luggage Count");
        label5.setBounds(605, 234, 116, 28);

        JTextField selectRow = new JTextField();
        selectRow.setVisible(false);
        selectRow.setBounds(21, 424, 96, 19);

        // Create the table model
        String[] columnNames = {"Modules.Passenger ID", "Name", "Surname", "Modules.Passenger Type", "Luggage Count", "Year of Birth"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Create the table
        JTable table = new JTable(model);
        table.setBounds(40, 25, 954, 165);

        refreshTable(model);

        // Submit button
        JButton addButton = new JButton("Add User");
        addButton.setBounds(17, 329, 164, 33);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Integer.parseInt(yearOfBirth.getText()) < 1900 || Integer.parseInt(yearOfBirth.getText()) > 2023) {
                    JOptionPane.showMessageDialog(null, "Please check Year of Birth", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String insertPassengerSQL = "INSERT INTO passengers (name, surname, passenger_type, luggage_count, year_of_birth) VALUES (?, ?, ?, ?, ?)";
                    try (Connection connection = DatabaseConnection.getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement(insertPassengerSQL)) {
                        preparedStatement.setString(1, name.getText());
                        preparedStatement.setString(2, surname.getText());
                        preparedStatement.setString(3, (String) passengerType.getSelectedItem());
                        preparedStatement.setInt(4, Integer.parseInt(luggageCount.getText()));
                        preparedStatement.setInt(5, Integer.parseInt(yearOfBirth.getText()));
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(panel, "Modules.Passenger added successfully!");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Error adding passenger: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    refreshTable(model);
                    name.setText("");
                    surname.setText("");
                    passengerType.setSelectedIndex(0);
                    luggageCount.setText("");
                    yearOfBirth.setText("");
                }
            }
        });

        JButton removeButton = new JButton("Remove User");
        removeButton.setBounds(201, 329, 164, 33);
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passengerIdStr = selectRow.getText();
                if (passengerIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please select a passenger to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String deletePassengerSQL = "DELETE FROM passengers WHERE passenger_id = ?";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(deletePassengerSQL)) {
                    preparedStatement.setInt(1, Integer.parseInt(passengerIdStr));
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(panel, "Modules.Passenger removed successfully!");
                    } else {
                        JOptionPane.showMessageDialog(panel, "Modules.Passenger not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error removing passenger: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                refreshTable(model);
                name.setText("");
                surname.setText("");
                passengerType.setSelectedIndex(0);
                luggageCount.setText("");
                yearOfBirth.setText("");
                selectRow.setText("");
            }
        });

        JButton updateButton = new JButton("Update User");
        updateButton.setBounds(406, 329, 164, 33);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String passengerIdStr = selectRow.getText();
                if (passengerIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please select a passenger to update.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (Integer.parseInt(yearOfBirth.getText()) < 1900 || Integer.parseInt(yearOfBirth.getText()) > 2023) {
                    JOptionPane.showMessageDialog(null, "Please check Year of Birth", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String updatePassengerSQL = "UPDATE passengers SET name = ?, surname = ?, passenger_type = ?, luggage_count = ?, year_of_birth = ? WHERE passenger_id = ?";
                    try (Connection connection = DatabaseConnection.getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement(updatePassengerSQL)) {
                        preparedStatement.setString(1, name.getText());
                        preparedStatement.setString(2, surname.getText());
                        preparedStatement.setString(3, (String) passengerType.getSelectedItem());
                        preparedStatement.setInt(4, Integer.parseInt(luggageCount.getText()));
                        preparedStatement.setInt(5, Integer.parseInt(yearOfBirth.getText()));
                        preparedStatement.setInt(6, Integer.parseInt(passengerIdStr));
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(panel, "Modules.Passenger updated successfully!");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Error updating passenger: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    refreshTable(model);
                    name.setText("");
                    surname.setText("");
                    passengerType.setSelectedIndex(0);
                    luggageCount.setText("");
                    yearOfBirth.setText("");
                    selectRow.setText("");
                }
            }
        });

        panel.add(addButton);
        panel.add(removeButton);
        panel.add(updateButton);
        panel.add(label1);
        panel.add(name);
        panel.add(label2);
        panel.add(surname);
        panel.add(label5);
        panel.add(table);
        panel.add(selectRow);

        luggageCount = new JTextField();
        luggageCount.setBounds(597, 267, 116, 28);
        panel.add(luggageCount);
        luggageCount.setColumns(10);

        yearOfBirth = new JTextField();
        yearOfBirth.setColumns(10);
        yearOfBirth.setBounds(757, 266, 116, 28);
        panel.add(yearOfBirth);

        JPanel panel_1 = new JPanel();
        panel_1.setBorder(BorderFactory.createTitledBorder("All Passengers"));
        panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_1.setBounds(21, 10, 992, 196);
        panel.add(panel_1);

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        String passengerId = model.getValueAt(selectedRow, 0).toString();
                        String n = model.getValueAt(selectedRow, 1).toString();
                        String s = model.getValueAt(selectedRow, 2).toString();
                        String pt = model.getValueAt(selectedRow, 3).toString();
                        String lc = model.getValueAt(selectedRow, 4).toString();
                        String yob = model.getValueAt(selectedRow, 5).toString();

                        name.setText(n);
                        surname.setText(s);
                        passengerType.setSelectedItem(pt);
                        luggageCount.setText(lc);
                        yearOfBirth.setText(yob);
                        selectRow.setText(passengerId);
                    }
                }
            }
        });
    }

    private void refreshTable(DefaultTableModel model) {
        model.setRowCount(0);
        String selectPassengersSQL = "SELECT * FROM passengers";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectPassengersSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int passengerId = resultSet.getInt("passenger_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String passengerType = resultSet.getString("passenger_type");
                int luggageCount = resultSet.getInt("luggage_count");
                int yearOfBirth = resultSet.getInt("year_of_birth");
                model.addRow(new Object[]{passengerId, name, surname, passengerType, luggageCount, yearOfBirth});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error loading passengers: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}