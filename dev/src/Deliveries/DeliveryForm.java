package Deliveries;

import PresentationLayer.UserInteractionUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.ListIterator;

public class DeliveryForm {
    private final int formId ;
    private Date date;
    private Timestamp dispatchTime;
    private WeightMeasurer weightMeasurer;
    private List<DeliveryStop> destinationSitesToVisit;
    private List<DeliveryStop> destinationSitesVisited;
    private int maxWeightAllowed;
    private String driverID;
    private String truckID;
    private Site originSite;
    private DeliveryManagerImpl deliveryManager;
    private DeliveryFormsController deliveryFormsController;
    private DeliveryStop stopToCancel;
    private int dispatchWeightTons; // Weight of the truck when it leaves the origin site

    public DeliveryForm(int formId, List<DeliveryStop> stops, Site originSite, int maxWeightAllowed,
                        String driverID, String truckID){
        this.formId = formId;
        this.destinationSitesToVisit = stops;
        this.destinationSitesVisited = new ArrayList<>();
        dispatchTime = new Timestamp(System.currentTimeMillis());
        // date = today?
        date = new Date();
        this.originSite = originSite;
        this.maxWeightAllowed = maxWeightAllowed;
        this.driverID = driverID;
        this.truckID = truckID;
        this.weightMeasurer = new UserInteractionUtil();
        deliveryManager = DeliveryManagerImpl.getInstance();
        deliveryFormsController = DeliveryFormsController.getInstance();
    }

    public void addDeliveryStop(DeliveryStop deliveryStop) {
        destinationSitesToVisit.add(deliveryStop);
    }

    public void visitDeliveryStop(DeliveryStop deliveryStop) {
        if (destinationSitesVisited.contains(deliveryStop) || deliveryStop.getStatus() == DeliveryStatus.DELIVERED) {
            return; // Already visited, should never reach here
        }

        destinationSitesVisited.add(deliveryStop);
        deliveryStop.setStatus(DeliveryStatus.DELIVERED);
        performWeightCheck();
    }

    private void performWeightCheck() {
        int currentWeight = measureWeight();
        if (currentWeight > maxWeightAllowed) {
            deliveryManager.replanDelivery(this);
        }
    }

    public int measureWeight() {
        int currentWeight = weightMeasurer.measureWeight(this);
        setDispatchWeightTons(currentWeight);
        return currentWeight;
    }

    @Override
    public String toString() {
        return "DeliveryForm{" +
                "formId=" + formId +
                ", dispatchTime=" + dispatchTime +
                ", originSite=" + originSite +
                ", destinationSitesToVisit=" + destinationSitesToVisit +
                ", dispatchWeightTons=" + dispatchWeightTons +
                '}';
    }

    /* setDispatchWeightTons() is called when the truck leaves the origin site */
    public void setDispatchWeightTons(int dispatchWeightTons) {
        this.dispatchWeightTons = dispatchWeightTons;
        if (dispatchWeightTons > maxWeightAllowed) {
            deliveryManager.replanDelivery(this);
            // Notify UI
        }
    }

    // setMaxWeightAllowed() is called when the truck is loaded with items
        public void setMaxWeightAllowed(int maxWeightAllowed) {
        this.maxWeightAllowed = maxWeightAllowed;
        if (dispatchWeightTons > maxWeightAllowed) {
            deliveryManager.replanDelivery(this);
        }
    }

    public void setDestinationSitesToVisit(List<DeliveryStop> destinationSitesToVisit) {
        this.destinationSitesToVisit = destinationSitesToVisit;
    }

    public int getFormId() {
        return formId;
    }

    public List<DeliveryStop> getDestinationSitesToVisit() {
        return destinationSitesToVisit;
    }

    public List<DeliveryStop> getDestinationSitesVisited() {
        return destinationSitesVisited;
    }

    public TruckType getTruckType() {
        TruckType truckType = TruckType.Regular;
        // for each delivery stop, check if the truck type is the same
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop.getTruckTypeRequired() == TruckType.Refrigerated) {
                truckType = TruckType.Refrigerated;
            }
        }
        return truckType;
    }

    public int getDispatchWeightTons() {
        return dispatchWeightTons;
    }

    public void startJourney(){
        // visit the stops in the order they were added
        ListIterator<DeliveryStop> iterator = destinationSitesToVisit.listIterator();
        while(iterator.hasNext()){
            DeliveryStop currentStop = iterator.next();
            visitDeliveryStop(currentStop);
            if (currentStop.getStatus() == DeliveryStatus.DELIVERED || currentStop.equals(stopToCancel)) {
                iterator.remove();
                // Needs testing
            }
        }
        completeJourney();

    }

    private void completeJourney() {
        deliveryManager.getDriverController().freeDriver(driverID);
        deliveryManager.getTruckController().freeTruck(truckID);
        deliveryFormsController.terminateDeliveryForm(this);
    }

    public void cancelForm() {
        deliveryManager.getDriverController().freeDriver(driverID);
        deliveryManager.getTruckController().freeTruck(truckID);
        // for delivery stops that were not visited, set their status to cancelled
        for (DeliveryStop stop : destinationSitesToVisit) {
            if (stop.getStatus() != DeliveryStatus.DELIVERED) { // Not sure if this is needed, but just in case
                stop.setStatus(DeliveryStatus.NOT_STARTED);
                deliveryManager.addDeliveryStop(stop);
            }
        }
    }

    public void cancelStop(DeliveryStop stop) {
        stopToCancel = stop;
    }

    public void setWeightMeasurer(WeightMeasurer weightMeasurer) {
        // This is used for testing
        this.weightMeasurer = weightMeasurer;
    }


}