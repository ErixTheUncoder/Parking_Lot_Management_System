package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import db.*;

public class ReportingPanel extends JPanel {

    public ReportingPanel() {

        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(4, 1, 10, 10));

        mainPanel.add(createOccupancyRateReportPanel());
        mainPanel.add(createParkedVehicleReportPanel());
        mainPanel.add(createUnpaidFinesReportPanel());
        mainPanel.add(createRevenueReportPanel());

        return mainPanel;
    }

    // ===============================
    // Daily Vehicles Report
    // ===============================
    private JPanel createOccupancyRateReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Today's Vehicle Entries"));

        int todayVehicles = ReportDAO.getTotalVehiclesToday();

        JLabel label = new JLabel("Vehicles Entered Today: " + todayVehicles);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // ===============================
    // Parked Vehicle Report
    // ===============================
    private JPanel createParkedVehicleReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Currently Parked Vehicles Report"));

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
    // Unpaid Fines Report
    // ===============================
    private JPanel createUnpaidFinesReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Unpaid Fines Report"));

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
    // Revenue Report
    // ===============================
    private JPanel createRevenueReportPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Revenue Report"));

        double revenue = InvoiceDAO.getTotalRevenue();

        JLabel label = new JLabel("Total Revenue Collected: RM " + revenue);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
