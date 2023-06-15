package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.PresentationLayer.GUI.View.AddDeliveryFrame;
import Deliveries.BusinessLayer.DeliveryFormsController;
import Deliveries.BusinessLayer.DeliveryManagerImpl;
import Deliveries.PresentationLayer.GUI.View.ExecuteDeliveriesFrame;

public class MainMenuModel extends AbstractModel {
    private DeliveryManagerImpl deliveryManager = DeliveryManagerImpl.getInstance();
    private final DeliveryFormsController deliveryFormsController;
    public MainMenuModel() {
        deliveryManager = DeliveryManagerImpl.getInstance(); //removed the use of service class
        deliveryFormsController = deliveryManager.getDeliveryFormsController();
        deliveryFormsController.loadFormsData();
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
        deliveryManager.createDeliveryGroup();
        if (deliveryManager.getDeliveryFormsController().getPendingDeliveryForms().size() == 0) {
            relatedFrame.displayError("Couldn't create delivery groups due to an illegal combination " +
                    "or unavailable delivery stops");
            return;
        }
        relatedFrame.dispose();
        new ExecuteDeliveriesFrame(deliveryManager.getDeliveryFormsController().getPendingDeliveryForms());
    }
}
