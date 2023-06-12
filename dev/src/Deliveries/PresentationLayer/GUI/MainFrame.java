package Deliveries.PresentationLayer.GUI;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        super("Deliveries System");
        // add an explanatory title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setVisible(true);
        // Add labels and buttons here
        JLabel label = new JLabel("Welcome to the Deliveries System!");
        add(label);
        JButton addStopButton = new JButton("Add a delivery stop");
        JButton removeStopButton = new JButton("Remove a delivery stop");
        JButton executeButton = new JButton("Execute a delivery");
        JButton exitButton = new JButton("Exit");

        add(addStopButton);
        add(removeStopButton);
        add(executeButton);
        add(exitButton);
    }

}