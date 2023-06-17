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
            relatedFrame.displayError("Not implemented yet :(");
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
        relatedFrame.displayError("Trip re-plan action called but not implemented yet!");
        return stops.get(0);
    }

    @Override
    public TripReplanAction chooseAction(List<DeliveryStop> stops) {
        relatedFrame.displayError("Trip re-plan action called but not implemented yet!");
        return TripReplanAction.REWEIGH_TRUCK;
    }
}
