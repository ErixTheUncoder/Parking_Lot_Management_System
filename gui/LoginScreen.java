package gui;

import java.awt.*;
import javax.swing.*;

/**
 * LoginScreen - Role-based entry screen
 * User → Vehicle Entry
 * Admin → Login required
 */
public class LoginScreen extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton loginButton;
    private JLabel statusLabel;

    //Panels must be instance variables to hide/show
    private JPanel emailPanel;
    private JPanel passwordPanel;

    public LoginScreen() {
        setTitle("Parking Management System");
        setSize(500, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        //Title
        JLabel titleLabel = new JLabel(
                "Parking Management System",
                SwingConstants.CENTER
        );
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        mainPanel.add(titleLabel);

        //Role Selection
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolePanel.add(new JLabel("Select Role:"));
        String[] roles = {"User", "Admin"};
        roleComboBox = new JComboBox<>(roles);
        rolePanel.add(roleComboBox);
        mainPanel.add(rolePanel);

        //Email Panel
        emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        emailPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        emailPanel.add(emailField);
        mainPanel.add(emailPanel);

        //Password Panel
        passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        passwordPanel.add(passwordField);
        mainPanel.add(passwordPanel);

        //Button
        loginButton = new JButton("Continue");
        loginButton.addActionListener(e -> handleLogin());
        mainPanel.add(loginButton);

        //Status Label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        mainPanel.add(statusLabel);

        add(mainPanel);

        //Listeners
        roleComboBox.addActionListener(e -> toggleLoginFields());

        //Enter key triggers action
        passwordField.addActionListener(e -> handleLogin());

        //Default State
        roleComboBox.setSelectedItem("User");
        toggleLoginFields();
    }

    //Show login fields only for Admin
    private void toggleLoginFields() {

        String role = (String) roleComboBox.getSelectedItem();
        boolean isAdmin = role.equals("Admin");

        emailPanel.setVisible(isAdmin);
        passwordPanel.setVisible(isAdmin);

        loginButton.setText(isAdmin ? "Login" : "Continue");

        // Clear fields when switching to User
        if (!isAdmin) {
            emailField.setText("");
            passwordField.setText("");
            statusLabel.setText("");
        }

        revalidate();
        repaint();
    }

    //Handle button click
    private void handleLogin() {

        String role = (String) roleComboBox.getSelectedItem();

        //User flow
        if (role.equals("User")) {
            this.dispose();
            new VehicleRegisterScreen();
            return;
        }

        //Admin Login Flow
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter email and password");
            return;
        }

        //Replace with real validation
        if (email.equals("admin") && password.equals("1234")) {

            statusLabel.setForeground(new Color(0, 130, 0));
            statusLabel.setText("Login successful!");

            SwingUtilities.invokeLater(() -> {
                this.dispose();
                new AdminDashboard(email);
            });

        } else {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText("Invalid admin credentials!");
        }
    }
}
