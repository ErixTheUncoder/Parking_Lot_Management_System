package gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import model.*;

public class EntryPanel extends JPanel {

    private Vehicle vehicle;
    private List<ParkingSpot> availableSpots; // list of spots for this vehicle type
    private ParkingSpot selectedSpot;

    // Ticket fields
    private JTextField ticketIdField;
    private JTextField entryTimeField;
    private JTextField spotField;

    public EntryPanel(Vehicle vehicle, List<ParkingSpot> allSpots) {
        this.vehicle = vehicle;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Filter available spots for this vehicle type
        availableSpots = allSpots.stream()
                .filter(spot -> !spot.isOccupied() && spot.getVehicleType() == vehicle.getVehicleType())
                .toList();

        // ===== TOP PANEL: Available Spots =====
        JPanel spotsPanel = new JPanel(new BorderLayout());
        spotsPanel.setBorder(BorderFactory.createTitledBorder("Available Parking Spots"));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ParkingSpot spot : availableSpots) {
            listModel.addElement(spot.getSpotId());
        }

        JList<String> spotList = new JList<>(listModel);
        spotList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(spotList);

        spotsPanel.add(scrollPane, BorderLayout.CENTER);

        // ===== CENTER PANEL: Ticket Details =====
        JPanel ticketPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        ticketPanel.setBorder(BorderFactory.createTitledBorder("Ticket"));

        ticketPanel.add(new JLabel("Ticket ID:"));
        ticketIdField = new JTextField();
        ticketIdField.setEditable(false);
        ticketPanel.add(ticketIdField);

        ticketPanel.add(new JLabel("Entry Time:"));
        entryTimeField = new JTextField();
        entryTimeField.setEditable(false);
        ticketPanel.add(entryTimeField);

        ticketPanel.add(new JLabel("Spot:"));
        spotField = new JTextField();
        spotField.setEditable(false);
        ticketPanel.add(spotField);

        // ===== BOTTOM PANEL: Actions =====
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton selectSpotBtn = new JButton("Select Spot and Generate Ticket");

        selectSpotBtn.addActionListener(e -> {
            int selectedIndex = spotList.getSelectedIndex();
            if (selectedIndex == -1) {
                JOptionPane.showMessageDialog(this, "Please select a parking spot!");
                return;
            }

            selectedSpot = availableSpots.get(selectedIndex);

            // Mark the spot as occupied
            selectedSpot.setOccupied(true);

            // Generate ticket info
            LocalDateTime now = LocalDateTime.now();
            String ticketId = "T-" + vehicle.getLicensePlate() + "-" +
                    now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));

            ticketIdField.setText(ticketId);
            entryTimeField.setText(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            spotField.setText(selectedSpot.getSpotId());

            JOptionPane.showMessageDialog(this, "Ticket generated successfully!");
        });

        actionPanel.add(selectSpotBtn);

        // ===== Combine Panels =====
        add(spotsPanel, BorderLayout.NORTH);
        add(ticketPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
    }
}
