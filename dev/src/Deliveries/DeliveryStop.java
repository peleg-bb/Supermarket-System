package Deliveries;

import java.util.List;

public class DeliveryStop {
    private int deliveryId;
    private List<String> items;
    private Site destination;
    private TruckType truckTypeRequired;



    public DeliveryStop(int deliveryId, List<String> items, Site destination, TruckType truckTypeRequired) {
        this.deliveryId = deliveryId;
        this.items = items;
        this.destination = destination;
        this.truckTypeRequired = truckTypeRequired;
    }

    // Getters
    public TruckType getTruckTypeRequired() {
        return truckTypeRequired;
    }




}
