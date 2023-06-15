package Deliveries.PresentationLayer.GUI.View;

import Deliveries.PresentationLayer.GUI.Model.AbstractModel;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractFrame extends JFrame {
    protected final JPanel buttonsPanel;
    private final JPanel errorPanel;
    private final JPanel infoPanel;
    protected AbstractModel relatedModel;


    public AbstractFrame(int numButtons, AbstractModel relatedModel) {
        this.relatedModel = relatedModel;
        relatedModel.addFrame(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Deliveries System");
        setLayout(new BorderLayout());

        // Create buttons panel
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(numButtons, 1));
        add(buttonsPanel, BorderLayout.CENTER);

        // Create error panel
        errorPanel = new JPanel();
        errorPanel.setBorder(BorderFactory.createTitledBorder("Error Panel"));
        errorPanel.setPreferredSize(new Dimension(200, 90));
        add(errorPanel, BorderLayout.NORTH);
        // Create info panel
        infoPanel = new JPanel();
        infoPanel.setBorder(BorderFactory.createTitledBorder("Information Panel"));
        infoPanel.setPreferredSize(new Dimension(200, 90));
        add(infoPanel, BorderLayout.SOUTH);


        getContentPane().setPreferredSize(new Dimension(800, 600)); // Set the width and height as desired

        pack();
        setVisible(true);
    }

    protected void addButton(String buttonText) {
        JButton button = new JButton(buttonText);
        buttonsPanel.add(button);

        // Button action listener
        button.addActionListener(relatedModel);
        button.addActionListener(e -> {
            String buttonText1 = ((JButton) e.getSource()).getText();
            displayInfo(buttonText1 + " clicked");
        });
    }

    public void displayError(String errorMessage) {
        errorPanel.removeAll();
        JLabel errorLabel = new JLabel(errorMessage);
        errorLabel.setForeground(Color.RED);
        errorPanel.add(errorLabel);
        errorPanel.revalidate();
        errorPanel.repaint();
    }

    public void clearError() {
        errorPanel.removeAll();
        errorPanel.revalidate();
        errorPanel.repaint();
    }

    public void displayInfo(String infoMessage) {
        infoPanel.removeAll();
        JLabel infoLabel = new JLabel(infoMessage);
        infoPanel.add(infoLabel);
        infoPanel.revalidate();
        infoPanel.repaint();
    }

    public void clearInfo() {
        infoPanel.removeAll();
        infoPanel.revalidate();
        infoPanel.repaint();
    }

}
