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
        addStopButton.setBounds(50, 100, 95, 30);
        addStopButton.setVisible(true);
        JButton removeStopButton = new JButton("Remove a delivery stop");
        removeStopButton.setBounds(50, 100, 95, 30);
        removeStopButton.setVisible(true);
        JButton executeButton = new JButton("Execute a delivery");
        executeButton.setBounds(50, 100, 95, 30);
        executeButton.setVisible(true);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(150, 200, 95, 30);

        add(addStopButton);
        add(removeStopButton);
        add(executeButton);
        add(exitButton);
    }

}