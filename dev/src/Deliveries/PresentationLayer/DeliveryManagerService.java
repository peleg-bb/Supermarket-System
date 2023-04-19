package Deliveries.PresentationLayer;

import Deliveries.DeliveryManagerImpl;

public class DeliveryManagerService {
    public DeliveryManagerImpl deliveryManager;
    public DeliveryManagerService() {
        this.deliveryManager = DeliveryManagerImpl.getInstance();
    }
}
