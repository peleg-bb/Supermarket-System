package Deliveries;

import java.util.ArrayList;
import java.util.List;

public class DeliveryFormsController {
    private final List<DeliveryForm> pendingDeliveryForms; // Improve to separate by status
    private final List<DeliveryForm> completedDeliveryForms;
    // singleton
    private static DeliveryFormsController instance;


    private DeliveryFormsController() {
        pendingDeliveryForms = new ArrayList<>();
        completedDeliveryForms = new ArrayList<>();
        // As of now doesn't have to be a singleton
    }

    public static DeliveryFormsController getInstance() {
        if (instance == null) {
            instance = new DeliveryFormsController();
        }
        return instance;
    }


    public void addDeliveryForm(DeliveryForm deliveryForm) {
        pendingDeliveryForms.add(deliveryForm);
    }

    public void removeDeliveryForm(DeliveryForm deliveryForm) {
        this.pendingDeliveryForms.remove(deliveryForm);
    }

    public List<DeliveryForm> getPendingDeliveryForms() {
        return pendingDeliveryForms;
    }

    public void startDeliveryForm(DeliveryForm deliveryForm) {
        deliveryForm.startJourney();
    }

    // print deliveries
    public void printPendingDeliveryForms() {
        for (DeliveryForm deliveryForm : pendingDeliveryForms) {
            System.out.println(deliveryForm);
        }
    }

    public void printCompletedDeliveryForms() {
        for (DeliveryForm deliveryForm : completedDeliveryForms) {
            System.out.println(deliveryForm);
        }
    }

    public void terminateDeliveryForm(DeliveryForm deliveryForm) {
        pendingDeliveryForms.remove(deliveryForm);
        completedDeliveryForms.add(deliveryForm);
    }

}
