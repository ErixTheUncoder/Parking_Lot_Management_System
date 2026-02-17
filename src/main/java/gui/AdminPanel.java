package gui;

import db.*;
import java.awt.*;
import java.util.List;
import javax.swing.*;

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

    private JPanel createAllSpotsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("All floors and spots"));

        int totalCapacity = SpotDAO.getTotalCapacity();
        JLabel label = new JLabel("Total spots in system: " + totalCapacity);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createOccupancyRatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Current Occupancy"));

        int parked = VehicleDAO.getParkedVehicles().size();
        int totalCapacity = SpotDAO.getTotalCapacity();

        double rate = totalCapacity > 0 ? (double) parked / totalCapacity * 100 : 0;

        JLabel label = new JLabel(
                "Parked: " + parked +
                        " / " + totalCapacity +
                        " (" + String.format("%.2f", rate) + "%)");

        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

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

    private JPanel createFineSchemePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Fine Scheme"));

        JComboBox<String> schemeBox = new JComboBox<>(
                new String[]{"FIXED", "PROGRESSIVE", "HOURLY"});

        // Set current selection
        String currentScheme = FineDAO.getActiveFineScheme();
        schemeBox.setSelectedItem(currentScheme);

        JButton applyBtn = new JButton("Apply");

        applyBtn.addActionListener(e -> {
            String selected = (String) schemeBox.getSelectedItem();
            FineDAO.setActiveFineScheme(selected);

            JOptionPane.showMessageDialog(this,
                    "Fine Scheme Updated to: " + selected);
        });

        panel.add(schemeBox);
        panel.add(applyBtn);

        return panel;
    }
}
