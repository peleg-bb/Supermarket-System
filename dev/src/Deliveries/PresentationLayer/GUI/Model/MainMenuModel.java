package Deliveries.PresentationLayer.GUI.Model;

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
import Deliveries.PresentationLayer.GUI.View.AddDeliveryFrame;
=======
import Deliveries.BusinessLayer.DeliveryForm;
>>>>>>> 6f8c36f5266d5abf22ef1352c9833c229bbffe6c
=======
import Deliveries.BusinessLayer.DeliveryForm;
>>>>>>> 6f8c36f5266d5abf22ef1352c9833c229bbffe6c
=======
import Deliveries.BusinessLayer.DeliveryForm;
>>>>>>> 6f8c36f5266d5abf22ef1352c9833c229bbffe6c
=======
import Deliveries.BusinessLayer.DeliveryForm;
>>>>>>> 6f8c36f5266d5abf22ef1352c9833c229bbffe6c
import Deliveries.BusinessLayer.DeliveryFormsController;
import Deliveries.BusinessLayer.DeliveryManagerImpl;
import Deliveries.PresentationLayer.GUI.View.ExecuteDeliveriesFrame;

<<<<<<< HEAD
=======
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

>>>>>>> 6f8c36f5266d5abf22ef1352c9833c229bbffe6c
public class MainMenuModel extends AbstractModel {
    private DeliveryManagerImpl deliveryManager = DeliveryManagerImpl.getInstance();
    private final DeliveryFormsController deliveryFormsController;
    public MainMenuModel() {
        deliveryManager = DeliveryManagerImpl.getInstance(); //removed the use of service class
        deliveryFormsController = deliveryManager.getDeliveryFormsController();
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getActionCommand().equals("Add a delivery stop")) {
            AddDeliveryStopClicked();
        }
        else if (e.getActionCommand().equals("Remove a delivery stop")) {
            RemoveDeliveryStopClicked();
        }
        else if (e.getActionCommand().equals("Execute a delivery")) {
            ExecuteDeliveriesClicked();
        }
        else if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }
    }

    private void AddDeliveryStopClicked() {
        // TODO: Implement this
        //relatedFrame.displayError("Not implemented yet :(");
        relatedFrame.dispose();
        new AddDeliveryFrame(deliveryManager.getDeliveryFormsController().getPendingDeliveryForms());

    }

    private void RemoveDeliveryStopClicked() {
        // TODO: Implement this
        relatedFrame.dispose();
        relatedFrame.displayError("Not implemented yet :(");
    }

    public void ExecuteDeliveriesClicked(){
        Set<DeliveryForm> pendingDeliveryForms = deliveryFormsController.getPendingDeliveryForms();

        if (pendingDeliveryForms.isEmpty()) {
            deliveryManager.createDeliveryGroup();
            pendingDeliveryForms = deliveryFormsController.getPendingDeliveryForms();
            // Breaks separation of concerns, should be fixed using a service layer
        }
        relatedFrame.dispose();
        new ExecuteDeliveriesFrame(pendingDeliveryForms);
    }
}
