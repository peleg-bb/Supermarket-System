package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.BusinessLayer.DeliveryForm;
import Deliveries.BusinessLayer.Enums_and_Interfaces.WeightMeasurer;

import java.awt.event.ActionEvent;

public class MakeDeliveryModel extends AbstractModel implements WeightMeasurer {
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
            deliveryForm.startJourney(this);
            relatedFrame.displayInfo("Delivery started!");
        }
        else {
            relatedFrame.displayError("Not implemented yet :(");
        }

    }

    @Override
    public int measureWeight(DeliveryForm form) {
        relatedFrame.displayError("Measure weight called but not implemented yet!");
        relatedFrame.displayInfo("Returning 4 as a placeholder for weight measurement");
        return 4;
    }
}
