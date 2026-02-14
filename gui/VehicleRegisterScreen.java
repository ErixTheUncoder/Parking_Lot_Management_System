package gui;
import java.awt.*;
import javax.swing.*;
import model.*;

public class VehicleRegisterScreen extends JFrame {

    private JTextField vehicleNumberField;
    private JComboBox<VehicleType> carTypeBox;

    public VehicleRegisterScreen() {
        setTitle("Enter Vehicle Details");
        setSize(500, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6,1,10,10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        //Plate number
        panel.add(new JLabel("Vehicle Number:"));
        vehicleNumberField = new JTextField();
        panel.add(vehicleNumberField);

        //Vehicle Type
        panel.add(new JLabel("Car Type:"));
        carTypeBox = new JComboBox<>(VehicleType.values());
        panel.add(carTypeBox);

        //Button
        JButton proceedBtn = new JButton("Proceed");
        proceedBtn.addActionListener(e -> openDashboard());
        panel.add(proceedBtn);

        add(panel);
        setVisible(true);
    }

    private void openDashboard() {
        String plate = vehicleNumberField.getText().trim();
        VehicleType type = (VehicleType) carTypeBox.getSelectedItem();

        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter vehicle number");
            return;
        }

        // Create Vehicle model object
        Vehicle vehicle = new Vehicle(plate, type);

        // Open dashboard and pass vehicle
        this.dispose();
        new VehicleDashboard(vehicle);
    }
}
