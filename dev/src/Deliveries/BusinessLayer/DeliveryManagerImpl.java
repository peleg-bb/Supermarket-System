package Deliveries.BusinessLayer;

import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryException;
import Deliveries.BusinessLayer.Enums_and_Interfaces.DeliveryManager;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TripReplanner;
import Deliveries.BusinessLayer.Enums_and_Interfaces.TruckType;
import Deliveries.BusinessLayer.Generators.DeliveryStopGenerator;
import Deliveries.DataAccessLayer.DeliveryStopDAO;
import Deliveries.PresentationLayer.UserInteractionUtil;

import java.util.*;


public class DeliveryManagerImpl implements DeliveryManager {
    public static final int PENDING_SIZE = 120;
    private final TruckController truckController;
    private final DriverController driverController;
    private Set<DeliveryStop> pendingDeliveryStops;
    private DeliveryFormsController deliveryFormsController;
    private TripReplanner tripReplanner;
    private int deliveryCount;
    private static DeliveryManagerImpl instance = null;
    private DeliveryStopDAO deliveryStopDAO;
    // Singleton Constructor
   private DeliveryManagerImpl() {
            pendingDeliveryStops = new HashSet<>();
            truckController = TruckController.getInstance();
            driverController = DriverController.getInstance();
            deliveryFormsController = DeliveryFormsController.getInstance();
            tripReplanner = new UserInteractionUtil();
            DeliveryStopGenerator deliveryStopGenerator = new DeliveryStopGenerator();
            pendingDeliveryStops.addAll(deliveryStopGenerator.getPendingDeliveryStops(PENDING_SIZE));
            deliveryStopDAO = new DeliveryStopDAO();
            deliveryCount = deliveryStopDAO.getCount();
            // shouldn't add all to the pending list, the completed stops should be added their forms
    }

    public static DeliveryManagerImpl getInstance() {
        if (instance == null) {
            instance = new DeliveryManagerImpl();
        }
        return instance;
    }

    @Override
    public int addDeliveryStop(Map<String, Integer> items, Site origin, Site destination, TruckType truckType) {
        DeliveryStop deliveryStop = new DeliveryStop(++deliveryCount, items, origin, destination, truckType);
        pendingDeliveryStops.add(deliveryStop);
        // decide how to manage the origin.
        return deliveryStop.getShipmentInstanceID();
    }

    public void addDeliveryStop(DeliveryStop deliveryStop) {
        pendingDeliveryStops.add(deliveryStop);
    }

    @Override
    public void removeDeliveryStop(int deliveryId) {
        pendingDeliveryStops.remove(deliveryId);
    }


    //maybe private
    public void createDeliveryGroup(){
        HashMap<Integer,List<DeliveryStop>> originToZones = createDeliveryLists(pendingDeliveryStops);
        for(Map.Entry<Integer,List<DeliveryStop>> entries: originToZones.entrySet()){
            try {
                deliveryFormsController.createForm(entries.getValue(), entries.getValue().get(0).getOrigin());
                entries.getValue().forEach(pendingDeliveryStops::remove); // removes the added stops from the pending list
                // might be a bit messy, couldn't think of a better way to get the origin
            } catch (DeliveryException e) {
                // Notify UI
                System.out.println(e.getMessage());
                System.out.println("Delivery group creation failed for origin " + entries.getValue().get(0).getOrigin());
            }
        }
    }



    public void replanDelivery(DeliveryForm form) {
        try {
            replaceTruck(form);
        } catch (DeliveryException e) {
            // Notify UI
            System.out.println("Truck is overweight for delivery " + form
                    + ". Tried to find a new truck but " + e.getMessage());
        }
        int action = tripReplanner.chooseAction(form.getDestinationSitesToVisit());
        if (action == 1) {
            DeliveryStop stopToCancel = tripReplanner.removeStop(form.getDestinationSitesToVisit());
            form.cancelStop(stopToCancel);
            pendingDeliveryStops.add(stopToCancel);
        }
        else if (action == 2) {
            form.cancelForm();
        }
        else if (action == 3) {
            form.performWeightCheck();
        }
        // else do nothing, will be handled by the UI according to submission 1
    }

    private void replaceTruck(DeliveryForm form) throws DeliveryException {
        Truck newTruck = truckController.pickTruck(form.getTruckType(), form.getDispatchWeightTons());
        form.setTruck(newTruck);
        Driver newDriver = driverController.pickDriver(newTruck, form.getDispatchTime(),
                form.getEstimatedTerminationTime());
        form.setDriver(newDriver);
    }

    private HashMap<Site,List<DeliveryStop>> sortStopsByOrigin(Set<DeliveryStop> pendingDeliveryStops){
        HashMap<Site,List<DeliveryStop>> sortedByOrigin = new HashMap<>();
        for (DeliveryStop stop : pendingDeliveryStops) {
            Site origin = stop.getOrigin();
            if(!sortedByOrigin.containsKey(origin)){
                ArrayList<DeliveryStop> originStops = new ArrayList<>();
                originStops.add(stop);
                sortedByOrigin.put(origin,originStops);
            }
            else{
                sortedByOrigin.get(origin).add(stop);
            }
        }
        return sortedByOrigin;
    }

    //takes a list of the stops for each origin and separates to lists sorted by zones
    private HashMap<Integer,List<DeliveryStop>> sortByDeliveryZones(List<DeliveryStop> originsStops){
       HashMap<Integer,List<DeliveryStop>> deliveryZonesSorted = new HashMap<>(); //key-delivery zone, value-stops in that delivery zones
        for (DeliveryStop stop: originsStops) {
            if (!deliveryZonesSorted.containsKey(stop.getDestination().getDeliveryZone())) {
                    ArrayList<DeliveryStop> deliveryZoneStops = new ArrayList<>();
                    deliveryZoneStops.add(stop);
                    deliveryZonesSorted.put(stop.getDestination().getDeliveryZone(), deliveryZoneStops);
                } else {
                    deliveryZonesSorted.get(stop.getDestination().getDeliveryZone()).add(stop);
                }

        }
        return deliveryZonesSorted;
    }

    public HashMap<Integer,List<DeliveryStop>> createDeliveryLists(Set<DeliveryStop> pendingDeliveryStops){
        HashMap<Integer,List<DeliveryStop>> originToSortedByZones = new HashMap<>();
        HashMap<Site,List<DeliveryStop>> originSorted = sortStopsByOrigin(pendingDeliveryStops);
        for(Map.Entry<Site,List<DeliveryStop>> originsStops: originSorted.entrySet()){
            List<DeliveryStop> stops = originsStops.getValue();
            HashMap<Integer,List<DeliveryStop>> zoneSorted = sortByDeliveryZones(stops);
            for (Map.Entry<Integer,List<DeliveryStop>> entries: zoneSorted.entrySet()) {
                originToSortedByZones.put(entries.getKey(),entries.getValue());
            }
        }
        return originToSortedByZones;
    }

    public TruckController getTruckController() {
        return truckController;
    }

    public DriverController getDriverController() {
        return driverController;
    }

    public DeliveryFormsController getDeliveryFormsController() {
        return deliveryFormsController;
    }

    public Iterable<? extends DeliveryStop> getPendingDeliveryStops() {
        return pendingDeliveryStops;
    }
}


