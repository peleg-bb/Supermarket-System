package Deliveries;

import java.util.List;

public interface DeliveryManager {


    void addDeliveryStop(List<String> items, Site origin, Site destination);
    void removeDeliveryStop(int deliveryId);

}
