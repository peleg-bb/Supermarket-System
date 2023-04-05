package Deliveries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DeliveryManagerImpl implements DeliveryManager{
    private TruckController truckController;
    private DriverController driverController;
    private List<DeliveryStop> pendingDeliveryStops;

    private int deliveryCount;
    private int deliveryFormCount;
    private static DeliveryManagerImpl instance = null;
    // Singleton Constructor
   private DeliveryManagerImpl() {
            deliveryCount = 0;
            deliveryFormCount = 0;
            pendingDeliveryStops = new ArrayList<DeliveryStop>();
            truckController = TruckController.getInstance();
            driverController = DriverController.getInstance();
    }

    public static DeliveryManagerImpl getInstance() {
        if (instance == null) {
            instance = new DeliveryManagerImpl();
        }
        return instance;
    }

    @Override
    public void addDeliveryStop(Map<String, Integer> items, Site origin, Site destination) {
        DeliveryStop deliveryStop = new DeliveryStop(++deliveryCount, items, destination, TruckType.Regular);
        pendingDeliveryStops.add(deliveryStop);
        // decide how to manage the origin.
    }

    @Override
    public void removeDeliveryStop(int deliveryId) {
        pendingDeliveryStops.remove(deliveryId);
    }



    public boolean createForm(List<DeliveryStop> stops, TruckType truckType, Site origin) {
        try {
            Truck truck = truckController.pickTruck(truckType);
            Driver driver = driverController.pickDriver(truck.getType(), truck.getMaxWeightTons());
            DeliveryForm form = new DeliveryForm(deliveryFormCount++, stops, origin, truck.getMaxWeightTons());//TODO: fix weight
            return true;

        } catch (DeliveryException e) {
            return false;
        }
    }

    public void replanDelivery(DeliveryForm form) {
        // Notify UI
        Truck newTruck = replaceTruck(form);

        // Handle the case where no truck is available
        List<DeliveryStop> stopsToAdd = null;
        List<DeliveryStop> stopsToRemove = null;
    }


    private Truck replaceTruck(DeliveryForm form){
        try {
            Truck newTruck = truckController.pickTruck(form.getTruckType(), form.getDispatchWeightTons());
            form.setMaxWeightAllowed(newTruck.getMaxWeightTons());
            return newTruck;
        } catch (DeliveryException e) {
            // Notify UI
        }
       return null; //????
    }

    private void removeItems(DeliveryForm form){
       List<DeliveryStop> stopsToVisit = form.getDestinationSitesToVisit();
       for(DeliveryStop deliveryStop:stopsToVisit){
           Map<String, Integer> items = deliveryStop.getItems();
           items.remove(0); //need to decide how to remove items
       }
    }

    private void removeStop(DeliveryForm form){
        form.getDestinationSitesToVisit().remove(0); //decide how to remove stops

    }

    private void replaceStops(DeliveryForm form){
       //need zones for that
    }
    public int returnReplanningResponse(List<DeliveryStop> stopsToAdd, List<DeliveryStop> stopsToRemove) {
        // Notify UI
        return 0;
    }

    private TruckType getTruckType(List<DeliveryStop> stops) {
        return null;//why?
    }


}


