package gui;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import model.Vehicle;
import model.ExitGate;
import model.Ticket;
import model.Invoice;

public class ExitPanel extends JPanel {

    private Vehicle vehicle;
    private ExitGate exitGate;

    //Parking Details Fields
    private JTextField plateField;
    private JTextField hoursField;
    private JTextField baseFeeField;
    private JTextField discountField;
    private JTextField totalDueField;

    //Payment Fields
    private JRadioButton cashRadio;
    private JRadioButton cardRadio;
    private JTextField cardNumberField;
    private JTextField cvcField;

    //Receipt Fields
    private JLabel invoiceLabel;
    private JLabel entryTimeLabel;
    private JLabel durationLabel;
    private JLabel feesLabel;


    // CONSTRUCTOR
    public ExitPanel(Vehicle vehicle, ExitGate exitGate) {
        this.vehicle = vehicle;
        this.exitGate = exitGate;

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createMainPanel(), BorderLayout.CENTER);
    }

 
    // BUILD MAIN PANEL
    private JPanel createMainPanel() {

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        centerPanel.add(createParkingDetailsPanel());
        centerPanel.add(createPaymentPanel());
        centerPanel.add(createReceiptPanel());

        return centerPanel;
    }

  
    // 1) PARKING DETAILS PANEL
    private JPanel createParkingDetailsPanel() {

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Parking Details"));

        panel.add(new JLabel("License Plate:"));
        plateField = new JTextField(vehicle.getLicensePlate());
        plateField.setEditable(false);
        panel.add(plateField);

        panel.add(new JLabel("Hours Parked:"));
        hoursField = new JTextField();
        hoursField.setEditable(false);
        panel.add(hoursField);

        panel.add(new JLabel("Base Fee:"));
        baseFeeField = new JTextField();
        baseFeeField.setEditable(false);
        panel.add(baseFeeField);

        panel.add(new JLabel("Discount:"));
        discountField = new JTextField();
        discountField.setEditable(false);
        panel.add(discountField);

        panel.add(new JLabel("Total Payment Due:"));
        totalDueField = new JTextField();
        totalDueField.setEditable(false);
        panel.add(totalDueField);

        return panel;
    }


    // 2) PAYMENT PANEL
    private JPanel createPaymentPanel() {

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Make Payment"));

        cashRadio = new JRadioButton("Cash");
        cardRadio = new JRadioButton("Card");

        ButtonGroup group = new ButtonGroup();
        group.add(cashRadio);
        group.add(cardRadio);

        cashRadio.setSelected(true);

        panel.add(new JLabel("Payment Method:"));
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(cashRadio);
        radioPanel.add(cardRadio);
        panel.add(radioPanel);

        // Card fields
        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberField = new JTextField();
        JLabel cvcLabel = new JLabel("CVC:");
        cvcField = new JTextField();

        panel.add(cardNumberLabel);
        panel.add(cardNumberField);
        panel.add(cvcLabel);
        panel.add(cvcField);

        setCardFieldsVisible(false);

        cashRadio.addActionListener(e -> setCardFieldsVisible(false));
        cardRadio.addActionListener(e -> setCardFieldsVisible(true));

        JButton payButton = new JButton("Pay & Exit");
        payButton.addActionListener(e -> handleExitPayment());
        panel.add(payButton);

        return panel;
    }

    // 3) RECEIPT PANEL
    private JPanel createReceiptPanel() {

        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Receipt"));

        invoiceLabel = new JLabel("Invoice: ");
        entryTimeLabel = new JLabel("Entry Time: ");
        durationLabel = new JLabel("Duration: ");
        feesLabel = new JLabel("Fees/Fines: ");

        JButton downloadBtn = new JButton("Download Receipt");
        downloadBtn.addActionListener(
                e -> JOptionPane.showMessageDialog(this, "Receipt downloaded!")
        );

        panel.add(invoiceLabel);
        panel.add(entryTimeLabel);
        panel.add(durationLabel);
        panel.add(feesLabel);
        panel.add(downloadBtn);

        return panel;
    }


    // HELPER: SHOW/HIDE CARD FIELDS
    private void setCardFieldsVisible(boolean visible) {
        cardNumberField.setVisible(visible);
        cvcField.setVisible(visible);
    }


    // PAYMENT LOGIC USING ExitGate
    private void handleExitPayment() {

        // Step 1: Retrieve ticket
        Ticket ticket = exitGate.retrieveTicket(vehicle.getLicensePlate());
        if (ticket == null) {
            JOptionPane.showMessageDialog(this,
                    "Ticket not found! Cannot process exit.");
            return;
        }

        // Step 2: Base price calculation
        double basePrice = exitGate.calculateBasePrice(ticket, ticket.getSpot().getSpotType());
        // Step 3: Floor premium
        double floorPremium = ticket.getSpot().getFloor().getExtraCharge();
        double subtotal = basePrice + floorPremium;

        // Step 4: Apply discount
        double discount = exitGate.applyDiscount(subtotal);
        double discountedTotal = subtotal - discount;

        // Step 5: Fines
        double fines = exitGate.retrieveFines(vehicle.getLicensePlate());

        // Step 6: Generate Invoice
        Invoice invoice = exitGate.generateInvoice(ticket, basePrice, floorPremium, discount, fines);

        // Step 7: Commit and release spot
        boolean success = exitGate.commitInvoice(invoice);
        if (!success) {
            JOptionPane.showMessageDialog(this,
                    "Error committing payment. Try again.");
            return;
        }

        // Update UI fields
        hoursField.setText(String.valueOf(ticket.getDurationHours()));
        baseFeeField.setText(String.format("$%.2f", basePrice));
        discountField.setText(String.format("$%.2f", discount));
        totalDueField.setText(String.format("$%.2f", invoice.getTotalAmount()));

        invoiceLabel.setText("Invoice: " + invoice.getInvoiceID());
        entryTimeLabel.setText("Entry Time: " + ticket.getEntryTimeFormatted());
        durationLabel.setText("Duration: " + ticket.getDurationHours() + "h");
        feesLabel.setText("Fees/Fines: $" + fines);

        JOptionPane.showMessageDialog(this,
                "Payment successful. Vehicle may exit.");
    }
}
