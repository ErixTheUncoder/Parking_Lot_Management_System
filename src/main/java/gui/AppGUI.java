package gui;

import db.DatabaseConnection;
import db.PriceRegistry;
import javax.swing.*;

public class AppGUI {
    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize database
        System.out.println("Initializing database...");
        DatabaseConnection.initializeDatabase("schema.sql");
        
        // Load price registry
        PriceRegistry.loadFromDatabase();

        // Launch login screen
        SwingUtilities.invokeLater(() -> new LoginScreen());
    }
}
