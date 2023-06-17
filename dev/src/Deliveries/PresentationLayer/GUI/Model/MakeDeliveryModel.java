package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.BusinessLayer.DeliveryForm;
import Deliveries.BusinessLayer.DeliveryStop;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TripReplanAction;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TripReplanner;
import Deliveries.BusinessLayer.Enums_and_Interfaces.WeightMeasurer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MakeDeliveryModel extends AbstractModel implements WeightMeasurer, TripReplanner {
    DeliveryForm deliveryForm;
    public MakeDeliveryModel(DeliveryForm deliveryForm) {
        this.deliveryForm = deliveryForm;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Return to main menu")) {
            ReturnToMainMenuClicked();
        }
        else if (e.getActionCommand().equals("Start delivery")) {
            relatedFrame.displayInfo("Delivery started!");
            ((JButton)e.getSource()).setVisible(false);
            deliveryForm.startJourney(this, this);
        }
        else {
            relatedFrame.displayError("Clicked a button which is not yet implemented :(");
        }
    }

    @Override
    public int measureWeight(DeliveryForm form) {
        String currentSite = form.getDestinationSitesToVisit().get(0).getDestination().getName();
        relatedFrame.displayInfo("Successfully delivered to " + currentSite + "!");
        String weightString = JOptionPane.showInputDialog(relatedFrame, "The truck is leaving " + currentSite
                + " and needs to be weighed, please enter weight", "Enter weight:", JOptionPane.QUESTION_MESSAGE);
        if (weightString != null) {
            try {
                return Integer.parseInt(weightString);
            } catch (NumberFormatException ex) {
                relatedFrame.displayError("Invalid weight entered!");
                return measureWeight(form);
            }
        }
        // TODO: Handle cancel button

        relatedFrame.displayError("Invalid weight entered!");
        return measureWeight(form);
    }

    @Override
    public DeliveryStop removeStop(List<DeliveryStop> stops) {
        JComboBox<DeliveryStop> stopsComboBox = new JComboBox<>(stops.toArray(new DeliveryStop[0]));
        JOptionPane.showMessageDialog(relatedFrame, stopsComboBox, "Choose stop to remove:", JOptionPane.QUESTION_MESSAGE);
        return (DeliveryStop) stopsComboBox.getSelectedItem();
    }

    @Override
    public TripReplanAction chooseAction(List<DeliveryStop> stops) {
        relatedFrame.displayError("The truck is overloaded," +
                " and we couldn't arrange a larger truck. You must choose how to proceed.");
        JComboBox<TripReplanAction> actions = new JComboBox<>(TripReplanAction.values());
        JOptionPane.showMessageDialog(relatedFrame, actions, "Choose re-plan action:", JOptionPane.QUESTION_MESSAGE);
        return (TripReplanAction) actions.getSelectedItem();
    }
}
