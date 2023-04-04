package Deliveries;

import java.util.List;

public class DeliveryStop {
    private int deliveryId;
    private List<String> items;
    private Site destination;



    public DeliveryStop(int deliveryId, List<String> items, Site destination) {
        this.deliveryId = deliveryId;
        this.items = items;
        this.destination = destination;
    }

    


}
