package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import db.*;

public class AdminPanel extends JPanel {

    public AdminPanel() {

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(5, 1, 10, 10));

        mainPanel.add(createAllSpotsPanel());
        mainPanel.add(createOccupancyRatePanel());
        mainPanel.add(createVehiclesParkedPanel());
        mainPanel.add(createUnpaidFinesPanel());
        mainPanel.add(createFineSchemePanel());

        return mainPanel;
    }

    // ===============================
    // Total Vehicles (All-Time)
    // ===============================
    private JPanel createAllSpotsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Total Vehicles in System"));

        int total = VehicleDAO.getTotalVehicles();

        JLabel label = new JLabel("Total Vehicles Registered: " + total);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // ===============================
    // Occupancy (Based on PARKED status)
    // ===============================
    private JPanel createOccupancyRatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Current Occupancy"));

        int parked = VehicleDAO.getParkedVehicles().size();
        int totalCapacity = 100; // You can replace with DB value later

        double rate = (double) parked / totalCapacity * 100;

        JLabel label = new JLabel(
                "Parked: " + parked +
                        " / " + totalCapacity +
                        " (" + String.format("%.2f", rate) + "%)");

        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // ===============================
    // Currently Parked Vehicles
    // ===============================
    private JPanel createVehiclesParkedPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Currently Parked Vehicles"));

        JTextArea area = new JTextArea();
        area.setEditable(false);

        List<String> vehicles = VehicleDAO.getParkedVehicles();
        for (String v : vehicles) {
            area.append(v + "\n");
        }

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    // ===============================
    // Unpaid Fines
    // ===============================
    private JPanel createUnpaidFinesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Unpaid Fines"));

        JTextArea area = new JTextArea();
        area.setEditable(false);

        List<String> fines = InvoiceDAO.getUnpaidInvoices();
        for (String fine : fines) {
            area.append(fine + "\n");
        }

        panel.add(new JScrollPane(area), BorderLayout.CENTER);
        return panel;
    }

    // ===============================
    // Fine Scheme (Still uses PriceRegistry)
    // ===============================
    private JPanel createFineSchemePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Fine Scheme"));

        JComboBox<String> schemeBox = new JComboBox<>(
                new String[]{"Standard", "Premium", "Weekend Special"});

        JButton applyBtn = new JButton("Apply");

        applyBtn.addActionListener(e -> {
            String selected = (String) schemeBox.getSelectedItem();
            PriceRegistry.setActiveScheme(selected);

            JOptionPane.showMessageDialog(this,
                    "Fine Scheme Updated to: " + selected);
        });

        panel.add(schemeBox);
        panel.add(applyBtn);

        return panel;
    }
}
