package gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import model.*;

public class ExitPanel extends JPanel {
    private Vehicle vehicle;

    public ExitPanel(Vehicle vehicle) {
        this.vehicle = vehicle;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== TOP PANEL: Parking Details =====
        JPanel parkingDetailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        parkingDetailsPanel.setBorder(BorderFactory.createTitledBorder("Parking Details"));

        parkingDetailsPanel.add(new JLabel("License Plate:"));
        JTextField plateField = new JTextField(vehicle.getLicensePlate());
        plateField.setEditable(false);
        parkingDetailsPanel.add(plateField);

        parkingDetailsPanel.add(new JLabel("Hours Parked:"));
        JTextField hoursField = new JTextField();
        hoursField.setEditable(false);
        parkingDetailsPanel.add(hoursField);

        parkingDetailsPanel.add(new JLabel("Parking Fee:"));
        JTextField feeField = new JTextField();
        feeField.setEditable(false);
        parkingDetailsPanel.add(feeField);

        parkingDetailsPanel.add(new JLabel("Any Unpaid Fees:"));
        JTextField unpaidField = new JTextField();
        unpaidField.setEditable(false);
        parkingDetailsPanel.add(unpaidField);

        parkingDetailsPanel.add(new JLabel("Total Payment Due:"));
        JTextField totalField = new JTextField();
        totalField.setEditable(false);
        parkingDetailsPanel.add(totalField);

        // ===== MIDDLE PANEL: Payment =====
        JPanel paymentPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        paymentPanel.setBorder(BorderFactory.createTitledBorder("Make Payment"));

        JRadioButton cashRadio = new JRadioButton("Cash");
        JRadioButton cardRadio = new JRadioButton("Card");
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashRadio);
        paymentGroup.add(cardRadio);
        cashRadio.setSelected(true);

        paymentPanel.add(new JLabel("Payment Method:"));
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(cashRadio);
        radioPanel.add(cardRadio);
        paymentPanel.add(radioPanel);

        // Card fields
        JLabel cardNumberLabel = new JLabel("Card Number:");
        JTextField cardNumberField = new JTextField();
        JLabel cvcLabel = new JLabel("CVC:");
        JTextField cvcField = new JTextField();

        paymentPanel.add(cardNumberLabel);
        paymentPanel.add(cardNumberField);
        paymentPanel.add(cvcLabel);
        paymentPanel.add(cvcField);

        // Hide card fields initially
        cardNumberLabel.setVisible(false);
        cardNumberField.setVisible(false);
        cvcLabel.setVisible(false);
        cvcField.setVisible(false);

        cashRadio.addActionListener(e -> {
            cardNumberLabel.setVisible(false);
            cardNumberField.setVisible(false);
            cvcLabel.setVisible(false);
            cvcField.setVisible(false);
        });
        cardRadio.addActionListener(e -> {
            cardNumberLabel.setVisible(true);
            cardNumberField.setVisible(true);
            cvcLabel.setVisible(true);
            cvcField.setVisible(true);
        });

        JButton payButton = new JButton("Pay");
        paymentPanel.add(payButton);

        // ===== BOTTOM PANEL: Receipt =====
        JPanel receiptPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        receiptPanel.setBorder(BorderFactory.createTitledBorder("Receipt"));

        JLabel invoiceLabel = new JLabel("Invoice: ");
        JLabel entryTimeLabel = new JLabel("Entry Time: ");
        JLabel durationLabel = new JLabel("Duration: ");
        JLabel feesLabel = new JLabel("Fees/Fines: ");
        JButton downloadBtn = new JButton("Download Receipt");

        receiptPanel.add(invoiceLabel);
        receiptPanel.add(entryTimeLabel);
        receiptPanel.add(durationLabel);
        receiptPanel.add(feesLabel);
        receiptPanel.add(downloadBtn);

        // ===== Pay Button Action =====
        payButton.addActionListener(e -> {
            String method = cashRadio.isSelected() ? "Cash (Pay at counter)" : "Card";

            if (cardRadio.isSelected()) {
                if (cardNumberField.getText().trim().isEmpty() || cvcField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter card number and CVC!");
                    return;
                }
            }

            invoiceLabel.setText("Invoice: INV-" + plateField.getText() + "-" +
                    LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMddHHmm")));
            entryTimeLabel.setText("Entry Time: 2026-02-14 10:00"); // placeholder
            durationLabel.setText("Duration: 3h"); // placeholder
            feesLabel.setText("Fees/Fines: $12"); // placeholder

            JOptionPane.showMessageDialog(this, "Payment successful via " + method);
        });

        downloadBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Receipt downloaded!"));

        // ===== Combine Panels =====
        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        centerPanel.add(parkingDetailsPanel);
        centerPanel.add(paymentPanel);
        centerPanel.add(receiptPanel);

        add(centerPanel, BorderLayout.CENTER);
    }
}
