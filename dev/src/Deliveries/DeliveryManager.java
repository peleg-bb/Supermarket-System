package Deliveries;

import java.util.List;
import java.util.Map;

public interface DeliveryManager {


    void addDeliveryStop(Map<String, Integer> deliveryItems, Site origin, Site destination);
    void removeDeliveryStop(int deliveryId);

}
