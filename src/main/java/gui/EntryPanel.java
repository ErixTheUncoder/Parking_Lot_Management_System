package gui;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.*;

public class EntryPanel extends JPanel {

    private Vehicle vehicle;
    private EntryGate entryGate;

    private JComboBox<String> spotDropdown;
    private JTextField ticketIdField;
    private JTextField entryTimeField;
    private JTextField ticketSpotField;

    public EntryPanel(Vehicle vehicle, EntryGate entryGate) {
        this.vehicle = vehicle;
        this.entryGate = entryGate;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.add(createAvailableSpotPanel());
        mainPanel.add(createTicketPanel());
        return mainPanel;
    }

    private JPanel createAvailableSpotPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder(
                "Select Available Spot for: " + vehicle.getVehicleType()));

        // Get allowed spots
        List<Spot> allowedSpots = entryGate.findAvailableSpots(vehicle.getVehicleType());

        // Convert long spot IDs to Strings
        String[] spotIDs = allowedSpots.stream()
                .map(spot -> String.valueOf(spot.getSpotID()))
                .toArray(String[]::new);

        spotDropdown = new JComboBox<>(spotIDs);
        spotDropdown.setPreferredSize(new Dimension(200, 25));

        panel.add(new JLabel("Available Spots:"));
        panel.add(spotDropdown);

        JButton reserveBtn = new JButton("Reserve Spot & Generate Ticket");
        reserveBtn.addActionListener(e -> handleReserveSpot());
        panel.add(reserveBtn);

        return panel;
    }

    private JPanel createTicketPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Ticket"));

        panel.add(new JLabel("Ticket ID:"));
        ticketIdField = new JTextField(); ticketIdField.setEditable(false);
        panel.add(ticketIdField);

        panel.add(new JLabel("Entry Time:"));
        entryTimeField = new JTextField(); entryTimeField.setEditable(false);
        panel.add(entryTimeField);

        panel.add(new JLabel("Spot:"));
        ticketSpotField = new JTextField(); ticketSpotField.setEditable(false);
        panel.add(ticketSpotField);

        return panel;
    }

    private void handleReserveSpot() {
        String selectedSpotIDStr = (String) spotDropdown.getSelectedItem();
        if (selectedSpotIDStr == null) {
            JOptionPane.showMessageDialog(this, "Please select a spot.");
            return;
        }

        Ticket ticket = entryGate.processEntry(vehicle, selectedSpotIDStr);
        if (ticket == null) {
            JOptionPane.showMessageDialog(this, "Selected spot is no longer available.");
            return;
        }

        ticketIdField.setText(ticket.getTicketID());
        entryTimeField.setText(ticket.getEntryTimeFormatted());

        // Add null check for spot
        if (ticket.getSpot() != null) {
            ticketSpotField.setText(ticket.getSpot().getSpotName());
        } else {
            ticketSpotField.setText("N/A");
        }

        JOptionPane.showMessageDialog(this, "Spot reserved! Ticket generated.");
    }

}
