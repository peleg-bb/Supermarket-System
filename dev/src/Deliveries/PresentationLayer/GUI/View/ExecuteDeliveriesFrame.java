package Deliveries.PresentationLayer.GUI.View;

import Deliveries.BusinessLayer.DeliveryForm;

import java.util.Set;

public class ExecuteDeliveriesFrame extends AbstractFrame {
    public ExecuteDeliveriesFrame(Set<DeliveryForm> deliveryForms) {
        super(deliveryForms.size(), e -> {});
        for (DeliveryForm deliveryForm : deliveryForms) {
            addBottom(e -> {}, deliveryForm.toString());
        }
    }
}
