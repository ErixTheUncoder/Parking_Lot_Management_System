package gui;

import javax.swing.*;
import model.*;

public class VehicleDashboard extends JFrame {

    private Vehicle vehicle;
    private EntryGate entryGate;
    private ExitGate exitGate;
    private JTabbedPane tabbedPane;

    public VehicleDashboard(Vehicle vehicle, EntryGate entryGate, ExitGate exitGate) {
        this.vehicle = vehicle;
        this.entryGate = entryGate;
        this.exitGate = exitGate;

        setTitle("Vehicle Dashboard - " + vehicle.getLicensePlate());
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new java.awt.BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblVehicle = new JLabel("Vehicle: " + vehicle.getLicensePlate());
        lblVehicle.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        lblVehicle.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblType = new JLabel("Vehicle Type: " + vehicle.getVehicleType());
        lblType.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        lblType.setAlignmentX(LEFT_ALIGNMENT);

        headerPanel.add(lblVehicle);
        headerPanel.add(lblType);

        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Entry Process", new EntryPanel(vehicle, entryGate));
        tabbedPane.addTab("Exit Process", new ExitPanel(vehicle, exitGate));

        mainPanel.add(headerPanel, java.awt.BorderLayout.NORTH);
        mainPanel.add(tabbedPane, java.awt.BorderLayout.CENTER);

        add(mainPanel);
    }
}
