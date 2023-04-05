package Deliveries;

import java.util.List;
import java.util.Map;

public class DeliveryStop {
    private int deliveryId;
    private Map<String, Integer> items;
    private Site destination;
    private TruckType truckTypeRequired;



    public DeliveryStop(int deliveryId, Map<String, Integer> items, Site destination, TruckType truckTypeRequired) {
        this.deliveryId = deliveryId;
        this.items = items;
        this.destination = destination;
        this.truckTypeRequired = truckTypeRequired;
    }

    // Getters

    public TruckType getTruckTypeRequired() {
        return truckTypeRequired;
    }

    public Map<String, Integer> getItems(){
        return items;
    }



}
