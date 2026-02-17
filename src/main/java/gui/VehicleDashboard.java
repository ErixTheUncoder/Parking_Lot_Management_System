package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header panel with BorderLayout to place labels left, logout right
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Left side: vehicle info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        JLabel lblVehicle = new JLabel("Vehicle: " + vehicle.getLicensePlate());
        lblVehicle.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel lblType = new JLabel("Vehicle Type: " + vehicle.getVehicleType());
        lblType.setFont(new Font("Arial", Font.PLAIN, 14));
        infoPanel.add(lblVehicle);
        infoPanel.add(lblType);

        // Right side: logout button
        JButton btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // close current dashboard
                SwingUtilities.invokeLater(() -> new LoginScreen()); // open login screen
            }
        });

        headerPanel.add(infoPanel, BorderLayout.WEST);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Entry Process", new EntryPanel(vehicle, entryGate));
        tabbedPane.addTab("Exit Process", new ExitPanel(vehicle, exitGate));

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}
