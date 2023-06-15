package Deliveries.PresentationLayer.GUI.View;

import Deliveries.PresentationLayer.GUI.Model.MakeDeliveryModel;

public class MakeDeliveryFrame extends AbstractFrame {
    public MakeDeliveryFrame() {
        super(3, new MakeDeliveryModel());
        addButton("Return to main menu");
    }
}
