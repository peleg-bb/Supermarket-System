package Deliveries;

import java.util.Map;

public class DeliveryStop {
    private int deliveryId;
    private Map<String, Integer> items;
    private Site origin;
    private Site destination;
    private TruckType truckTypeRequired;



    public DeliveryStop(int deliveryId, Map<String, Integer> items,Site origin, Site destination, TruckType truckTypeRequired) {
        this.deliveryId = deliveryId;
        this.items = items;
        this.destination = destination;
        this.truckTypeRequired = truckTypeRequired;
        this.origin=origin;
    }

    // Getters

    public TruckType getTruckTypeRequired() {
        return truckTypeRequired;
    }

    public Map<String, Integer> getItems(){
        return items;
    }

    public Site getOrigin(){
        return origin;
    }

    public Site getDestination(){
        return destination;
    }


    public int getDeliveryId() {
        return deliveryId;
    }
}
