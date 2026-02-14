package gui;
import java.awt.*;
import javax.swing.*;
import model.*;



public class VehicleDashboard extends JFrame {
    private Vehicle vehicle;
    private JTabbedPane tabbedPane;

    public VehicleDashboard(Vehicle vehicle) {
        this.vehicle = vehicle;
        //this.assignedSubmissions = FileService.loadSubmissionsByEvaluator(evaluator.getEvaluatorID());

        setTitle("User Dashboard - " + vehicle.getLicensePlate());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initializeComponents();
        setVisible(true);
    }

    private void initializeComponents() {
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Header panel with vertical layout
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel welcomeLabel = new JLabel("Vehicle: " + vehicle.getLicensePlate());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(welcomeLabel);

        JLabel typeLabel = new JLabel("Vehicle Type: " + vehicle.getVehicleType());
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        headerPanel.add(typeLabel);

        
        // Tabbed pane - simpler
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Entry Process", new EntryPanel(vehicle));
        tabbedPane.addTab("Exit Process", new ExitPanel(vehicle));
        

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

}