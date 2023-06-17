package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.BusinessLayer.DeliveryForm;
import Deliveries.BusinessLayer.DeliveryStop;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TripReplanAction;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TripReplanner;
import Deliveries.BusinessLayer.Enums_and_Interfaces.WeightMeasurer;

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
            deliveryForm.startJourney(this, this);
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
