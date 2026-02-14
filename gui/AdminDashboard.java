package gui;

import javax.swing.*;
import java.awt.*;
import model.*;

public class AdminDashboard extends JFrame {

    private JTabbedPane tabbedPane;
    private String email; // added missing field

    // Constructor
    public AdminDashboard(String email) { 
        this.email = email;

        setTitle("Admin Dashboard");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblAdmin = new JLabel("Admin: " + email);
        lblAdmin.setFont(new Font("Arial", Font.BOLD, 16));
        lblAdmin.setAlignmentX(LEFT_ALIGNMENT);

        headerPanel.add(lblAdmin);

        // Tabs
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Admin Panel", new AdminPanel());
        tabbedPane.addTab("Reporting Panel", new ReportingPanel());

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}
