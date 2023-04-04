package Deliveries;

import java.util.ArrayList;
import java.util.List;

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
    public void addDeliveryStop(List<String> items, Site origin, Site destination) {
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
        try {
            Truck newTruck = truckController.pickTruck(form.getTruckType(), form.getDispatchWeightTons());
            form.setMaxWeightAllowed(newTruck.getMaxWeightTons());
        } catch (DeliveryException e) {
            // Notify UI
        }

        // Handle the case where no truck is available
        List<DeliveryStop> stopsToAdd = null;
        List<DeliveryStop> stopsToRemove = null;
    }

    public int returnReplanningResponse(List<DeliveryStop> stopsToAdd, List<DeliveryStop> stopsToRemove) {
        // Notify UI
        return 0;
    }

    private TruckType getTruckType(List<DeliveryStop> stops) {
        return null;
    }


}


