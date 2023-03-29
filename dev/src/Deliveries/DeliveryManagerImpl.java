package Deliveries;

import java.util.ArrayList;
import java.util.List;

public class DeliveryManagerImpl implements DeliveryManager{
    private List<Truck> trucks;
    private List<DeliveryStop> deliveryStops;
    private int deliveryCount;
    private static DeliveryManagerImpl instance = null;
    // Singleton Constructor
   private DeliveryManagerImpl() {
            deliveryCount = 0;
            deliveryStops = new ArrayList<DeliveryStop>();
            trucks = new ArrayList<Truck>();
    }

    public static DeliveryManagerImpl getInstance() {
        if (instance == null) {
            instance = new DeliveryManagerImpl();
        }
        return instance;
    }

    @Override
    public void addDeliveryStop(List<String> items, Site destination) {
        DeliveryStop deliveryStop = new DeliveryStop(++deliveryCount, items, destination);
    }

    @Override
    public void removeDeliveryStop(int deliveryId) {


    }
}
