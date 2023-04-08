package Deliveries;

import java.util.ArrayList;
import java.util.List;

public class DeliveryFormsController {
    private List<DeliveryForm> deliveryForms;

    public DeliveryFormsController() {
        this.deliveryForms = new ArrayList<>();
        // As of now doesn't have to be a singleton
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

    public void startDeliveryForm(DeliveryForm deliveryForm) {
        deliveryForm.startJourney();
    }

    // print deliveries
    public void printDeliveryForms() {
        for (DeliveryForm deliveryForm : deliveryForms) {
            System.out.println(deliveryForm);
        }
    }



}
