package Deliveries.BusinessLayer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DeliveryFormsController {
    private final Set<DeliveryForm> pendingDeliveryForms; // Improve to separate by status
    private final Set<DeliveryForm> completedDeliveryForms;
    // singleton
    private static DeliveryFormsController instance;


    private DeliveryFormsController() {
        pendingDeliveryForms = new HashSet<>();
        completedDeliveryForms = new HashSet<>();
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

    public Set<DeliveryForm> getPendingDeliveryForms() {
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

    public DeliveryForm getDeliveryForm(int id) {
        for (DeliveryForm deliveryForm : pendingDeliveryForms) {
            if (deliveryForm.getFormId() == id) {
                return deliveryForm;
            }
        }
        return null;
    }

}
