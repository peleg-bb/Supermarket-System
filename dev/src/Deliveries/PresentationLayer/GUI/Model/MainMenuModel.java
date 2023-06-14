package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.BusinessLayer.DeliveryFormsController;
import Deliveries.BusinessLayer.DeliveryManagerImpl;
import Deliveries.BusinessLayer.Generators.SiteGenerator;
import Deliveries.BusinessLayer.Site;
import Deliveries.PresentationLayer.GUI.View.AbstractFrame;
import Deliveries.PresentationLayer.GUI.View.ExecuteDeliveriesFrame;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

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
        relatedFrame.dispose();
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
    }

    private void RemoveDeliveryStopClicked() {
        // TODO: Implement this

    }

    public void ExecuteDeliveriesClicked(){
        deliveryManager.createDeliveryGroup();
        if (!deliveryManager.getDeliveryFormsController().getPendingDeliveryForms().iterator().hasNext()) {
            System.out.println("Couldn't create delivery groups due to an illegal combination of delivery stops");
            return;
        }
        new ExecuteDeliveriesFrame(deliveryManager.getDeliveryFormsController().getPendingDeliveryForms());
    }
}
