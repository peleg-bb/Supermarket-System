package Deliveries;

import java.util.ArrayList;
import java.util.List;

import static Deliveries.TruckType.Regular;

public class DeliveryManagerImpl implements DeliveryManager{
    private List<Truck> trucks;
    private List<DeliveryStop> deliveryStops;
    private int deliveryCount;
    private  int deliveryFormCount;
    private static DeliveryManagerImpl instance = null;
    // Singleton Constructor
   private DeliveryManagerImpl() {
            deliveryCount = 0;
            deliveryFormCount = 0;
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
    public void addDeliveryStop(List<String> items, Site origin, Site destination) {
        DeliveryStop deliveryStop = new DeliveryStop(++deliveryCount, items, destination);
        // decide how to manage the origin.
    }

    @Override
    public void removeDeliveryStop(int deliveryId) {


    }

    public void createForm(List<DeliveryStop> stops, TruckType requiredTruck, Site origin){
        Driver d = pickDriver(requiredTruck, 100);
        Truck t = pickTruck(requiredTruck);
       //DeliveryForm form = new DeliveryForm(deliveryFormCount++, stops, origin, requiredTruck)
    }

    private Driver pickDriver(TruckType truckType, int truckWeightTons){
       //for(int i=0)//go over all drivers and pick a driver that answers all requirements
        return new Driver("a","1","sa");
    }

    private  Truck pickTruck(TruckType requiredType){//sa,e for truck controller
        return new Truck("a","a", Regular,2,2);
    }
}
