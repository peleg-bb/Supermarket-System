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

//        setLayout(new BorderLayout());
//
//        JPanel topPanel = new JPanel(new FlowLayout());
//        topPanel.add(buttonsPanel);
//        add(topPanel, BorderLayout.NORTH);
//
        for (DeliveryForm deliveryForm : deliveryForms) {
                addButton(deliveryForm.toString());
        }
    }

}
