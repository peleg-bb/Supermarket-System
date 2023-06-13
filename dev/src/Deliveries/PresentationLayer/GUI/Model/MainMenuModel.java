package Deliveries.PresentationLayer.GUI.Model;

import Deliveries.BusinessLayer.DeliveryFormsController;
import Deliveries.BusinessLayer.DeliveryManagerImpl;
import Deliveries.BusinessLayer.Generators.SiteGenerator;
import Deliveries.BusinessLayer.Site;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

public class MainMenuModel implements ActionListener {
    private DeliveryManagerImpl deliveryManager = DeliveryManagerImpl.getInstance();
    private DeliveryFormsController deliveryFormsController;
    public MainMenuModel() {
        deliveryManager = DeliveryManagerImpl.getInstance(); //removed the use of service class
        deliveryFormsController = deliveryManager.getDeliveryFormsController();
        deliveryFormsController.loadFormsData();
    }
    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        deliveryManager.createDeliveryGroup();
        if (!deliveryManager.getDeliveryFormsController().getPendingDeliveryForms().iterator().hasNext()) {
            System.out.println("Couldn't create delivery groups due to an illegal combination of delivery stops");
            return;
        }

    }
}
