package Deliveries;

import java.util.Map;

public interface DeliveryManager {
    int addDeliveryStop(Map<String, Integer> deliveryItems, Site origin, Site destination,TruckType truckType);
    void removeDeliveryStop(int deliveryId);

}
