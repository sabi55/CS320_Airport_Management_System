package GUI;

import Controller.PlaneManagementController;
import Modules.Hangar;
import Modules.Plane;
import Modules.Runway;
import Systems.AirportManagementSystem;
import Systems.PlaneManagementSystem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;

public class PlaneManagementGUI {

    private PlaneManagementController<Plane, PlaneManagementSystem> controller;
    private AirportManagementSystem airportManagementSystem;
    private PlaneManagementSystem planeManagementSystem;
    private JPanel panel;
    private JTextField hangerAirport;
    private JTextField runwayAirport;

    public PlaneManagementGUI() {
        this.planeManagementSystem = new PlaneManagementSystem();
        this.airportManagementSystem = App.airportManagementSystem;
        controller = new PlaneManagementController<Plane, PlaneManagementSystem>(planeManagementSystem);
        panel = new JPanel();

        // String input fields
        JLabel label1 = new JLabel("Modules.Plane ID");
        label1.setBounds(444, 10, 107, 28);
        JTextField planeId = new JTextField();
        planeId.setBounds(444, 43, 136, 28);

        // Integer input field
        JLabel label5 = new JLabel("Capacity:");
        label5.setBounds(612, 10, 72, 28);
        JSpinner capacity = new JSpinner();
        capacity.setBounds(612, 43, 72, 28);

        JTextField selectRow = new JTextField();
        selectRow.setVisible(false);
        selectRow.setBounds(428, 138, 96, 19);

        // Create the table model
        String[] columnNames = { "Modules.Plane ID", "Available", "Capacity" };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        refreshTable(model);
        // Create the table
        JTable table = new JTable(model);

        table.setBounds(10, 10, 329, 165);
        panel.add(table);

        // Submit button
        JButton addButton = new JButton("Add Modules.Plane");
        addButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if ((int) capacity.getValue() <= 0) {
                    JOptionPane.showMessageDialog(null, "Capacity must be greater than zero!", "Error",
                            JOptionPane.ERROR_MESSAGE);

                } else {
                    String insertPlaneSQL = "INSERT INTO planes (plane_id, capacity, available) VALUES (?, ?, ?)";
                    try (Connection connection = DatabaseConnection.getConnection();
                         PreparedStatement preparedStatement = connection.prepareStatement(insertPlaneSQL)) {
                        preparedStatement.setString(1, planeId.getText());
                        preparedStatement.setInt(2, (int) capacity.getValue());
                        preparedStatement.setBoolean(3, true);
                        preparedStatement.executeUpdate();
                        JOptionPane.showMessageDialog(panel, "Modules.Plane added successfully!");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Error adding plane: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    refreshTable(model);
                    planeId.setText("");
                    capacity.setValue(0);
                }

            }
        });

        addButton.setBounds(417, 91, 164, 33);
        panel.setLayout(null);

        // Add components to the panel
        panel.add(label1);
        panel.add(planeId);
        panel.add(label5);
        panel.add(capacity);
        panel.add(addButton);
        panel.add(selectRow);

        JButton removeButton = new JButton("Remove Modules.Plane");
        removeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String planeIdStr = selectRow.getText();
                if (planeIdStr.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please select a plane to remove.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String deletePlaneSQL = "DELETE FROM planes WHERE plane_id = ?";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(deletePlaneSQL)) {
                    preparedStatement.setString(1, planeIdStr);
                    int rowsAffected = preparedStatement.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(panel, "Modules.Plane deleted successfully!");
                    } else {
                        JOptionPane.showMessageDialog(panel, "Modules.Plane not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error deleting plane: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }

                refreshTable(model);
                planeId.setText("");
                capacity.setValue(0);
                selectRow.setText("");

            }
        });

        removeButton.setBounds(615, 91, 164, 33);
        panel.add(removeButton);

        JLabel lblNewLabel_1 = new JLabel("Modules.Hangar Status");
        lblNewLabel_1.setBounds(10, 358, 107, 13);
        panel.add(lblNewLabel_1);

        JTextArea hangarStatus = new JTextArea();
        hangarStatus.setBounds(20, 381, 344, 199);
        panel.add(hangarStatus);

        setHangarStatusArea(hangarStatus, airportManagementSystem.getHangars());
        JLabel lblNewLabel_1_1 = new JLabel("Modules.Runway Status");
        lblNewLabel_1_1.setBounds(479, 358, 107, 13);
        panel.add(lblNewLabel_1_1);

        JTextArea runwayStatus = new JTextArea();
        runwayStatus.setBounds(489, 381, 344, 199);
        panel.add(runwayStatus);

        setStatusArea(runwayStatus, airportManagementSystem.getRunways());


        JLabel lblNewLabel = new JLabel("Modules.Hangar Id");
        lblNewLabel.setBounds(10, 234, 45, 13);
        panel.add(lblNewLabel);

        hangerAirport = new JTextField();
        hangerAirport.setBounds(10, 249, 107, 28);
        panel.add(hangerAirport);
        hangerAirport.setColumns(10);

        runwayAirport = new JTextField();
        runwayAirport.setColumns(10);
        runwayAirport.setBounds(10, 302, 107, 28);
        panel.add(runwayAirport);

        JButton btnAddHanger = new JButton("Add Hanger");
        btnAddHanger.setBounds(159, 249, 120, 33);
        panel.add(btnAddHanger);

        btnAddHanger.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                airportManagementSystem.addHangar();
                setHangarStatusArea(hangarStatus, airportManagementSystem.getHangars());
            }
        });

        JButton btnRemoveHanger = new JButton("Remove Hanger");
        btnRemoveHanger.setBounds(296, 249, 154, 33);
        panel.add(btnRemoveHanger);

        btnRemoveHanger.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                airportManagementSystem.removeHangar(hangerAirport.getText());
                setHangarStatusArea(hangarStatus, airportManagementSystem.getHangars());
            }
        });

        JButton btnAddRunway = new JButton("Add Modules.Runway");
        btnAddRunway.setBounds(159, 299, 120, 33);
        panel.add(btnAddRunway);
        btnAddRunway.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                airportManagementSystem.addRunway();
                setStatusArea(runwayStatus, airportManagementSystem.getRunways());
            }
        });
        JButton btnRemoveRunway = new JButton("Remove Modules.Runway");
        btnRemoveRunway.setBounds(296, 299, 154, 33);
        panel.add(btnRemoveRunway);

        btnRemoveRunway.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                airportManagementSystem.removeRunway(runwayAirport.getText());
                setStatusArea(runwayStatus, airportManagementSystem.getRunways());
            }
        });
        JButton btnReserveHanger = new JButton("Reserve Hanger");
        btnReserveHanger.setBounds(479, 249, 144, 33);
        panel.add(btnReserveHanger);

        btnReserveHanger.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                airportManagementSystem.reserveHangar(planeManagementSystem, hangerAirport.getText(),
                        selectRow.getText());
                setHangarStatusArea(hangarStatus, airportManagementSystem.getHangars());

            }
        });

        JButton btnResetHanger = new JButton("Reset Hanger");
        btnResetHanger.setBounds(643, 247, 136, 33);
        panel.add(btnResetHanger);

        btnResetHanger.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                airportManagementSystem.resetHangar(planeManagementSystem,hangerAirport.getText());
                setHangarStatusArea(hangarStatus, airportManagementSystem.getHangars());
            }
        });

        JButton btnReserveRunway = new JButton("Reserve Modules.Runway");
        btnReserveRunway.setBounds(479, 299, 144, 33);
        panel.add(btnReserveRunway);

        btnReserveRunway.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                airportManagementSystem.reserveRunway(planeManagementSystem, runwayAirport.getText(),
                        selectRow.getText());
                setStatusArea(runwayStatus, airportManagementSystem.getRunways());

            }
        });

        JButton btnResetRunway = new JButton("Reset Modules.Runway");
        btnResetRunway.setBounds(643, 297, 136, 33);
        panel.add(btnResetRunway);

        btnResetRunway.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                airportManagementSystem.resetRunway(planeManagementSystem,runwayAirport.getText());
                setStatusArea(runwayStatus, airportManagementSystem.getRunways());
            }
        });


        JLabel lblRunwayId = new JLabel("Modules.Runway Id");
        lblRunwayId.setBounds(10, 287, 72, 13);
        panel.add(lblRunwayId);


        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                        // Get the values from the selected row
                        String deptLocation = (String) table.getValueAt(selectedRow, 0);
                        int cap = (int) table.getValueAt(selectedRow, 2);

                        // Update the input fields with the selected values
                        planeId.setText(deptLocation);
                        capacity.setValue(cap);
                        selectRow.setText(deptLocation);

                    }
                }

            }
        });
    }

    private void setStatusArea(JTextArea textArea, ArrayList<Runway> runways) {
        String txt = "";
        for (Runway runway : runways) {
            if (runway.getPlane() == null) {
                txt += "Modules.Runway " + runway.getRunwayId() + " is empty.\n";
            } else {
                txt += "Modules.Runway " + runway.getRunwayId() + " is reserved by " + runway.getPlane().getPlaneId() + ".\n";
            }
        }
        textArea.setText(txt);
    }

    private void setHangarStatusArea(JTextArea textArea, ArrayList<Hangar> hangars) {
        String txt = "";
        for (Hangar hangar : hangars) {
            if (hangar.getPlane() == null) {
                txt += "Modules.Hangar " + hangar.getHangarId() + " is empty.\n";
            } else {
                txt += "Modules.Hangar " + hangar.getHangarId() + " is reserved by " + hangar.getPlane().getPlaneId() + ".\n";
            }
        }
        textArea.setText(txt);
    }

    private void refreshTable(DefaultTableModel model) {
        model.setRowCount(0);
        String selectPlanesSQL = "SELECT * FROM planes";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectPlanesSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                String planeId = resultSet.getString("plane_id");
                boolean available = resultSet.getBoolean("available");
                int capacity = resultSet.getInt("capacity");
                model.addRow(new Object[]{planeId, available, capacity});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(panel, "Error loading planes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public JPanel getPanel() {
        return this.panel;
    }
}