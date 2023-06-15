package Deliveries.PresentationLayer.GUI.View;

import Deliveries.BusinessLayer.DeliveryForm;
import Deliveries.PresentationLayer.GUI.Model.ExecuteDeliveriesModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Set;

public class ExecuteDeliveriesFrame extends AbstractFrame {

    public ExecuteDeliveriesFrame(Set<DeliveryForm> deliveryForms) {
        super(deliveryForms.size() + 1, new ExecuteDeliveriesModel());

        addButton("Return to main menu");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(buttonsPanel);
        add(topPanel, BorderLayout.NORTH);

        //JPanel centerPanel = new JPanel(new GridLayout(deliveryForms.size(), 1));
       // add(centerPanel, BorderLayout.CENTER);

        for (DeliveryForm deliveryForm : deliveryForms) {
            //if (deliveryForm.toString().startsWith("form")) {
                addButton(deliveryForm.toString());
            //}
        }
    }

    private void addButton(String buttonText, JPanel panel) {
        JButton button = new JButton(buttonText);
        button.addActionListener(relatedModel);
        panel.add(button);

    }
//    @Override
//    protected void addButton(String buttonText) {
//        super.addButton(buttonText);
//    }

    private void executeDelivery(ActionEvent event) {
        String buttonText = event.getActionCommand();
        // Execute the delivery based on the button text or perform any other desired action
        System.out.println("Executing delivery for: " + buttonText);
    }
}
