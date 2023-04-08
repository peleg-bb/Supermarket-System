package Deliveries;

import java.util.ArrayList;
import java.util.List;

public class DeliveryFormsController {
    private List<DeliveryForm> deliveryForms;

    public DeliveryFormsController() {
        this.deliveryForms = new ArrayList<>();
    }

    public void addDeliveryForm(DeliveryForm deliveryForm) {
        this.deliveryForms.add(deliveryForm);
    }

    public void removeDeliveryForm(DeliveryForm deliveryForm) {
        this.deliveryForms.remove(deliveryForm);
    }

    public List<DeliveryForm> getDeliveryForms() {
        return this.deliveryForms;
    }



}
